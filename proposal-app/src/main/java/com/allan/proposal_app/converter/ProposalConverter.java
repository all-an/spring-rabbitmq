package com.allan.proposal_app.converter;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class ProposalConverter {

    private final NumberFormatHelper numberFormatHelper;

    public ProposalConverter(NumberFormatHelper numberFormatHelper) {
        this.numberFormatHelper = numberFormatHelper;
    }

    public ProposalEntity convertProposalRequestDtoToProposalEntity(ProposalRequestDto proposalRequestDto) {
        if (proposalRequestDto == null) {
            throw new RuntimeException("proposalRequestDto is required");
        }
        ProposalEntity proposalEntity = new ProposalEntity();
        proposalEntity.setProposalValue(proposalRequestDto.getProposalValue());
        proposalEntity.setPaymentLimitInMonths(proposalRequestDto.getPaymentLimitInMonths());
        proposalEntity.setIntegrated(true);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName(proposalRequestDto.getName());
        accountEntity.setSurName(proposalRequestDto.getSurName());
        accountEntity.setPhone(proposalRequestDto.getPhone());
        accountEntity.setCpf(proposalRequestDto.getCpf());
        accountEntity.setIncome(proposalRequestDto.getIncome());
        proposalEntity.setAccountEntity(accountEntity);

        return proposalEntity;
    }

    public ProposalResponseDto convertProposalEntityToProposalResponseDto(ProposalEntity proposalEntity) {
        if (proposalEntity == null) {
            throw new RuntimeException("proposalEntity is required");
        }
        ProposalResponseDto dto = new ProposalResponseDto();
        dto.setId(proposalEntity.getId());
        dto.setName(proposalEntity.getAccountEntity().getName());
        dto.setSurName(proposalEntity.getAccountEntity().getSurName());
        dto.setWasApproved(proposalEntity.getWasApproved());
        dto.setProposalValue(numberFormatHelper.format(proposalEntity.getProposalValue())); // Injected dependency
        dto.setPaymentLimitInMonths(proposalEntity.getPaymentLimitInMonths());
        dto.setObservation(proposalEntity.getObservation());

        return dto;
    }

}
