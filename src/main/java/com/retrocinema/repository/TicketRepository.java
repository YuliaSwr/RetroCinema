package com.retrocinema.repository;

import com.retrocinema.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findById(Long id);

    List<Ticket> findAll();

    List<Ticket> findAllByMovieIgnoreCase(String movie);

    List<Ticket> findAllByCustomerEmail(String email);
}
