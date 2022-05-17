package com.retrocinema.service;

import com.retrocinema.entity.Customer;
import com.retrocinema.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return customerRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User" + email + " is NOT found!"));
//    }


    public Customer save(Customer customer) {
        customerRepository.findByEmail(customer.getEmail())
                .ifPresent((s) -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already in system");
                });
        return customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is NOT in system"));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is wrong"));
    }

    public void update(Customer customer) {
        customerRepository.save(customer);
    }
}
