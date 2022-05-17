package com.retrocinema.service;

import com.retrocinema.entity.Operator;
import com.retrocinema.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    public Operator save(Operator operator) {
        operatorRepository.findByEmployeeCode(operator.getEmployeeCode())
                .ifPresent((s) -> {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This employee code is already in system");
                        });
        return operatorRepository.save(operator);
    }

    public List<Operator> getAll() {
        return operatorRepository.findAll();
    }

    public Operator getById(Long id) {
        return operatorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "This employee is NOT in system"));
    }

    public List<Operator> getAllAvailable() {
        return operatorRepository.findAllByIsAvailable(true);
    }

    public void setNonAvailable(Long id) {
        Operator operator = operatorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "This employee is NOT in system"));
        operator.setIsAvailable(false);
        operatorRepository.save(operator);
    }

    public void deleteById(Long id) {
        Operator operator = operatorRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is not such operator"));
        operatorRepository.delete(operator);
    }
}
