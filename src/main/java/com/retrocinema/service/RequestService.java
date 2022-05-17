package com.retrocinema.service;

import com.retrocinema.entity.*;
import com.retrocinema.repository.CustomerRepository;
import com.retrocinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public void handleRequest(ClientRequest request) {
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());

        Ticket ticket = new Ticket();
        ticket.setMovie(request.getMovie());
        ticket.setDate(request.getDate());
        ticket.setTime(request.getTime());
        ticket.setCustomerEmail(request.getEmail());
        ticket.setType(request.getType());
        ticket.setStatus("WAITING");

        if (!customerRepository.existsByEmail(request.getEmail())) {
            customerRepository.save(customer);
        }

        ticketRepository.save(ticket);
    }
}
