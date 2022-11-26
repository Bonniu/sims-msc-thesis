package com.logsmock.logsmock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.logsmock.logsmock.LogMessages.*;
import static com.logsmock.logsmock.TimeCalculator.addRandomnessToTime;
import static com.logsmock.logsmock.TimeCalculator.countTime;


@Slf4j
@SpringBootApplication
public class LogsmockApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogsmockApplication.class, args);
        var list = new ArrayList<MyLog>();

        list.addAll(generateNormalUser("jkiwior", 8, 0, 8));
        list.addAll(generateNormalUser("rlewandowski", 7, 30, 8));
        list.addAll(generateNormalUser("wszczesny", 9, 0, 6));
        list.addAll(generateNormalUser("gkrychowiak", 12, 0, 8));
        list.addAll(generateNormalUser("pzielinski", 8, 0, 12));
        list.addAll(generateNormalUser("kpiatek", 9, 15, 6));

        list.sort(MyLog::compareTo);
        for (var t : list) {
            System.out.println(t);
        }

    }

    public static List<MyLog> generateNormalUser(String username, int startHour, int startMinutes, int workTime) {
        int requestPeriod = 15; // minutes

        var list = new ArrayList<MyLog>();
        // days between 14-27 november
        for (int i = 14; i < 28; i++) {
            if (i == 19 || i == 20) continue; // weekend
            var today = LocalDate.of(2022, 11, i);

            // start work
            var startTime = addRandomnessToTime(startHour, startMinutes, 3);
            list.add(new MyLog(today, startTime, username, MessageFormat.format(LOGGED_TO_WORK, username)));

            //end work
            var endTime = addRandomnessToTime(startHour + workTime, startMinutes + 5, 2);
            String s = countTime(startTime, endTime);
            list.add(new MyLog(today, endTime, username, MessageFormat.format(LOGGED_OUT, username, s)));

            //between
            var time = startTime.plus(15, ChronoUnit.MINUTES);
            list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));

            while (time.isBefore(endTime)) {
                time = time.plus(randomInt(30, requestPeriod * 60), ChronoUnit.SECONDS);
                if (time.isAfter(endTime)) break;
                list.add(new MyLog(today, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
                if (randomInt(1, 100) % 2 == 0) {
                    time = time.plus(randomInt(30, 100), ChronoUnit.SECONDS);
                    if (time.isAfter(endTime)) break;
                    list.add(new MyLog(today, time, username, MessageFormat.format(RESERVATION, username)));
                }
            }

        }


        return list;
    }

    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

}
