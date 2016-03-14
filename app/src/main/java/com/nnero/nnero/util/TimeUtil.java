package com.nnero.nnero.util;

/**
 * Created by NNERO on 16/1/19.
 */
public class TimeUtil {

    public static String msecToFormatTime(int milliseconds) {
        int seconds = milliseconds / 1000 % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int hours = (milliseconds / (1000 * 60 * 60)) % 24;
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
}
