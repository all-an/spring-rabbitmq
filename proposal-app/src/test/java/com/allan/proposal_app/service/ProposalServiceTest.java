package com.allan.proposal_app.service;

import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.event.ProposalEventPublisher;
import com.allan.proposal_app.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProposalServiceTest {

    @InjectMocks
    private ProposalService proposalService;

    @Mock
    private ProposalRepository proposalRepository;

    @Mock
    private ProposalConverter proposalConverter;

    @Mock
    private RabbitMQNotificationService rabbitMQNotificationService;

    @Mock
    private ProposalEventPublisher eventPublisher;

    private String exchange = "test-exchange";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proposalService = new ProposalService(proposalRepository, rabbitMQNotificationService, proposalConverter, exchange, eventPublisher);
    }

    @Test
    void testProposalService_whenNullRequest_thenThrowsException() {
        // Act & Assert: Verify exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> proposalService.create(null)
        );

        assertEquals("proposalRequestDto is required", exception.getMessage());
    }

    @Test
    void testProposalService() {
        ProposalRequestDto requestDto = new ProposalRequestDto();
        requestDto.setProposalValue(10000.0);
        requestDto.setPaymentLimitInMonths(12);
        requestDto.setName("John");
        requestDto.setSurName("Doe");
        requestDto.setCpf("12345678900");
        requestDto.setIncome(5000.0);

        ProposalEntity proposalEntity = new ProposalEntity();
        proposalEntity.setId(3L);
        proposalEntity.setProposalValue(requestDto.getProposalValue());
        proposalEntity.setPaymentLimitInMonths(requestDto.getPaymentLimitInMonths());
        proposalEntity.setWasApproved(true);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName(requestDto.getName());
        accountEntity.setSurName(requestDto.getSurName());
        accountEntity.setCpf(requestDto.getCpf());
        accountEntity.setIncome(requestDto.getIncome());
        proposalEntity.setAccountEntity(accountEntity);

        ProposalResponseDto proposalResponseDto = new ProposalResponseDto();
        proposalResponseDto.setId(proposalEntity.getId());
        proposalResponseDto.setName(proposalEntity.getAccountEntity().getName());
        proposalResponseDto.setSurName(proposalEntity.getAccountEntity().getSurName());
        proposalResponseDto.setPaymentLimitInMonths(proposalEntity.getPaymentLimitInMonths());
        proposalResponseDto.setProposalValue(String.valueOf(proposalEntity.getProposalValue()));
        proposalResponseDto.setWasApproved(proposalEntity.getWasApproved());

        when(proposalConverter.convertProposalRequestDtoToProposalEntity(requestDto)).thenReturn(proposalEntity);
        when(proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntity)).thenReturn(proposalResponseDto);

        ProposalResponseDto result = proposalService.create(requestDto);

        ArgumentCaptor<ProposalEntity> proposalEntityArgumentCaptor = ArgumentCaptor.forClass(ProposalEntity.class);
        verify(proposalRepository).save(proposalEntityArgumentCaptor.capture());

        ProposalEntity capturedEntity = proposalEntityArgumentCaptor.getValue();

        assertNotNull(result);
        assertEquals(proposalEntity.getId(), result.getId());
        assertEquals(proposalEntity.getWasApproved(), result.getWasApproved());
        assertEquals(accountEntity.getName(), result.getName());
        assertNotNull(capturedEntity);
        assertEquals(requestDto.getProposalValue(), capturedEntity.getProposalValue());
        assertEquals(requestDto.getPaymentLimitInMonths(), capturedEntity.getPaymentLimitInMonths());
        assertNotNull(capturedEntity.getAccountEntity());
        assertEquals(requestDto.getName(), capturedEntity.getAccountEntity().getName());
        assertEquals(requestDto.getSurName(), capturedEntity.getAccountEntity().getSurName());
        assertEquals(requestDto.getCpf(), capturedEntity.getAccountEntity().getCpf());
        assertEquals(requestDto.getIncome(), capturedEntity.getAccountEntity().getIncome());
    }

    @Test
    void shouldThrowExceptionWhenNotificationFails() {
        // Arrange
        ProposalRequestDto requestDto = new ProposalRequestDto();
        ProposalEntity proposalEntity = new ProposalEntity();
        when(proposalConverter.convertProposalRequestDtoToProposalEntity(requestDto)).thenReturn(proposalEntity);

        doThrow(new RuntimeException("RabbitMQ error"))
                .when(rabbitMQNotificationService)
                .notify(proposalEntity, "test-exchange");

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            proposalService.create(requestDto); // create() calls notifyRabbitMQ internally
        });

        // Verify the entity was updated before rethrowing the exception
        verify(proposalRepository, times(2)).save(proposalEntity);
        assertEquals("Proposal not integrated.", thrown.getMessage());
    }

    @Test
    void testGetProposals() {
        int page = 1;
        int size = 1;
        int offset = page * size;

        List<ProposalEntity> mockEntities = new ArrayList<>();
        ProposalEntity proposalEntity1 = new ProposalEntity();
        proposalEntity1.setProposalValue(3.5);
        mockEntities.add(proposalEntity1);

        when(proposalRepository.findProposalsWithPagination(offset, size)).thenReturn(mockEntities);

        // Mocking conversion
        ProposalResponseDto proposalResponseDto1 = new ProposalResponseDto();
        proposalResponseDto1.setProposalValue(String.valueOf(proposalEntity1.getProposalValue()));

        when(proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntity1))
                .thenReturn(proposalResponseDto1);

        // Calling service method
        List<ProposalResponseDto> result = proposalService.getProposals(page, size);

        // Verifications
        assertNotNull(result);
        assertEquals(proposalEntity1.getProposalValue().toString(), result.get(0).getProposalValue());
        assertEquals(size, result.size());
        verify(proposalRepository, times(1)).findProposalsWithPagination(offset, size);
        verify(proposalConverter, times(size)).convertProposalEntityToProposalResponseDto(any(ProposalEntity.class));
    }

}
