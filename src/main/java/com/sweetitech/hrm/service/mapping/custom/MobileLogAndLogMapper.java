package com.sweetitech.hrm.service.mapping.custom;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.Log;
import com.sweetitech.hrm.domain.MobileLogs;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class MobileLogAndLogMapper {

    /**
     *
     * @param mobileLog
     * @return log
     */
    public Log mobileLogToLog(MobileLogs mobileLog) throws Exception {

        Log log = new Log();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mobileLog.getTimestamp());

        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DEVICE_FORMAT);

        log.setDateTimeRecord(sdf.format(date));
        log.setIndRegId(mobileLog.getLogId());
        log.setMachineNumber(Constants.MOBILE_IDENTIFIER);

        return log;

    }

}
