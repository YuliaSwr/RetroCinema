package com.retrocinema.service;

import com.retrocinema.entity.*;
import com.retrocinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatorService operatorService;

    public Assignment save(Ticket ticket) {
        assignmentRepository.findByTicketId(ticket.getId())
                .ifPresent((s) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The assignment for this ticket is already in system");
                });

        Assignment assignment =  new Assignment();
        assignment.setTicketId(ticket.getId());
        assignment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        assignment.setStatus("WAITING");

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAll() {
        return assignmentRepository.findAll();
    }

    public Assignment getByTicketId(Long ticketId) {
        return assignmentRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The assignment for this ticket is NOT in system"));
    }

    public List<Assignment> getByStatus(String status) {
        return assignmentRepository.findAllByStatus(status);
    }

    public void edit(EditAssignment request) {
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The assignment is NOT in system"));

        assignment.setOperatorId(request.getOperatorId());
        assignment.setStatus("IN_PROCESS");
        ticketRepository.findById(assignment.getTicketId()).get().setStatus("IN_PROCESS");

        assignmentRepository.save(assignment);
    }

    public void deleteById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The assignment is NOT in system"));

        if (assignment.getOperatorId() != null) {
            operatorService.getById(assignment.getOperatorId()).setIsAvailable(true);
        }

        assignmentRepository.deleteById(id);
    }

    public void setAsDone(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "The assignment is NOT in system"));

        if (!assignment.getStatus().equals("IN_PROCESS")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This assignment is NOT in process");
        }

        operatorService.getById(assignment.getOperatorId()).setIsAvailable(true);

        assignment.setStatus("DONE");
        ticketRepository.findById(assignment.getTicketId()).get().setStatus("DONE");
        assignmentRepository.save(assignment);
    }
}
