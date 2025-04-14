package com.allan.proposal_app.service;

import com.allan.proposal_app.dto.ProposalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void notify(ProposalResponseDto proposalResponseDto) {
        simpMessagingTemplate.convertAndSend("/proposal", proposalResponseDto);
    }

}
