package com.event.registration.model;


import lombok.Setter;

import java.util.ArrayList;
import java.util.List;



public class Event {
    private String name;
    private String date;
    private String location;
    private List<User> registeredUsers=new ArrayList<>();

    public Event(String name, String date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
    }

    public boolean registerUser(User user) {
        if (!registeredUsers.contains(user)) {
            registeredUsers.add(user);
            return true;
        }
        return false;
    }

    public void unregisterUser(User user) {
        registeredUsers.remove(user);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(List<User> registeredUsers) {
		this.registeredUsers = registeredUsers;
	}
    
    
}
