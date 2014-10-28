package vn.enclave.peyton.fusion.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date convertString2Date(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to create an org.eclipse.swt.graphics.Image from
     * pluginId and fileImagePath.
     * 
     * @param pluginId
     *            the id of the plug-in containing the image file; null is
     *            returned if the plug-in does not exist
     * @param imageFilePath
     *            the relative path of the image file, relative to the root of
     *            the plug-in; the path must be legal
     * @return image is instant of org.eclipse.swt.graphics.Image or null if
     *         image is not existed.
     */
    public static Image createImage(String pluginId, String imageFilePath) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
            pluginId, imageFilePath).createImage();
    }
}
