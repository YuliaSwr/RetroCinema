package com.retrocinema.controller;

import com.retrocinema.entity.*;
import com.retrocinema.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/assignment")
public class AssignmentModelController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private OperatorService operatorService;

    @PostMapping()
      public String edit(Model model, @ModelAttribute EditAssignment editAssignment) {
        assignmentService.edit(editAssignment);

        operatorService.setNonAvailable(editAssignment.getOperatorId());
        return "redirect:/admin/assignment";
    }

    @GetMapping()
    public String getAll(Model model) {

        // for statistic block
        model.addAttribute("amount_waiting", assignmentService.getByStatus("WAITING").size());
        model.addAttribute("amount_inprocess", assignmentService.getByStatus("IN_PROCESS").size());
        model.addAttribute("amount_done", assignmentService.getByStatus("DONE").size());

        // for register form to set an operator to waiting assignment
        model.addAttribute("available_operators", operatorService.getAllAvailable());
        model.addAttribute("waiting_assignments", assignmentService.getByStatus("WAITING"));
        model.addAttribute("edit_assignment", new EditAssignment());

        // for queue table
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("assignments", assignmentService.getAll());

        return "admin-panel-assignments";
    }

    @PostMapping("/done/{id}")
    public String setAsDone(@PathVariable Long id) {
        assignmentService.setAsDone(id);
        return "redirect:/admin/assignment";
    }

    @PostMapping( "/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        assignmentService.deleteById(id);
        return "redirect:/admin/assignment";
    }
}
