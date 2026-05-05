package com.weldo.erms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weldo.erms.model.Admin;
import com.weldo.erms.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    // SHOW LOGIN PAGE
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // SHOW DASHBOARDDDDDDDD
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/";  
        }
        return "dashboard";
}

    // HANDLE LOGIN FORM SUBMIT
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Admin admin = adminRepository.findByUsernameAndPassword(username, password);

        if (admin != null) {
            session.setAttribute("admin", admin.getFullName());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error");
            return "login";
        }
    }

    // LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}