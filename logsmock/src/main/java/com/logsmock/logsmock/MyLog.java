package com.logsmock.logsmock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class MyLog {

    private LocalDate date;
    private LocalTime time;
    private String username;
    private String message;
}
