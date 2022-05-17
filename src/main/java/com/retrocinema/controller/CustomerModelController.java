package com.retrocinema.controller;

import com.retrocinema.entity.*;
import com.retrocinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/customer")
public class CustomerModelController {

    @Autowired
    private CustomerService customerService;


    @PostMapping()
    public String register(Model model, @ModelAttribute Customer customer) {
        if (customer.getId() != null) {
            customerService.update(customer);
        } else {
            customerService.save(customer);
        }
        return "redirect:/admin/customer";
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customers", customerService.getAll());
        return "admin-panel-customers";
    }

    @GetMapping( "/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("customers", customerService.getAll());
        model.addAttribute("customer", customerService.getById(id));
        return "admin-panel-customers";
    }


    @PostMapping( "/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/admin/customer";
    }
}
