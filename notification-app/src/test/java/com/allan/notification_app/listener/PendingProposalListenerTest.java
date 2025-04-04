package com.allan.notification_app.listener;

import com.allan.notification_app.constant.ConstantMessage;
import com.allan.notification_app.domain.AccountEntity;
import com.allan.notification_app.domain.ProposalEntity;
import com.allan.notification_app.service.SnsNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PendingProposalListenerTest {

    @Mock
    private SnsNotificationService snsNotificationService;

    @InjectMocks
    private PendingProposalListener pendingProposalListener;

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

        // Act
        pendingProposalListener.pendingProposal(proposal);

        // Assert
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(accountEntity.getPhone(), capturedPhone);
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS, "Test Account"), capturedMessage);
    }

    @Test
    public void testPendingProposalWithNullProposal() {
        // Act & Assert
        RuntimeException exception = assertThrows(IllegalArgumentException.class, () -> {
            pendingProposalListener.pendingProposal(null);
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
            pendingProposalListener.pendingProposal(proposal);
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
        pendingProposalListener.pendingProposal(proposal);

        // Assert
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS, null), capturedMessage);
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

        doThrow(new RuntimeException("Service unavailable")).when(snsNotificationService).notify(anyString(), anyString());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pendingProposalListener.pendingProposal(proposal);
        });
        assertEquals("Service unavailable", exception.getMessage());

        // Verify the message was correctly formed before the exception
        verify(snsNotificationService).notify(phoneCaptor.capture(), messageCaptor.capture());
        String capturedPhone = phoneCaptor.getValue();
        String capturedMessage = messageCaptor.getValue();
        assertEquals(accountEntity.getPhone(), capturedPhone);
        assertEquals(String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS, "Test Account"), capturedMessage);
    }
}