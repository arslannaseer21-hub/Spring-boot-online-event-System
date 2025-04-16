package com.event.registration.model;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class User {
    private String username;
    private String password;
    private boolean isAdmin;
    private List<Event> events;

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.events = new ArrayList<Event>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }
    public void removeEvent(String event) {
        Iterator<Event> iterator = this.events.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getName().equals(event)) {
                iterator.remove();
            }
        }
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
    



}
