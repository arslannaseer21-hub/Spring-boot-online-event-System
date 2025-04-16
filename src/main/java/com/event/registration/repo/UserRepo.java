package com.event.registration.repo;

import com.event.registration.model.Event;
import java.util.Iterator;

import com.event.registration.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepo {

    List<User> users=new ArrayList<User>();
    public void add(User user) {
        users.add(user);
    }
    public void remove(User user) {
        users.remove(user);
    }

    public boolean isUserExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username) && !user.isAdmin()) {
                return true;
            }
        }
        return false;
    }
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && !user.isAdmin()) {
                return user;
            }
        }
        return null;
    }

    public User addEventToUser(User user, Event event) {
        for (User user1 : users) {
            if(user1.getUsername().equalsIgnoreCase(user.getUsername())) {
                user1.addEvent(event);
                return user1;
            }
        }
        return null;

    }
    public User removeEveentFromUser(User user, String eventName) {
        for (User user1 : users) {
            if(user1.getUsername().equalsIgnoreCase(user.getUsername())) {
                user1.removeEvent(eventName);
                return user1;
            }
        }
        return null;
    }

    public List<Event> getEventsFromUser(User user) {
        for (User user1 : users) {
            if(user1.getUsername().equalsIgnoreCase(user.getUsername())) {
                return user1.getEvents();
            }
        }
        return new ArrayList<>();
    }

    public void removeEvent(String eventNameDelete) {
        for (User user1 : users) {
            Iterator<Event> iterator = user1.getEvents().iterator(); // ✅ Use an iterator
            while (iterator.hasNext()) {
                Event event = iterator.next();
                if (event.getName().equalsIgnoreCase(eventNameDelete)) {
                    iterator.remove();
                }
            }
        }
    }

    public boolean isEventExistWithUser(String eventName, User user) {
        for (User user1 : users) {
            if(user1.getUsername().equalsIgnoreCase(user.getUsername())) {
                for (Event event : user1.getEvents()) {
                    if(event.getName().equalsIgnoreCase(eventName)) {
                        return true;
                    }
                }
                return false;
            }

        }
        return false;
    }

    public void clear() {
        users.clear();
    }
}
