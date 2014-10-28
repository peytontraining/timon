package vn.enclave.peyton.fusion.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Constant {

    private static final String PLUGIN_ID = "vn.enclave.peyton.fusion";

    public static final String PERSISTENCE_UNIT_NAME =
        "vn.enclave.peyton.fusion";

    public static final int DEFAULT_VERSION_ID = -1;

    public static final int DEFAULT_PROJECT_ID = -1;

    /* Image */
    public static final Image IMAGE_PLAN = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/company_group.png")
        .createImage();

    public static final Image IMAGE_PROJECT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/project.png")
        .createImage();

    public static final Image IMAGE_VERSION = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/version.png")
        .createImage();

    public static final Image IMAGE_CAMERA = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/camera.png").createImage();

    public static final Image IMAGE_DVR_NVR = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/cctv.png").createImage();

    public static final Image IMAGE_CCTV = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/cctv.png").createImage();

    public static final Image IMAGE_FIREPLACE_CONTROLLER = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/fireplace-controller.png")
        .createImage();

    public static final Image IMAGE_ARROW_UP = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/arrow_up.png")
        .createImage();

    public static final Image IMAGE_ARROW_DOWN = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/arrow_down.png")
        .createImage();

    public static final Image IMAGE_SAVE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/save_as.png")
        .createImage();

    public static final Image IMAGE_EDIT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/edit.png").createImage();

    public static final Image IMAGE_ADD_FOLDER = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/add_folder.png")
        .createImage();

    public static final Image IMAGE_DELETE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/delete.png").createImage();

    public static final Image IMAGE_SAVE_CLOSE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/save_and_close.png")
        .createImage();

    public static final Image IMAGE_UPDATE_DEVICE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/device_update.png")
        .createImage();

    public static final Image IMAGE_SHOW_DEVICE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/show_devices.png")
        .createImage();

    public static final Image IMAGE_EDIT_SERVICE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/service.png")
        .createImage();

    /* Device Type */
    public static final String DVR_NVR = "DVR/NVR";

    public static final String FIREPLACE_CONTROLLER = "Fireplace Controller";

    public static final String IP_CAMERAS = "IP Cameras";

    /* Module Type */
    public static final String CCTV = "CCTV";

}