package com.MDM.demo.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MDM.demo.entity.Event;
import com.MDM.demo.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
    existingEvent.setName(updatedEvent.getName());
    existingEvent.setDescription(updatedEvent.getDescription());
    existingEvent.setStartTime(updatedEvent.getStartTime());
    existingEvent.setEndTime(updatedEvent.getEndTime());
    return eventRepository.save(existingEvent);
        }
        return null;
    }

    public void deleteEvent(Long id) {
     eventRepository.deleteById(id);
    }
}

