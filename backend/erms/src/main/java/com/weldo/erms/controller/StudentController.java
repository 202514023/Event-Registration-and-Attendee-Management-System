package com.weldo.erms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weldo.erms.model.Event;
import com.weldo.erms.model.Registration;
import com.weldo.erms.repository.EventRepository;
import com.weldo.erms.repository.RegistrationRepository;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // STUDENT DASHBOARD
    @GetMapping("/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        if (session.getAttribute("student") == null) return "redirect:/";
        model.addAttribute("events", eventRepository.findAll());
        return "student-dashboard";
    }

    // VIEW EVENT DETAILS
    @GetMapping("/events/{id}")
    public String viewEvent(@PathVariable int id, HttpSession session, Model model) {
        if (session.getAttribute("student") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) return "redirect:/student/dashboard";

        String studentEmail = (String) session.getAttribute("studentEmail");
        boolean alreadyRegistered = registrationRepository.existsByEventIdAndEmail(id, studentEmail);

        model.addAttribute("event", eventOpt.get());
        model.addAttribute("alreadyRegistered", alreadyRegistered);
        return "student-event";
    }

    // REGISTER SELF FOR EVENT
    @PostMapping("/events/{id}/register")
    public String registerSelf(@PathVariable int id, HttpSession session, Model model) {
        if (session.getAttribute("student") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) return "redirect:/student/dashboard";

        Event event = eventOpt.get();
        String studentEmail = (String) session.getAttribute("studentEmail");

        // DUPLICATE CHECK
        if (registrationRepository.existsByEventIdAndEmail(id, studentEmail)) {
            model.addAttribute("event", event);
            model.addAttribute("alreadyRegistered", true);
            model.addAttribute("error", "You are already registered for this event!");
            return "student-event";
        }

        // SLOT CHECK
        if (event.getCurrentSlots() >= event.getMaxSlots()) {
            model.addAttribute("event", event);
            model.addAttribute("alreadyRegistered", false);
            model.addAttribute("error", "Sorry, this event is already full!");
            return "student-event";
        }

        // CREATE REGISTRATION
        Registration registration = new Registration();
        registration.setEventId(id);
        registration.setFirstName((String) session.getAttribute("studentFirstName"));
        registration.setLastName((String) session.getAttribute("studentLastName"));
        registration.setEmail(studentEmail);
        registration.setContactNumber((String) session.getAttribute("studentNumber"));
        registration.setAttendanceStatus("Pending");
        registrationRepository.save(registration);

        // UPDATE SLOT COUNT
        event.setCurrentSlots(event.getCurrentSlots() + 1);
        eventRepository.save(event);

        model.addAttribute("event", event);
        model.addAttribute("alreadyRegistered", true);
        model.addAttribute("success", "Successfully registered for this event!");
        return "student-event";
    }

    // UNREGISTER FROM EVENT
    @GetMapping("/events/{id}/unregister")
    public String unregister(@PathVariable int id, HttpSession session, Model model) {
        if (session.getAttribute("student") == null) return "redirect:/";

        String studentEmail = (String) session.getAttribute("studentEmail");
        Optional<Registration> regOpt = registrationRepository.findByEventIdAndEmail(id, studentEmail);

        if (regOpt.isPresent()) {
            registrationRepository.delete(regOpt.get());

            // DECREASE SLOT COUNT
            Optional<Event> eventOpt = eventRepository.findById(id);
            eventOpt.ifPresent(event -> {
                event.setCurrentSlots(event.getCurrentSlots() - 1);
                eventRepository.save(event);
            });
        }

        return "redirect:/student/events/" + id;
    }
}