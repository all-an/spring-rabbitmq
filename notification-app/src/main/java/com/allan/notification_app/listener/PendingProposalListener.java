package com.allan.notification_app.listener;

import com.allan.notification_app.constant.ConstantMessage;
import com.allan.notification_app.domain.ProposalEntity;
import com.allan.notification_app.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PendingProposalListener {

    private SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void pendingProposal(ProposalEntity proposal) {
        if (Objects.isNull(proposal)) {
            throw new RuntimeException("Proposal is required");
        }
        String message = String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS, proposal.getAccountEntity().getName());
        snsNotificationService.notify(message);
    }

}
