package com.retrocinema.controller;

import com.retrocinema.entity.*;
import com.retrocinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/operator")
public class OperatorModelController {

    @Autowired
    private OperatorService operatorService;

    @PostMapping()
    public String register(Model model, @ModelAttribute Operator operator) {
        operatorService.save(operator);
        return "redirect:/admin/operator";
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("operator", new Operator());
        model.addAttribute("operators", operatorService.getAll());
        return "admin-panel-operators";
    }

    @PostMapping( "/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        operatorService.deleteById(id);
        return "redirect:/admin/operator";
    }
}
