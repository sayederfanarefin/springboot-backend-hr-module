package com.sweetitech.hrm.common.util;

public class DataUtil {

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

}
