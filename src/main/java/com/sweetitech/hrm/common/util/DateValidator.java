package com.sweetitech.hrm.common.util;

public class DateValidator {

    public static boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    public static boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    public static boolean isValidHour(int hour) {
        return (hour >= 0 && hour <= 23);
    }

    public static boolean isValidDay(int day) {
        return (day >= 1 && day <= 31);
    }

}
