package com.weldo.erms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weldo.erms.model.Event;
import com.weldo.erms.repository.EventRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // this opens a new page to createeeee the thingies events wuwoadnas
    @GetMapping("/new")
    public String newEventForm(HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";
        return "event-create";
    }

    // this handles addign of events oasjndfkoafn
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

    // this deletes events ong
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";
        eventRepository.deleteById(id);
        return "redirect:/dashboard";
    }
}