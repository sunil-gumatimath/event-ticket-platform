package com.ted.tickets.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;

    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;

    private List<CreateTicketTypeResponseDto> ticketTypes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
