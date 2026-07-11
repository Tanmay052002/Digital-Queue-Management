package com.queuemgmt.controller;

import com.queuemgmt.model.Office;
import com.queuemgmt.model.User;
import com.queuemgmt.repository.OfficeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private OfficeRepository officeRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // not logged in, kick back to login
            return "redirect:/login";
        }

        List<Office> offices = officeRepository.findAll();

        model.addAttribute("user", loggedInUser);
        model.addAttribute("offices", offices);

        return "dashboard";
    }
}
