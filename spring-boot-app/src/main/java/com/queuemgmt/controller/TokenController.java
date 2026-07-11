package com.queuemgmt.controller;

import com.queuemgmt.model.Counter;
import com.queuemgmt.model.ServiceEntity;
import com.queuemgmt.model.Token;
import com.queuemgmt.model.User;
import com.queuemgmt.repository.NotificationRepository;
import com.queuemgmt.repository.ServiceRepository;
import com.queuemgmt.model.Notification;
import com.queuemgmt.service.NotificationClientService;
import com.queuemgmt.service.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class TokenController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private NotificationClientService notificationClientService;

    @Autowired
    private NotificationRepository notificationRepository;

    // confirmation page before generating - shows service name + generate button
    @GetMapping("/service/{serviceId}/generate-token")
    public String generateTokenPage(@PathVariable Long serviceId, HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return "redirect:/dashboard";
        }

        model.addAttribute("service", serviceOpt.get());
        return "generate-token";
    }

    // actually creates the token when the user clicks "Generate Token"
    @PostMapping("/service/{serviceId}/generate-token")
    public String generateToken(@PathVariable Long serviceId, HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return "redirect:/dashboard";
        }

        ServiceEntity service = serviceOpt.get();

        // find the counter with the least people waiting for this office
        Counter counter = tokenService.pickBestCounter(service.getOfficeId());

        if (counter == null) {
            model.addAttribute("errorMessage", "No counters configured for this office yet.");
            return "generate-token";
        }

        Token token = tokenService.generateToken(loggedInUser.getId(), serviceId, counter);

        // try to send a notification through the .NET microservice
        String message = "Your token " + token.getTokenNumber() + " for " + service.getName() + " has been generated.";
        boolean sent = notificationClientService.sendNotification(token.getId(), token.getTokenNumber(), message);

        Notification notification = new Notification(
                token.getId(),
                message,
                LocalDateTime.now(),
                sent ? "SENT" : "FAILED"
        );
        notificationRepository.save(notification);

        model.addAttribute("token", token);
        model.addAttribute("service", service);
        model.addAttribute("counter", counter);
        model.addAttribute("notificationSent", sent);

        return "token-result";
    }
}
