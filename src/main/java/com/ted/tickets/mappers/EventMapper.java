package com.ted.tickets.mappers;

import com.ted.tickets.domain.model.UpdateEventRequest;
import com.ted.tickets.domain.model.UpdateTicketTypeRequest;
import com.ted.tickets.dto.request.UpdateEventRequestDto;
import com.ted.tickets.dto.request.UpdateTicketTypeRequestDto;
import com.ted.tickets.dto.response.*;
import com.ted.tickets.entity.Event;
import com.ted.tickets.dto.request.CreateEventRequestDto;
import com.ted.tickets.dto.request.CreateTicketTypeRequestDto;
import com.ted.tickets.domain.model.CreateEventRequest;
import com.ted.tickets.domain.model.CreateTicketTypeRequest;
import com.ted.tickets.entity.TicketType;
import jdk.jfr.EventType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto (Event event);

    GetEventTicketTypesResponseDto toGetEventDetailsTicketTypesResponseDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto  toListPublishedEventResponseDto(Event event);

    GetPublishedEventTicketTypesResponseDto toGetPublishedEventTicketTypesResponseDto(EventType eventType);

    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);
}
