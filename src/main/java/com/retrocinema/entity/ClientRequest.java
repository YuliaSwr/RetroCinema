package com.retrocinema.entity;

import lombok.Data;

@Data
public class ClientRequest {

    private String name;
    private String email;
    private String phoneNumber;
    private String movie;
    private String date;
    private String time;
    private String type;
}
