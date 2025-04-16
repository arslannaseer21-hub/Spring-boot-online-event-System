package com.event.registration.repo;

import com.event.registration.model.Event;
import com.event.registration.model.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class EventRepo {
    List<Event> events = new ArrayList<Event>();

    /*
    public EventRepo() {
        Event event = new Event("Tech Conference", "2025-04-15", "New York");
        add(event);
        add(new Event("Music Festival", "2025-05-20", "Los Angeles"));
        add(new Event("Coding Bootcamp", "2025-06-10", "San Francisco"));

    }*/

    public void add(Event event) {
        events.add(event);
    }

    public void remove(Event event) {
        events.remove(event);
    }

    public void updateEvent(Event event, User user) {
        for (Event e : events) {
            if (e.getName().equalsIgnoreCase(event.getName()) && e.getDate().equals(event.getDate()) && e.getLocation().equals(event.getLocation())) {
                e.registerUser(user);
                return;
            }
        }
    }
    public void unRegisterUser(String eventName, User user) {
        for (Event e : events) {
            if (e.getName().equalsIgnoreCase(eventName)) {
                e.unregisterUser(user);
            }
        }
    }


    public Event findEventByName(String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    public boolean removeEvent(String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                events.remove(event);
                return true;
            }
        }
        return false;
    }

    public void clear() {
        events.clear();
    }

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
    
}
