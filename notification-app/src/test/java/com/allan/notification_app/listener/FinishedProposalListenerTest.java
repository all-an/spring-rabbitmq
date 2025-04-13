package com.allan.notification_app.listener;

import com.allan.notification_app.constant.ConstantMessage;
import com.allan.notification_app.domain.AccountEntity;
import com.allan.notification_app.domain.ProposalEntity;
import com.allan.notification_app.service.SnsNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class FinishedProposalListenerTest {

    @Mock
    private SnsNotificationService snsNotificationService;

    @InjectMocks
    private FinishedProposalListener finishedProposalListener;

    @Captor
    private ArgumentCaptor<String> messageCaptor;

    @Captor
    private ArgumentCaptor<String> phoneCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPendingProposalWithValidProposal() {
        // Arrange
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Test Account");
        accountEntity.setPhone("989879868");

        ProposalEntity proposal = new ProposalEntity();
        proposal.setAccountEntity(accountEntity);
        proposal.setObservation("Test Observation");

        // Act
        finishedProposalListener.finishedProposal(proposal);

        // Assert
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(accountEntity.getPhone(), capturedPhone);
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS_FINISHED, "Test Account", "Test Observation"), capturedMessage);
    }

    @Test
    public void testPendingProposalWithNullProposal() {
        // Act & Assert
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            finishedProposalListener.finishedProposal(null);
        });
        assertEquals("Proposal is required", exception.getMessage());

        // Verify that snsNotificationService is never called
        verify(snsNotificationService, never()).notify(anyString(), anyString());
    }

    @Test
    public void testPendingProposalWithProposalHavingNullAccount() {
        // Arrange
        ProposalEntity proposal = new ProposalEntity();
        // Account is null

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            finishedProposalListener.finishedProposal(proposal);
        });

        // Verify that snsNotificationService is never called
        verify(snsNotificationService, never()).notify(anyString(), anyString());
    }

    @Test
    public void testPendingProposalWithProposalHavingNullAccountName() {
        // Arrange
        AccountEntity accountEntity = new AccountEntity();
        // Name and Phone are null

        ProposalEntity proposal = new ProposalEntity();
        proposal.setAccountEntity(accountEntity);

        // Act
        finishedProposalListener.finishedProposal(proposal);

        // Assert
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS_FINISHED, null, null), capturedMessage);
        assertNull(capturedPhone);
    }

    @Test
    public void testPendingProposalServiceFailure() {
        // Arrange
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Test Account");
        accountEntity.setPhone("989879868");

        ProposalEntity proposal = new ProposalEntity();
        proposal.setAccountEntity(accountEntity);
        proposal.setObservation("Test Observation");

        doThrow(new RuntimeException("Service unavailable")).when(snsNotificationService).notify(anyString(), anyString());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            finishedProposalListener.finishedProposal(proposal);
        });
        assertEquals("Service unavailable", exception.getMessage());

        // Verify the message was correctly formed before the exception
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(accountEntity.getPhone(), capturedPhone);
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS_FINISHED, "Test Account", "Test Observation"), capturedMessage);
    }
}