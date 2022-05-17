package com.retrocinema.repository;

import com.retrocinema.entity.Operator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperatorRepository extends CrudRepository<Operator, Long> {

    List<Operator> findAll();

    Optional<Operator> findByEmployeeCode(String employeeCode);

    List<Operator> findAllByIsAvailable(boolean isAvailbale);
}
