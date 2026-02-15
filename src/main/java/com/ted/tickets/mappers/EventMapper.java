package com.ted.tickets.mappers;

import com.ted.tickets.entity.Event;
import com.ted.tickets.entity.dto.CreateEventRequestDto;
import com.ted.tickets.entity.dto.CreateEventResponseDto;
import com.ted.tickets.entity.dto.CreateTicketTypeRequestDto;
import com.ted.tickets.entity.model.CreateEventRequest;
import com.ted.tickets.entity.model.CreateTicketTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);


}
