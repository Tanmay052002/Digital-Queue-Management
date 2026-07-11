package com.queuemgmt.controller;

import com.queuemgmt.model.Office;
import com.queuemgmt.model.ServiceEntity;
import com.queuemgmt.model.User;
import com.queuemgmt.repository.OfficeRepository;
import com.queuemgmt.repository.ServiceRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class ServiceController {

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // shows the list of services for the office the user clicked on the dashboard
    @GetMapping("/office/{officeId}/services")
    public String showServices(@PathVariable Long officeId, HttpSession session, Model model) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        Optional<Office> officeOpt = officeRepository.findById(officeId);
        if (officeOpt.isEmpty()) {
            return "redirect:/dashboard";
        }

        List<ServiceEntity> services = serviceRepository.findByOfficeId(officeId);

        model.addAttribute("office", officeOpt.get());
        model.addAttribute("services", services);

        return "services";
    }
}
