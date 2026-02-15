package com.ted.tickets.mappers;

import com.ted.tickets.entity.Event;
import com.ted.tickets.dto.request.CreateEventRequestDto;
import com.ted.tickets.dto.request.CreateTicketTypeRequestDto;
import com.ted.tickets.dto.response.CreateEventResponseDto;
import com.ted.tickets.domain.model.CreateEventRequest;
import com.ted.tickets.domain.model.CreateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);


}
