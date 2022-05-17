package com.retrocinema.entity;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assignment {

    @SequenceGenerator(
            name = "assigment_sequence",
            sequenceName = "assigment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assigment_sequence"
    )
    @Id
    private Long id;

    private String date;

    private Long ticketId;

    private Long operatorId;

    private String status = "WAITING";

    public boolean isInProcess() {
        return status.equals("IN_PROCESS");
    }
}
