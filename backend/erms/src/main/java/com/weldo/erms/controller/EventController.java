package com.weldo.erms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weldo.erms.model.Event;
import com.weldo.erms.model.Registration;
import com.weldo.erms.repository.EventRepository;
import com.weldo.erms.repository.RegistrationRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // opens add event page
    @GetMapping("/new")
    public String newEventForm(HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";
        return "event-create";
    }

    // handles adding of events
    @PostMapping("/new")
    public String addEvent(@RequestParam String eventName,
                           @RequestParam String description,
                           @RequestParam String eventDate,
                           @RequestParam String location,
                           @RequestParam int maxSlots,
                           HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        int adminId = (int) session.getAttribute("adminId");
        Event event = new Event();
        event.setEventName(eventName);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setLocation(location);
        event.setMaxSlots(maxSlots);
        event.setCurrentSlots(0);
        event.setCreatedBy(adminId);
        eventRepository.save(event);
        return "redirect:/dashboard";
    }

    // deletes event
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";
        eventRepository.deleteById(id);
        return "redirect:/dashboard";
    }

    // shows event view page with registrations list
    @GetMapping("/{id}")
    public String viewEvent(@PathVariable int id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) return "redirect:/dashboard";

        List<Registration> registrations = registrationRepository.findByEventId(id);

        model.addAttribute("event", eventOpt.get());
        model.addAttribute("registrations", registrations);
        return "event-view";
    }

    // shows edit event form with pre-filled data
    @GetMapping("/edit/{id}")
    public String editEventForm(@PathVariable int id, HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) return "redirect:/dashboard";

        model.addAttribute("event", eventOpt.get());
        return "event-edit";
    }

    // handles edit event form submission
    @PostMapping("/edit/{id}")
    public String editEvent(@PathVariable int id,
                            @RequestParam String eventName,
                            @RequestParam String description,
                            @RequestParam String eventDate,
                            @RequestParam String location,
                            @RequestParam int maxSlots,
                            HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isEmpty()) return "redirect:/dashboard";

        Event event = eventOpt.get();
        event.setEventName(eventName);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setLocation(location);
        event.setMaxSlots(maxSlots);
        eventRepository.save(event);
        return "redirect:/dashboard";
    }
}