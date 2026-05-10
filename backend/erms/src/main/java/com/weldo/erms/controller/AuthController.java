package com.weldo.erms.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weldo.erms.model.Admin;
import com.weldo.erms.repository.AdminRepository;
import com.weldo.erms.repository.EventRepository;
import com.weldo.erms.repository.RegistrationRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // SHOW LOGIN PAGE
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // HANDLE LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Admin admin = adminRepository.findByUsernameAndPassword(username, password);

        if (admin != null) {
            System.out.println("checks for id sicne ayaw gumana: " + admin.getUserId());
            session.setAttribute("admin", admin.getFullName()); /// this stores the name and displays
            session.setAttribute("adminId", admin.getUserId()); /// this stores the adminId for creation
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Spy ka ata boi!");
            return "login";
        }
    }

    // SHOW DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/";
        }

        String today = LocalDate.now().toString();

        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("totalEvents", eventRepository.count());
        model.addAttribute("upcomingEvents", eventRepository.countByEventDateGreaterThanEqual(today));

        model.addAttribute("totalRegistrations", registrationRepository.count());
        model.addAttribute("totalPresent", registrationRepository.countByAttendanceStatus("Present"));

        return "dashboard";
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}