package com.allan.proposal_app.repository;

import com.allan.proposal_app.entity.ProposalEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProposalRepository extends CrudRepository<ProposalEntity, Long> {
}
