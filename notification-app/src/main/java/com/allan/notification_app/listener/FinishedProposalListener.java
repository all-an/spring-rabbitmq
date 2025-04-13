package com.allan.notification_app.listener;

import com.allan.notification_app.constant.ConstantMessage;
import com.allan.notification_app.domain.ProposalEntity;
import com.allan.notification_app.service.SnsNotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FinishedProposalListener {

    @Autowired
    private SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.finishedproposal.queueToMsNotification}")
    public void finishedProposal(ProposalEntity proposal) {
        if (Objects.isNull(proposal)) {
            throw new IllegalArgumentException("Proposal is required");
        }
        String message = String.format(ConstantMessage.PROPOSAL_IN_ANALYSIS_FINISHED,
                proposal.getAccountEntity().getName(),
                proposal.getObservation());
        snsNotificationService.notify(proposal.getAccountEntity().getPhone(), message);
    }

}
