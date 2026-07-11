package com.queuemgmt.controller;

import com.queuemgmt.model.User;
import com.queuemgmt.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {

        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user == null) {
            // wrong username/password
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

        // save logged in user in session - very basic, no spring security here
        session.setAttribute("loggedInUser", user);

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
