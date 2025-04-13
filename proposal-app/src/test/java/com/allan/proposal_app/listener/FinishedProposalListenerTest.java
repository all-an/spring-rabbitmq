package com.allan.proposal_app.listener;

import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class FinishedProposalListenerTest {

    private ProposalRepository proposalRepository;
    private FinishedProposalListener listener;

    @BeforeEach
    void setUp() throws Exception {
        proposalRepository = mock(ProposalRepository.class);
        listener = new FinishedProposalListener(proposalRepository);

        // Use reflection to set private field
        Field field = FinishedProposalListener.class.getDeclaredField("proposalRepository");
        field.setAccessible(true);
        field.set(listener, proposalRepository);
    }

    @Test
    void shouldSaveProposalWhenReceived() {
        ProposalEntity proposal = new ProposalEntity();

        listener.finishedProposal(proposal);

        verify(proposalRepository, times(1)).save(proposal);
    }
}