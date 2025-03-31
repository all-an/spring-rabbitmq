package com.allan.proposal_app.converter;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProposalConverterTest {

    @Mock
    private NumberFormatHelper numberFormatHelper;

    @InjectMocks
    private ProposalConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertProposalRequestDtoToProposalEntity_Success() {
        ProposalRequestDto requestDto = new ProposalRequestDto();
        requestDto.setProposalValue(10000.0);
        requestDto.setPaymentLimitInMonths(12);
        requestDto.setName("John");
        requestDto.setSurName("Doe");
        requestDto.setCpf("12345678900");
        requestDto.setIncome(5000.0);

        ProposalEntity result = converter.convertProposalRequestDtoToProposalEntity(requestDto);

        assertNotNull(result);
        assertEquals(10000, result.getProposalValue());
        assertEquals(12, result.getPaymentLimitInMonths());
        assertTrue(result.isIntegrated());
        assertNotNull(result.getAccountEntity());
        assertEquals("John", result.getAccountEntity().getName());
        assertEquals("Doe", result.getAccountEntity().getSurName());
        assertEquals("12345678900", result.getAccountEntity().getCpf());
        assertEquals(5000, result.getAccountEntity().getIncome());
    }

    @Test
    void testConvertProposalRequestDtoToProposalEntity_NullInput_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertProposalRequestDtoToProposalEntity(null));
        assertEquals("proposalRequestDto is required", exception.getMessage());
    }

    @Test
    void testConvertProposalEntityToProposalResponseDto_Success() {
        ProposalEntity proposalEntity = new ProposalEntity();
        proposalEntity.setProposalValue(10000.0);
        proposalEntity.setPaymentLimitInMonths(12);
        proposalEntity.setWasApproved(true);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("test");
        proposalEntity.setAccountEntity(accountEntity);

        when(numberFormatHelper.format(proposalEntity.getProposalValue())).thenReturn(proposalEntity.getProposalValue().toString());

        ProposalResponseDto result = converter.convertProposalEntityToProposalResponseDto(proposalEntity);

        assertNotNull(result);
        assertEquals(proposalEntity.getProposalValue().toString(), result.getProposalValue());
        assertTrue(result.getWasApproved());
    }

    @Test
    void testConvertProposalEntityToProposalResponseDto_NullInput_ShouldThrowException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertProposalEntityToProposalResponseDto(null));
        assertEquals("proposalEntity is required", exception.getMessage());
    }
}
