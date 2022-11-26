package com.logsmock.logsmock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.logsmock.logsmock.LogMessages.*;
import static com.logsmock.logsmock.TimeCalculator.addRandomnessToTime;
import static com.logsmock.logsmock.TimeCalculator.countTime;


@Slf4j
@SpringBootApplication
public class LogsmockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogsmockApplication.class, args);
        generateNormalUser("jkiwior", 8, 0, 8);

    }

    public static void generateNormalUser(String username, int startHour, int startMinutes, int workTime) {
        int requestperiod = 15; // minutes

        var list = new ArrayList<MyLog>();
        // days between 14-27 november
        for (int i = 14; i < 28; i++) {
            if (i == 19 || i == 20) continue; // weekend
            var today = LocalDate.of(2022, 11, i);
            // start work
            var startTime = addRandomnessToTime(startHour, startMinutes, 3);
            list.add(new MyLog(today, startTime, username, MessageFormat.format(LOGGED_TO_WORK, username)));

            //work time
            var time = startTime.plus(15, ChronoUnit.MINUTES);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(827, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(302, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(47, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(RESERVATION, username)));
            time = time.plus(1503, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(7815, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(65, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(RESERVATION, username)));
            time = time.plus(650, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            time = time.plus(15, ChronoUnit.SECONDS);
            list.add(new MyLog(today, time, username, MessageFormat.format(RESERVATION, username)));


            //end work
            var endTime = addRandomnessToTime(startHour + workTime, startMinutes + 5, 2);
            String s = countTime(startTime, endTime);
            list.add(new MyLog(today, endTime, username, MessageFormat.format(LOGGED_OUT, username, s)));

        }

        for (var t : list) {
            System.out.println(t);
        }


    }

}
