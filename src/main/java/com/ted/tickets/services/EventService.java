package com.ted.tickets.services;

import com.ted.tickets.entity.model.CreateEventRequest;
import com.ted.tickets.entity.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
