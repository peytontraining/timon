package vn.enclave.peyton.fusion.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Constant {

    public static final String PERSISTENCE_UNIT_NAME =
        "vn.enclave.peyton.fusion";

    public static final int DEFAULT_VERSION_ID = -1;

    public static final int DEFAULT_PROJECT_ID = -1;

    /* Image */
    public static final Image IMAGE_PLAN = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/company_group.png")
        .createImage();

    public static final Image IMAGE_PROJECT = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/project.png").createImage();

    public static final Image IMAGE_VERSION = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/version.png").createImage();

    public static final Image IMAGE_CAMERA = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/camera.png").createImage();

    public static final Image IMAGE_DVR_NVR = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/cctv.png").createImage();

    public static final Image IMAGE_CCTV = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/cctv.png").createImage();

    public static final Image IMAGE_FIREPLACE_CONTROLLER = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/fireplace-controller.png")
        .createImage();

    public static final Image IMAGE_ARROW_UP = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/arrow_up.png").createImage();

    public static final Image IMAGE_ARROW_DOWN = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/arrow_down.png").createImage();

    public static final Image IMAGE_SAVE = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/save_as.png").createImage();

    public static final Image IMAGE_EDIT = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/edit.png").createImage();

    public static final Image IMAGE_ADD_FOLDER = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/add_folder.png").createImage();

    public static final Image IMAGE_DELETE = AbstractUIPlugin
        .imageDescriptorFromPlugin(
            "vn.enclave.peyton.fusion", "/icons/delete.png").createImage();

    /* Device Type */
    public static final String DVR_NVR = "DVR/NVR";

    public static final String FIREPLACE_CONTROLLER = "Fireplace Controller";

    public static final String IP_CAMERAS = "IP Cameras";

    /* Module Type */
    public static final String CCTV = "CCTV";
}