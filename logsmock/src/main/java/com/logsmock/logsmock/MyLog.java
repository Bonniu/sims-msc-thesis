package com.logsmock.logsmock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class MyLog implements Comparable<MyLog> {

    private LocalDate date;
    private LocalTime time;
    private String username;
    private String message;
    private String type;

    public MyLog(LocalDate date, LocalTime time, String username, String message) {
        this(date, time, username, message, " INFO");
    }

    @Override
    public int compareTo(MyLog b) {
        if (this.getDate().isEqual(b.getDate())) {
            if (this.getTime().equals(b.getTime())) {
                return 0;
            }
            return this.getTime().isBefore(b.getTime()) ? -1 : 1;
        }
        return this.getDate().isBefore(b.getDate()) ? -1 : 1;

    }

    @Override
    public String toString() {
        return
                getDate() + "T" + resolveTimeString() + ".000+01:00 " //date and time
                        + getType() // log type
                        + " 17948 --- [           main] c.logsmock.logsmock.LogsmockApplication  : " // random, not used
                        + getMessage(); // message
    }

    private String resolveTimeString() {
        if (getTime().toString().length() < 8)
            return getTime().toString() + ":00";
        else
            return getTime().toString();
    }
}
