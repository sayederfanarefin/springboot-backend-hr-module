package com.sweetitech.hrm.common.util;

import com.sweetitech.hrm.common.Constants;

public class ConstantsUtil {

    public static boolean isValidStatus(String status) {
        return status.equals(Constants.RequestStatus.REQUESTED)
                || status.equals(Constants.RequestStatus.FORWARDED)
                || status.equals(Constants.RequestStatus.APPROVED)
                || status.equals(Constants.RequestStatus.DECLINED)
                || status.equals(Constants.RequestStatus.CANCELLED);
    }

    public static boolean isValidTypeOfTender(String status) {
        return status.equals(Constants.TypeOfTender.E_GP)
                || status.equals(Constants.TypeOfTender.MANUAL);
    }

    public static boolean isValidTenderStatus(String status) {
        return status.equals(Constants.TenderStatus.GREEN)
                || status.equals(Constants.TenderStatus.RED)
                || status.equals(Constants.TenderStatus.WHITE)
                || status.equals(Constants.TenderStatus.YELLOW);
    }

    public static boolean isValidEntityTenderStatus(String status) {
        return status.equals(Constants.EntityTenderStatus.GOT_WORK)
                || status.equals(Constants.EntityTenderStatus.DID_NOT_GET_WORK);
    }

    public static boolean isValidAmountOfTenderSecurityStatus(String status) {
        return status.equals(Constants.AmountOfTenderSecurityStatus.PENDING)
                || status.equals(Constants.AmountOfTenderSecurityStatus.RELEASE)
                || status.equals(Constants.AmountOfTenderSecurityStatus.RELEASED);
    }

    public static boolean isValidCustomDepartment(String department) {
        return department.equals(Constants.CustomDepartments.SALES)
                || department.equals(Constants.CustomDepartments.APPLICATION)
                || department.equals(Constants.CustomDepartments.OTHER);
    }

}
