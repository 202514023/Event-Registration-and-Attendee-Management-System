package com.weldo.erms.controller;

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
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    // SHOW REGISTRATION FORM
    @GetMapping("/new/{eventId}")
    public String registerForm(@PathVariable int eventId,
                               HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) return "redirect:/dashboard";

        model.addAttribute("event", eventOpt.get());
        return "register";
    }

    // HANDLE REGISTRATION FORM SUBMISSION
    @PostMapping("/new/{eventId}")
    public String register(@PathVariable int eventId,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String email,
                           @RequestParam String contactNumber,
                           HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) return "redirect:/dashboard";

        Event event = eventOpt.get();

        // SLOT CHECK - is the event full?
        if (event.getCurrentSlots() >= event.getMaxSlots()) {
            model.addAttribute("event", event);
            model.addAttribute("error", "Sorry, this event is already full!");
            return "register";
        }

        // CREATE REGISTRATION
        Registration registration = new Registration();
        registration.setEventId(eventId);
        registration.setFirstName(firstName);
        registration.setLastName(lastName);
        registration.setEmail(email);
        registration.setContactNumber(contactNumber);
        registration.setAttendanceStatus("Pending");
        registrationRepository.save(registration);

        // UPDATE SLOT COUNT
        event.setCurrentSlots(event.getCurrentSlots() + 1);
        eventRepository.save(event);

        return "redirect:/events/" + eventId;
    }

    // MARK ATTENDANCE
    @GetMapping("/attend/{id}/{status}")
    public String markAttendance(@PathVariable int id,
                                 @PathVariable String status,
                                 HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        Optional<Registration> regOpt = registrationRepository.findById(id);
        if (regOpt.isEmpty()) return "redirect:/dashboard";

        Registration reg = regOpt.get();
        reg.setAttendanceStatus(status);
        registrationRepository.save(reg);

        return "redirect:/events/" + reg.getEventId();
    }

    // DELETE REGISTRATION
    @GetMapping("/delete/{id}/{eventId}")
    public String deleteRegistration(@PathVariable int id,
                                     @PathVariable int eventId,
                                     HttpSession session) {
        if (session.getAttribute("admin") == null) return "redirect:/";

        registrationRepository.deleteById(id);

        // DECREASE SLOT COUNT
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        eventOpt.ifPresent(event -> {
            event.setCurrentSlots(event.getCurrentSlots() - 1);
            eventRepository.save(event);
        });

        return "redirect:/events/" + eventId;
    }
}