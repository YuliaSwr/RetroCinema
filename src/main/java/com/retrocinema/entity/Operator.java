package com.retrocinema.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Operator {

    @SequenceGenerator(
            name = "operator_sequence",
            sequenceName = "operator_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "operator_sequence"
    )
    @Id
    private Long id;

    private String employeeCode;

    private String lastName;

    private String firstName;

    private Boolean isAvailable = true;
}
