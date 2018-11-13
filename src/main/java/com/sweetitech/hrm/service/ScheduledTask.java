package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;
import com.sweetitech.hrm.service.implementation.NotificationServiceImpl;
import com.sweetitech.hrm.service.implementation.TenderServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTask {

    public static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private TenderServiceImpl tenderService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Scheduled(fixedRate = (1 * 5 * 1000))
    public void reportCurrentTime() throws Exception {

//        log.info("The time now is {}", simpleDateFormat.format(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(Calendar.HOUR_OF_DAY) == 12 || calendar.get(Calendar.HOUR_OF_DAY) == 16) {
            if (calendar.get(Calendar.MINUTE) == 0) {
                if (calendar.get(Calendar.SECOND) > 0 && calendar.get(Calendar.SECOND) < 6) {
                    List<TenderResponseDTO> tenders = tenderService.readAllNearingExpiry(new Date());

                    if (tenders != null && tenders.size() > 0) {
                        List<User> users = userService.readAllGeneralAdminsAndAccounts(tenders.get(0).getCreatedByUser().getCompanyId());

                        NotificationResponseDTO notification;
                        if (users != null && users.size() > 0) {
                            for (User user: users) {
                                notification = notificationService.create(notificationMapper.map(tenders, user.getId()));

                                pushNotificationService.pushNotification(notification, user.getId());
                            }
                        }
                    }
                }
            }
        }

    }

}
