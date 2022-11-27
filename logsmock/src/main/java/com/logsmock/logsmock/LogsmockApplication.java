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
        // october
        var weekends = List.of(8, 9, 15, 16, 22, 23, 29, 30);
        for (int i = 3; i < 31; i++) {
            if (weekends.contains(i)) continue; // weekends
            var today = LocalDate.of(2022, 10, i);
            list.addAll(generateNormalUser("jkiwior", 8, 0, 8, today));
            list.addAll(generateNormalUser("rlewandowski", 7, 30, 8, today));
            list.addAll(generateNormalUser("wszczesny", 9, 0, 6, today));
            list.addAll(generateNormalUser("gkrychowiak", 12, 0, 8, today));
            list.addAll(generateNormalUser("pzielinski", 8, 0, 12, today));
            list.addAll(generateNormalUser("kpiatek", 9, 15, 6, today));
        }

        var date = LocalDate.of(2022, 11, 1);
        list.addAll(generateUserLoggedOnStrangeHour("kpiatek", 9, 15, 6, date));


        list.sort(MyLog::compareTo);
        for (var t : list) {
            log.info(t.toString());
        }

    }

    public static List<MyLog> generateNormalUser(String username, int startHour, int startMinutes, int workTime, LocalDate date) {
        int requestPeriod = 15; // minutes
        var list = new ArrayList<MyLog>();

        // start work
        var startTime = addRandomnessToTime(startHour, startMinutes, 3);
        list.add(new MyLog(date, startTime, username, MessageFormat.format(LOGGED_TO_WORK, username)));

        //end work
        var endTime = addRandomnessToTime(startHour + workTime, startMinutes + 5, 2);
        String s = countTime(startTime, endTime);
        list.add(new MyLog(date, endTime, username, MessageFormat.format(LOGGED_OUT, username, s)));

        //between
        var time = startTime.plus(15, ChronoUnit.MINUTES);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));

        while (time.isBefore(endTime)) {
            time = time.plus(randomInt(30, requestPeriod * 60), ChronoUnit.SECONDS);
            if (time.isAfter(endTime)) break;
            list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username)));
            if (randomInt(1, 100) % 2 == 0) {
                time = time.plus(randomInt(30, 100), ChronoUnit.SECONDS);
                if (time.isAfter(endTime)) break;
                list.add(new MyLog(date, time, username, MessageFormat.format(RESERVATION, username)));
            }
        }
        return list;
    }

    public static List<MyLog> generateUserLoggedOnStrangeHour(String username, int startHour, int startMinutes, int workTime, LocalDate date) {
        var list = new ArrayList<MyLog>();

        // start work but in the night
        var time = addRandomnessToTime(startHour, startMinutes, 3);
        time = time.withHour(1);
        list.add(new MyLog(date, time, username, MessageFormat.format(FAILED_LOG_TO_WORK, username, 1), false));
        time = time.plus(1, ChronoUnit.MINUTES);
        list.add(new MyLog(date, time, username, MessageFormat.format(FAILED_LOG_TO_WORK, username, 2), " WARN", false));
        time = time.plus(randomInt(30, 78), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(FAILED_LOG_TO_WORK, username, 3), " WARN", false));
        time = time.plus(randomInt(30, 78), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(FAILED_LOG_TO_WORK, username, 4), " WARN", false));
        time = time.plus(randomInt(300, 780), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(FAILED_LOG_TO_WORK, username, 5), " WARN", false));
        time = time.plus(randomInt(30, 78), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(LOGGED_TO_WORK, username), false));
        time = time.plus(randomInt(3, 7), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username), false));
        time = time.plus(randomInt(30, 60), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username), false));
        time = time.plus(randomInt(31, 71), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username), false));
        time = time.plus(randomInt(32, 71), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username), false));
        time = time.plus(randomInt(31, 71), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(ALL_RESERVATIONS, username), false));
        time = time.plus(randomInt(2, 7), ChronoUnit.SECONDS);
        list.add(new MyLog(date, time, username, MessageFormat.format(LOGGED_OUT, username), false));

        return list;
    }

    private static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

}
