package vn.enclave.peyton.fusion.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Constant {

    private static final String PLUGIN_ID = "vn.enclave.peyton.fusion";

    public static final String PERSISTENCE_UNIT_NAME = "vn.enclave.peyton.fusion";

    public static final int DEFAULT_VERSION_ID = -1;

    public static final int DEFAULT_PROJECT_ID = -1;

    /* Image */
    public static final Image IMAGE_ADD_FOLDER = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/add_folder.png").createImage();

    public static final Image IMAGE_ADD = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/add.png").createImage();

    public static final Image IMAGE_ARROW_DOWN = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/arrow_down.png").createImage();

    public static final Image IMAGE_ARROW_UP = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/arrow_up.png").createImage();

    public static final Image IMAGE_CAMERA = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/camera.png").createImage();

    public static final Image IMAGE_CCTV = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/cctv.png").createImage();

    public static final Image IMAGE_COLLAPSE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/collapse.png").createImage();

    public static final Image IMAGE_COMPANY_GROUP = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/company_group.png").createImage();

    public static final Image IMAGE_COPY = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/copy.png").createImage();

    public static final Image IMAGE_DELETE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/delete.png").createImage();

    public static final Image IMAGE_DEVICE_CHANGED = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/device_changed.png").createImage();

    public static final Image IMAGE_DEVICE_UPDATE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/device_update.png").createImage();

    public static final Image IMAGE_EDIT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/edit.png").createImage();

    public static final Image IMAGE_EXPAND = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/expand.png").createImage();

    public static final Image IMAGE_EXPORT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/export.png").createImage();

    public static final Image IMAGE_FIREPLACE_CONTROLLER = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/fireplace-controller.png").createImage();

    public static final Image IMAGE_IMPORT_ZWAVE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/import_zwave.png").createImage();

    public static final Image IMAGE_IMPORT_DEVICES = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/import-devices.png").createImage();

    public static final Image IMAGE_NEW_TEMPLATE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/new_template.png").createImage();

    public static final Image IMAGE_NODE_ADD = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/node_add.png").createImage();

    public static final Image IMAGE_NODE_DELETE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/node_delete.png").createImage();

    public static final Image IMAGE_PROJECT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/project.png").createImage();

    public static final Image IMAGE_REFRESH = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/refresh.png").createImage();

    public static final Image IMAGE_REQUEST_ONLINE_STATUS = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/request_online_status.png").createImage();

    public static final Image IMAGE_SAVE_AND_CLOSE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/save_and_close.png").createImage();

    public static final Image IMAGE_SAVE_AS = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/save_as.png").createImage();

    public static final Image IMAGE_SERVICE = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/service.png").createImage();

    public static final Image IMAGE_SHOW_DEVICES = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/show_devices.png").createImage();

    public static final Image IMAGE_SYSTEM = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/system.png").createImage();

    public static final Image IMAGE_TEMPLATE_CHILD = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/template_child.png").createImage();

    public static final Image IMAGE_VERSION = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/version.png").createImage();

    public static final Image IMAGE_CLONE_VERSION = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/clone_version.png").createImage();
    /*
     * COLUMN INDEX OF DEVICE TABLE.
     */
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_APP_MODULE = 1;
    public static final int COLUMN_DEVICE_TYPE = 2;
    public static final int COLUMN_PHYSICAL_LOCATION = 3;
    public static final int COLUMN_MANUFACTURER = 4;

}