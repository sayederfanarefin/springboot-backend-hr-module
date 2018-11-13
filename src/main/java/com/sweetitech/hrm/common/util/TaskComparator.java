package com.sweetitech.hrm.common.util;

import com.sweetitech.hrm.domain.Task;

import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getScheduledDate().compareTo(o2.getScheduledDate());
    }
}
