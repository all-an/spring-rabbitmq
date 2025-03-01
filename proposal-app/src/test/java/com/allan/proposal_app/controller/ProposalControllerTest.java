package com.allan.proposal_app.controller;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.service.ProposalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProposalControllerTest {

    @InjectMocks
    private ProposalController proposalController;

    @Mock
    private ProposalService proposalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProposalController_whenNullRequest_thenThrowsException() {
        // Arrange: Mock service behavior
        when(proposalService.create(null))
                .thenThrow(new RuntimeException("proposalRequestDto is required"));

        // Act & Assert: Verify exception is thrown
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> proposalController.create(null)
        );

        assertEquals("proposalRequestDto is required", exception.getMessage());
    }

    @Test
    void testProposalController_whenValidRequest_thenReturnsResponse() {
        ProposalRequestDto requestDto = new ProposalRequestDto();
        ProposalResponseDto responseDto = new ProposalResponseDto();

        when(proposalService.create(requestDto)).thenReturn(responseDto);

        ResponseEntity<ProposalResponseDto> response = proposalController.create(requestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }


}
