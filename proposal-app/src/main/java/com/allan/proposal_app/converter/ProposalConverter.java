package com.allan.proposal_app.converter;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;

@Component
public class ProposalConverter {

    public ProposalEntity convertProposalRequestDtoToProposalEntity(ProposalRequestDto proposalRequestDto) {
        if (proposalRequestDto == null) {
            throw new RuntimeException("proposalRequestDto is required");
        }
        ProposalEntity proposalEntity = new ProposalEntity();
        proposalEntity.setProposalValue(proposalRequestDto.getProposalValue());
        proposalEntity.setPaymentLimitInMonths(proposalRequestDto.getPaymentLimitInMonths());

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
        ProposalResponseDto proposalResponseDto = new ProposalResponseDto();
        proposalResponseDto.setId(proposalEntity.getId());
        proposalResponseDto.setName(proposalEntity.getAccountEntity().getName());
        proposalResponseDto.setSurName(proposalEntity.getAccountEntity().getSurName());
        proposalResponseDto.setWasApproved(proposalEntity.getWasApproved());
        proposalResponseDto.setProposalValue(NumberFormat.getNumberInstance().format(proposalEntity.getProposalValue()));
        proposalResponseDto.setPaymentLimitInMonths(proposalEntity.getPaymentLimitInMonths());
        proposalResponseDto.setObservation(proposalEntity.getObservation());

        return proposalResponseDto;
    }

}
