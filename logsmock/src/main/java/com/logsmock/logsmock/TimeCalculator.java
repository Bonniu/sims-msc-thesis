package com.logsmock.logsmock;

import java.time.LocalTime;


public class TimeCalculator {

    /*
     *   Adds randomized value to time.
     *    example: 08:00, randomness = 5 (minutes)
     *    returns value between 07:55 - 08:05 + seconds between 0 - 59 (07:56:42)
     *   Arguments:
     *       int hour, int minutes, int randomness (in minutes)
     *   or
     *      LocalTime time, int randomness
     *   or
     *      LocalTime time (randomness=5)
     * */
    public static LocalTime addRandomnessToTime(int hour, int minutes, int randomness) {
        int minutesAll = hour * 60 + minutes;
        int max = minutesAll + randomness;
        int min = minutesAll - randomness;

        int v = (int) (Math.random() * (max - min + 1) + min);
        int startHour = v / 60;
        int startMinutes = v % 60;
        int randomSeconds = (int) (Math.random() * 60);
        return LocalTime.of(startHour, startMinutes, randomSeconds);
    }

    public static LocalTime addRandomnessToTime(LocalTime time, int randomness) {
        return TimeCalculator.addRandomnessToTime(time.getHour(), time.getMinute(), randomness);
    }

    public static LocalTime addRandomnessToTime(LocalTime time) {
        return TimeCalculator.addRandomnessToTime(time.getHour(), time.getMinute(), 5);
    }

    /*
     *  Counts time between given dates and returns it as String
     * */
    public static String countTime(LocalTime startTime, LocalTime endTime) {
        int hours = endTime.getHour() - startTime.getHour();
        int minutes = endTime.getMinute() - startTime.getMinute();
        int seconds = endTime.getSecond() - startTime.getSecond();

        var secondsTotal = hours * 3600 + minutes * 60 + seconds;

        var s = secondsTotal % 60;
        var m = secondsTotal / 60 % 60;
        var h = secondsTotal / 3600;

        var returned = "";
        if (h < 10) returned += "0";
        returned += h + ":";
        if (m < 10) returned += "0";
        returned += m + ":";
        if (s < 10) returned += "0";
        returned += s;

        return returned;
    }

}
