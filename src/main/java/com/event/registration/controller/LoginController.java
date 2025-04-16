package com.event.registration.controller;


import com.event.registration.model.User;
import com.event.registration.repo.EventRepo;
import com.event.registration.repo.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@Component
@RequestMapping
public class LoginController {


    @Autowired
    UserRepo userRepo;
    @Autowired
    EventRepo eventRepo;


    @GetMapping("/")
    public String login() {
        userRepo.add(new User("admin", "123", true));
        return "login_page";
    }

    @PostMapping("/login_customer")
    public String login_customer(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        User user =userRepo.authenticate(username, password);
        if(user!=null) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/event/customer";
        }
        else {
            model.addAttribute("message","Invalid username or password" );
            return "login_page";
        }

    }

    @PostMapping("/login_admin")
    public String login_admin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if (username.equals("admin") && password.equals("123")) {
            session.setAttribute("user", new User("admin", "123", true));

            return "redirect:/event";
        } else {

            model.addAttribute("error", "Invalid username or password");
            return "login_page";
        }
    }

    @GetMapping("/register_page")
    public String register() {
        return "register_user";
    }

    //model is used to send data from controller to frontEnd html page
    @PostMapping("/register_user")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {

        User user = new User(username, password, false);
        if (!userRepo.isUserExist(username)) {
            userRepo.add(user);
            model.addAttribute("message", "Registration successful! Please log in.");
            return "login_page";

        } else {
            model.addAttribute("message", "User Name already exists! Please try again.");
            return "register_user";
        }


    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate session
        return "redirect:/"; // Redirect to login page
    }

    @PostMapping("/reset")
    public String restApplication(HttpSession session) {
        userRepo.clear();
        eventRepo.clear();
        session.invalidate();
        return "redirect:/"; // Redirect to login page
    }




}