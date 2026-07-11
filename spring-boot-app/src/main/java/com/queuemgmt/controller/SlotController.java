package com.queuemgmt.controller;

import com.queuemgmt.model.Counter;
import com.queuemgmt.model.ServiceEntity;
import com.queuemgmt.model.User;
import com.queuemgmt.repository.CounterRepository;
import com.queuemgmt.repository.ServiceRepository;
import com.queuemgmt.service.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class SlotController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CounterRepository counterRepository;

    @Autowired
    private TokenService tokenService;

    // shows how many free slots each counter has for the office that offers this service
    @GetMapping("/service/{serviceId}/slots")
    public String showSlots(@PathVariable Long serviceId, HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Optional<ServiceEntity> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isEmpty()) {
            return "redirect:/dashboard";
        }

        ServiceEntity service = serviceOpt.get();
        List<Counter> counters = counterRepository.findByOfficeId(service.getOfficeId());

        // build a simple list of maps: counter number + free slots
        // (would normally make a DTO class for this but keeping it quick)
        List<Map<String, Object>> slotInfo = new ArrayList<>();
        for (Counter c : counters) {
            Map<String, Object> row = new HashMap<>();
            row.put("counterNumber", c.getCounterNumber());
            row.put("maxSlots", c.getMaxSlots());
            row.put("availableSlots", tokenService.getAvailableSlots(c));
            slotInfo.add(row);
        }

        model.addAttribute("service", service);
        model.addAttribute("slotInfo", slotInfo);

        return "slot-availability";
    }
}
