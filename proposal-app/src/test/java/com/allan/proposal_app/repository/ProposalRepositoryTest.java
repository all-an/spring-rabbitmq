package com.allan.proposal_app.repository;

import com.allan.proposal_app.entity.AccountEntity;
import com.allan.proposal_app.entity.ProposalEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
public class ProposalRepositoryTest {

    @Autowired
    private ProposalRepository proposalRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testFindProposalsWithPagination() {
        int size = 5;
        int page = 1;
        int offset = page * size;

        // Insert test data
        IntStream.range(1, 21).forEach(i -> {
            ProposalEntity proposal = new ProposalEntity();
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setName("Account " + i);
            proposal.setAccountEntity(accountEntity);
            entityManager.persist(proposal);
        });
        entityManager.flush(); // Force persistence to DB

        // Execute repository method
        List<ProposalEntity> result = proposalRepository.findProposalsWithPagination(offset, size);

        // Assertions
        assertNotNull(result);
        assertEquals(size, result.size()); // Should return 'size' proposals
        assertEquals(6, result.get(0).getId()); // The first proposal should be ID 6 (since page 1 starts at offset 5)
    }
}