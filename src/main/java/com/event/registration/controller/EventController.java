package com.event.registration.controller;


import com.event.registration.model.Event;
import com.event.registration.model.User;
import com.event.registration.repo.EventRepo;
import com.event.registration.repo.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Component
@RequestMapping("/event")
public class EventController {

    private List<User> registeredUsers=new ArrayList<>();

    @Autowired
    EventRepo eventRepo;

    @Autowired
    UserRepo userRepo;




    @GetMapping
    public String showEvents(Model model) {

        model.addAttribute("events", eventRepo.getEvents());
        return "admin_page";
    }

    @GetMapping("/customer")
    public String customerDashboard(Model model,HttpSession httpSession) {
        User user=(User)httpSession.getAttribute("user");
        httpSession.setAttribute("registeredEvents", userRepo.getEventsFromUser(user));


        model.addAttribute("events", eventRepo.getEvents());
       return "customer_page";
    }

    @PostMapping("/add_event")
    public String addEvent(@RequestParam String eventName, @RequestParam String date, @RequestParam String location, Model model) {
        eventRepo.add(new Event(eventName, date, location));
        model.addAttribute("message", "Event added");
        return "redirect:/event";


    }

    @PostMapping("/delete_event")
    public String deleteEvent(@RequestParam String eventNameDelete, Model model) {
       if(eventRepo.removeEvent(eventNameDelete)) {
           userRepo.removeEvent(eventNameDelete);
           model.addAttribute("message", "Event removed");
           model.addAttribute("events", eventRepo.getEvents());
       }
       return "redirect:/event";
    }
    // View users registered for an event
    @GetMapping("/view_users")
    public String viewUsers(@RequestParam String eventNameUsers, Model model) {
        Event event = eventRepo.findEventByName(eventNameUsers);
        if (event != null) {
            model.addAttribute("event", event);
            model.addAttribute("users", event.getRegisteredUsers());
            model.addAttribute("message", "view Users");
        } else {
            model.addAttribute("message", "Event not found.");
        }
        return "event_users";
    }


    @GetMapping("/event/register_customer")
    public String registerForm(@RequestParam String eventName,Model model) {

        Event event = eventRepo.findEventByName(eventName);
        model.addAttribute("event", event);
        return "form_register_for_event";

    }


    @PostMapping("/register_event")
    public String registerEvent(@RequestParam String  eventName, RedirectAttributes model, HttpSession httpSession) {

        User user=(User) httpSession.getAttribute("user");
        Event event=eventRepo.findEventByName(eventName);

        if(user==null || event==null || userRepo.isEventExistWithUser(eventName,user)) {
            model.addFlashAttribute("message", "Already Registered..");
            return "redirect:/event/customer";
        }

        eventRepo.updateEvent(event,user);
        User updatedUser=userRepo.addEventToUser(user,event);
        httpSession.setAttribute("user",updatedUser);
        httpSession.setAttribute("registeredEvents", user.getEvents());

        model.addFlashAttribute("message", "registered successfully!");
        return "redirect:/event/customer";


    }



    @PostMapping("event/unregister_event")
    public String unregisterEvent(@RequestParam String eventNameUnReg, Model model,HttpSession httpSession) {
        User user=(User)httpSession.getAttribute("user");

        eventRepo.unRegisterUser(eventNameUnReg,user);
        user=userRepo.removeEveentFromUser(user,eventNameUnReg);

        httpSession.setAttribute("user",user);
        System.out.println(user.getEvents().size());
        httpSession.setAttribute("registeredEvents", user.getEvents());

        return "redirect:/event/customer";

    }









}
