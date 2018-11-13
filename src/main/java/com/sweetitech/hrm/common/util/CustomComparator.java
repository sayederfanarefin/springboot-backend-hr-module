package com.sweetitech.hrm.common.util;

import com.sweetitech.hrm.domain.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class CustomComparator implements Comparator<Log> {
    @Override
    public int compare(Log o1, Log o2) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy h:mm:ss a");
        try {
            Date date1 = sdf.parse(o1.getDateTimeRecord());
            Date date2 = sdf.parse(o2.getDateTimeRecord());

            return date1.compareTo(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
