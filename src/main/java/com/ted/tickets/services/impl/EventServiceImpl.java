package com.ted.tickets.services.impl;

import com.ted.tickets.entity.model.CreateEventRequest;
import com.ted.tickets.entity.Event;
import com.ted.tickets.entity.TicketType;
import com.ted.tickets.entity.User;
import com.ted.tickets.exceptions.UserNotFoundException;
import com.ted.tickets.repository.EventRepository;
import com.ted.tickets.repository.UserRepository;
import com.ted.tickets.services.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private  final UserRepository userRepository;
    private  final EventRepository eventRepository;


    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer =userRepository.findById(organizerId).orElseThrow(
                ()-> new UserNotFoundException(String.format("User not found with id " + organizerId))
        );

        List<TicketType> ticketTypes = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());

                    return ticketTypeToCreate;

                }).toList();

        Event eventToCreate = new Event();
        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypes);

        return eventRepository.save(eventToCreate);
    }
}
