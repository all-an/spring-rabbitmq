package com.allan.proposal_app.controller;

import com.allan.proposal_app.dto.ProposalRequestDto;
import com.allan.proposal_app.dto.ProposalResponseDto;
import com.allan.proposal_app.response.ApiResponse;
import com.allan.proposal_app.response.ApiStatus;
import com.allan.proposal_app.service.ProposalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProposalControllerTest {

    @InjectMocks
    private ProposalController proposalController;

    @Mock
    private ProposalService proposalService;

    @Test
    void createProposal_ReturnsSuccessResponse() {
        // Arrange
        ProposalRequestDto requestDto = new ProposalRequestDto();
        ProposalResponseDto responseDto = new ProposalResponseDto();
        responseDto.setId(1L);

        when(proposalService.create(requestDto)).thenReturn(responseDto);

        // Act
        ApiResponse<ProposalResponseDto> response = proposalController.create(requestDto);

        // Assert
        assertEquals(ApiStatus.CREATED.getCode(), response.getStatus());
        assertEquals(ApiStatus.CREATED.getMessage(), response.getMessage());
        assertEquals(responseDto, response.getData());
    }

}
