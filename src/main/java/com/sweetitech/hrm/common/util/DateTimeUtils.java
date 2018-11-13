package com.sweetitech.hrm.common.util;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    //2015-08-25T21:00:00+03:00
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private final Calendar time;

    public DateTimeUtils(Calendar time){
        this.time = time;
    }

    public DateTimeUtils(Date date) {
        this.time = Calendar.getInstance();
        this.time.setTime(date);
    }

    /**
     * Returns the current time. However, this behaviour can be changed by setting time via calling setCurrentTime() during testing
     * @return
     */
    public Calendar getCurrentTime() {
        if (time == null) {
            return  Calendar.getInstance();
        }
        return (Calendar) time.clone();
    }

    public Calendar getFirstDay(Calendar date) {
        date = getStartOfDay(date);
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date;
    }

    public Calendar getLastDay(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, 1);
        date.add(Calendar.MONTH, 1);
        date.add(Calendar.DAY_OF_YEAR, -1);
        date = getEndOfDay(date);
        return date;
    }

    public Calendar getThisMonthStartDate() {
        Calendar date = getCurrentTime();
        return getFirstDay(date);
    }


    public Calendar getThisMonthEndDate() {
        Calendar date = getCurrentTime();
        getLastDay(date);
        return date;
    }

    public Calendar getLastMonthStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
//        date.set(Calendar.DATE, 1);
//        date.add(Calendar.DAY_OF_MONTH, -1);
        date.add(Calendar.MONTH, -1);
        date.set(Calendar.DAY_OF_MONTH, 1);
        return date;
    }

    public Calendar getLastMonthEndDate() {
       // Calendar date = getCurrentTime();
//        date = getEndOfDay(date);
//        date.set(Calendar.DAY_OF_MONTH, 1);
//        date.add(Calendar.DAY_OF_YEAR, -1);
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(Calendar.DATE, 1);
        date.add(Calendar.DAY_OF_MONTH, -1);
        return date;
    }

    public Calendar getThisWeekStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        // first day of current week
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return date;
    }

    public Calendar getThisWeekEndDate() {
        Calendar date = getThisWeekStartDate();
        date.add(Calendar.DAY_OF_YEAR, 6);
        date = getEndOfDay(date);
        return date;
    }


    public Calendar getThisYearStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(Calendar.MONTH, 1);
        date.set(Calendar.DAY_OF_YEAR, 1);
        return date;
    }

    public Calendar getThisYearEndDate() {
        Calendar date = getCurrentTime();
        date = getEndOfDay(date);
        date.set(Calendar.MONTH, 11);
        date.set(Calendar.DAY_OF_MONTH, 31);
        return date;
    }

    public Calendar getLastYearStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.add(Calendar.YEAR, -1);
        date.set(Calendar.MONTH, 1);
        date.set(Calendar.DAY_OF_YEAR, 1);
        return date;
    }

    public Calendar getLastYearEndDate() {
        Calendar date = getCurrentTime();
        date = getEndOfDay(date);
        date.add(Calendar.YEAR, -1);
        date.set(Calendar.MONTH, 11);
        date.set(Calendar.DAY_OF_MONTH, 31);
        return date;
    }

    public Calendar getLastWeekStartDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        // first day of current week
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        date.add(Calendar.DAY_OF_YEAR, -7);
        return date;
    }

    public Calendar getYesterday() {
        Calendar cal = getCurrentTime();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    public Calendar getLastWeekEndDate() {
        Calendar date = getLastWeekStartDate();
        date.add(Calendar.DAY_OF_YEAR, 6);
        date = getEndOfDay(date);
        return date;
    }

    public Calendar getBeginningFromDate() {
        Calendar date = getCurrentTime();
        date = getStartOfDay(date);
        date.set(1900, Calendar.JANUARY, 1);
        return date;
    }

    public static String toDateString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(calendar.getTime());
    }

    public static String toDateString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static String toDateTimeString(Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
        return df.format(calendar.getTime());
    }

    public static Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(date);
    }

    public static Calendar toCalender(String date) throws ParseException {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        cal.setTime(sdf.parse(date));
        return cal;
    }

    public static Calendar toDateTime(String dateTime) throws ParseException {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        cal.setTime(sdf.parse(dateTime));
        return cal;
    }

    public static Calendar toDateTime(String dateTime, String dateFormat) throws ParseException {
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        cal.setTime(sdf.parse(dateTime));
        return cal;
    }

    public static Calendar getUserTime(Date date, String dateFormat) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        // sdf.setTimeZone(TimeZone.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(sdf.format(date)));
        return calendar;
    }

