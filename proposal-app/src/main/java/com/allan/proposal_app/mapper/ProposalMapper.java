package com.allan.proposal_app.mapper;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.entity.ProposalEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProposalMapper {

    ProposalEntity convertDtoToProposalEntity(ProposalRequestDto proposalRequestDto);

}
