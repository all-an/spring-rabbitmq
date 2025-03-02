package com.allan.proposal_app.service;

import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProposalServiceTest {

    @InjectMocks
    private ProposalService proposalService;

    @Mock
    private ProposalRepository proposalRepository;

    @Mock
    private ProposalConverter proposalConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        proposalResponseDto.setName(proposalEntity.getAccountEntity().getName());
        proposalResponseDto.setSurName(proposalEntity.getAccountEntity().getSurName());
        proposalResponseDto.setPaymentLimitInMonths(proposalEntity.getPaymentLimitInMonths());
        proposalResponseDto.setProposalValue(proposalEntity.getProposalValue());
        proposalResponseDto.setWasApproved(proposalEntity.getWasApproved());

        when(proposalConverter.convertProposalRequestDtoToProposalEntity(requestDto)).thenReturn(proposalEntity);
        when(proposalConverter.convertProposalEntityToProposalResponseDto(proposalEntity)).thenReturn(proposalResponseDto);

        proposalService.create(requestDto);

        ArgumentCaptor<ProposalEntity> proposalEntityArgumentCaptor = ArgumentCaptor.forClass(ProposalEntity.class);
        verify(proposalRepository).save(proposalEntityArgumentCaptor.capture());

        ProposalEntity capturedEntity = proposalEntityArgumentCaptor.getValue();

        // Assert: Verify the saved entity contains expected values
        assertNotNull(capturedEntity);
        assertEquals(requestDto.getProposalValue(), capturedEntity.getProposalValue());
        assertEquals(requestDto.getPaymentLimitInMonths(), capturedEntity.getPaymentLimitInMonths());
        assertNotNull(capturedEntity.getAccountEntity());
        assertEquals(requestDto.getName(), capturedEntity.getAccountEntity().getName());
        assertEquals(requestDto.getSurName(), capturedEntity.getAccountEntity().getSurName());
        assertEquals(requestDto.getCpf(), capturedEntity.getAccountEntity().getCpf());
        assertEquals(requestDto.getIncome(), capturedEntity.getAccountEntity().getIncome());
    }

}
