package com.ted.tickets.controllers;

import com.sun.jdi.request.EventRequest;
import com.ted.tickets.domain.model.CreateEventRequest;
import com.ted.tickets.domain.model.UpdateEventRequest;
import com.ted.tickets.dto.request.CreateEventRequestDto;
import com.ted.tickets.dto.request.UpdateEventRequestDto;
import com.ted.tickets.dto.response.CreateEventResponseDto;
import com.ted.tickets.dto.response.GetEventDetailsResponseDto;
import com.ted.tickets.dto.response.ListEventResponseDto;
import com.ted.tickets.dto.response.UpdateEventResponseDto;
import com.ted.tickets.entity.Event;
import com.ted.tickets.mappers.EventMapper;
import com.ted.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event updateEvent = eventService.updateEventForOrganizer(
                userId, eventId ,updateEventRequest
        );

        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(updateEvent);
        return ResponseEntity.ok(updateEventResponseDto);

    }

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
    ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new  ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
    ){
        UUID userId = parseUserId(jwt);
        Page<Event> events = eventService.listEventsForOrganizer(userId, pageable);

        return ResponseEntity.ok(
                events.map(eventMapper::toListEventResponseDto)
        );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ){
        UUID userid =  parseUserId(jwt);
        return eventService.getEventForOrganizer(userid, eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    private UUID parseUserId(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
