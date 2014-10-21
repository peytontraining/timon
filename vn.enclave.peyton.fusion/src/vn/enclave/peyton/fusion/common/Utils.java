package vn.enclave.peyton.fusion.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * This method is used to convert java.util.Date to java.lang.String. The
     * format of date after convert is yyyy/MM/dd hh:mm:ss.
     * 
     * @param date
     *            is instant of java.util.Date,
     * @return date is instant of java.util.String with format is yyyy/MM/dd
     *         hh:mm:ss.
     */
    public static String convertDate2String(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(date);
    }
}
