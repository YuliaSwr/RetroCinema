package com.retrocinema.controller;

import com.retrocinema.entity.*;
import com.retrocinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.html.StyleSheet;

@Controller
public class ClientController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/logoutAdmin")
    public String logout(Model model) {
        model.addAttribute("request", new ClientRequest());
        return "index";
    }

    @GetMapping("/")
    public String get(Model model) {
        adminService.saveAdmin();
        model.addAttribute("request", new ClientRequest());
        return "index";
    }

    @PostMapping("/send")
    public String handle(Model model, @ModelAttribute ClientRequest request) {
        requestService.handleRequest(request);
        model.addAttribute("request", new ClientRequest());
        return "index";
    }

    @GetMapping("/mytickets")
    public String getTickets(Model model) {
        // for selection block
        model.addAttribute("search", new SearchRequest());
        return "my-tickets";
    }

    @PostMapping("/mytickets")
    public String findTickets(Model model, @ModelAttribute SearchRequest search) {
        model.addAttribute("search", new SearchRequest());
        model.addAttribute("tickets", ticketService.getAllByCustomer(search.getCustomerEmail()));
        return "my-tickets";
    }
}
