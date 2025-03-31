package com.allan.proposal_app.repository;

import com.allan.proposal_app.entity.ProposalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProposalRepository extends CrudRepository<ProposalEntity, Long> {

    @Query(value = "SELECT * FROM tb_proposal p ORDER BY p.id LIMIT :size OFFSET :offset", nativeQuery = true)
    List<ProposalEntity> findProposalsWithPagination(@Param("offset") int offset, @Param("size") int size);


}