//    public static Calendar convertToLocalTimeZone(Calendar calendar, String dateFormat) throws Exception {
//        String dateString = getUserTime(calendar.getTime(), dateFormat);
//        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
//        Date date = sdf.parse(dateString);
//        Calendar toSend = Calendar.getInstance();
//        toSend.setTime(date);
//        return toSend;
//    }

    public static Calendar toTime(String dateTime, String dateFormat) throws ParseException {
        Calendar calendar = toDateTime(dateTime, dateFormat);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
        String time = sdf.format(calendar.getTime());
        calendar.setTime(toDateTime(time, "h:mm:ss a").getTime());
        return calendar;
    }

    public static String toString(Calendar calendar, String format) {
        if (format == null)
            format = DATE_TIME_FORMAT;
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    public static String timestampToDayMonth(String stamp) {

        String dt = stamp.split("-")[1];
        Timestamp timestamp = new Timestamp(Long.valueOf(dt));
        return new Date(timestamp.getTime()).toString().substring(0,10);

    }

    public static String timestampToTime(String stamp) {

        String dt = stamp.split("-")[1];
        Timestamp timestamp = new Timestamp(Long.valueOf(dt));
        return new Date(timestamp.getTime()).toString().substring(11,19);
    }

    public static boolean isValidDate(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static Calendar getCalender(String dateString, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            Date date = df.parse(dateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar getEndOfDay(Calendar date) {
        date.setTime(DateUtils.addMilliseconds(DateUtils.ceiling(date.getTime(), Calendar.DATE), -1));
        date.set(Calendar.MILLISECOND, 0);
        return date;
    }

    public static Calendar getStartOfDay(Calendar date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Calendar getmonth(Integer month, Integer year) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.YEAR, year);
        return cal;
    }

    public static Calendar getStartOfMonth(int month, int year) {
        Calendar calStart = Calendar.getInstance();

        calStart.set(Calendar.DATE, 1);
        calStart.set(Calendar.MONTH, (month - 1));
        calStart.set(Calendar.YEAR, year);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);

        return calStart;
    }

    public static Calendar getEndOfMonth(int month, int year) {
        Calendar calEnd = Calendar.getInstance();

        calEnd.set(Calendar.DATE, 1);
        calEnd.set(Calendar.MONTH, (month - 1));
        calEnd.set(Calendar.YEAR, year);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.DATE, calEnd.getActualMaximum(Calendar.DATE)); // changed calendar to cal
        calEnd.add(Calendar.DATE, 1);

        return calEnd;
    }

    public static Long getStartOfDayTimestamp(Date date) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(date);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        return calStart.getTimeInMillis();
    }

    public static Long getEndOfDayTimestamp(Date date) {
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(date);
        calEnd.add(Calendar.DATE, 1);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        return calEnd.getTimeInMillis() - 1;
    }

    public static String getNameOfMonth(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }

        return "";
    }

    public static String getMonth(String text) {
        if (text.contains("January")) return "January";
        if (text.contains("February")) return "February";
        if (text.contains("March")) return "March";
        if (text.contains("April")) return "April";
        if (text.contains("May")) return "May";
        if (text.contains("June")) return "June";
        if (text.contains("July")) return "July";
        if (text.contains("August")) return "August";
        if (text.contains("September")) return "September";
        if (text.contains("October")) return "October";
        if (text.contains("November")) return "November";
        if (text.contains("December")) return "December";

        return "";
    }

}