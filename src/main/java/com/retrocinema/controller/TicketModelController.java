package com.retrocinema.controller;

import com.retrocinema.entity.*;
import com.retrocinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/ticket")
public class TicketModelController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CustomerService customerService;

    List<String> MOVIES = Arrays.asList("LaLaLand", "Titanic", "GreenBook", "KillBill", "JohnWick", "Deadpool");

    @PostMapping()
    public String save(Model model, @ModelAttribute Ticket ticket) {
        ticketService.save(ticket);
        return "redirect:/admin/ticket";
    }

    @GetMapping()
    public String getAll(Model model) {

        setAttributesForPage(model);

        return "admin-panel-tickets";
    }

    private void setAttributesForPage(Model model) {
        // for statistic block
        for (String movie : MOVIES) {
            model.addAttribute("number_" + movie, ticketService.getAmountOfMovie(movie));
        }

        // for registration form
        model.addAttribute("customers", customerService.getAll());

        // for selection block
        model.addAttribute("search", new SearchRequest());

        // for queue table
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("tickets", ticketService.getAll());
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        ticketService.deleteById(id);
        return "redirect:/admin/ticket";
    }

    @GetMapping("/search")
    public String getAllByCustomerEmail(Model model, @ModelAttribute SearchRequest searchRequest) {

        if (searchRequest.getCustomerEmail().equals("ALL")) {
            return "redirect:/admin/ticket";
        }

        setAttributesForPage(model);
        model.addAttribute("tickets", ticketService.getAllByCustomer(searchRequest.getCustomerEmail()));
        return "admin-panel-tickets";
    }
}
