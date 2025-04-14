package com.allan.proposal_app.listener;

import com.allan.proposal_app.converter.ProposalConverter;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.entity.ProposalEntity;
import com.allan.proposal_app.repository.ProposalRepository;
import com.allan.proposal_app.service.WebSocketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class FinishedProposalListenerTest {

    private ProposalRepository proposalRepository;
    private WebSocketService webSocketService;
    private ProposalConverter proposalConverter;
    private FinishedProposalListener listener;

    @BeforeEach
    void setUp() throws Exception {
        proposalRepository = mock(ProposalRepository.class);
        proposalConverter = mock(ProposalConverter.class);
        webSocketService = mock(WebSocketService.class);
        listener = new FinishedProposalListener(proposalRepository,
                webSocketService, proposalConverter);

        // Use reflection to set private field
        Field field = FinishedProposalListener.class.getDeclaredField("proposalRepository");
        field.setAccessible(true);
        field.set(listener, proposalRepository);
    }

    @Test
    void shouldSaveProposalWhenReceived() {
        ProposalEntity proposal = new ProposalEntity();
        ProposalResponseDto proposalResponseDto = new ProposalResponseDto();
        when(proposalConverter.convertProposalEntityToProposalResponseDto(proposal))
                .thenReturn(proposalResponseDto);
        when(proposalRepository.save(proposal)).thenReturn(proposal);

        listener.finishedProposal(proposal);

        verify(proposalRepository, times(1)).save(proposal);
        verify(proposalConverter, times(1)).convertProposalEntityToProposalResponseDto(proposal);
        verify(webSocketService, times(1)).notify(proposalResponseDto);
    }
}