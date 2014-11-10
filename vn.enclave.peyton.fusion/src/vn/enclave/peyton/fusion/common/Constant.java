package vn.enclave.peyton.fusion.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Constant {

    private static final String PLUGIN_ID = "vn.enclave.peyton.fusion";

    public static final String PERSISTENCE_UNIT_NAME = "vn.enclave.peyton.fusion";

    /* Image */
    public static final Image IMAGE_ARROW_DOWN = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/arrow_down.png").createImage();

    public static final Image IMAGE_ARROW_UP = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/arrow_up.png").createImage();

    public static final Image IMAGE_COMPANY_GROUP = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/company_group.png").createImage();

    public static final Image IMAGE_EDIT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/edit.png").createImage();

    public static final Image IMAGE_NODE_ADD = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/node_add.png").createImage();

    public static final Image IMAGE_NODE_DELETE = AbstractUIPlugin.imageDescriptorFromPlugin(
        PLUGIN_ID, "icons/node_delete.png").createImage();

    public static final Image IMAGE_PROJECT = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/project.png").createImage();

    public static final Image IMAGE_SAVE_AS = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/save_as.png").createImage();

    public static final Image IMAGE_VERSION = AbstractUIPlugin
        .imageDescriptorFromPlugin(PLUGIN_ID, "icons/version.png").createImage();

    /*
     * COLUMN INDEX OF DEVICE TABLE.
     */
    public static final int COLUMN_NAME = 0;
    public static final int COLUMN_APP_MODULE = 1;
    public static final int COLUMN_DEVICE_TYPE = 2;
    public static final int COLUMN_PHYSICAL_LOCATION = 3;
    public static final int COLUMN_MANUFACTURER = 4;

    /*
     * TOOLBAR ID OF VIEWPART.
     */
    public static final String TOOLBAR_NAVIGATION_VIEW_PART = "toolbar:navigationViewPart";
    public static final String TOOLBAR_DEVICE_TABLE_VIEW_PART = "toolbar:deviceTableViewPart";
    public static final String TOOLBAR_ADDING_DEVICE_VIEW_PART = "toolbar:addingDeviceViewPart";
    public static final String TOOLBAR_AREAS_TEMPLATES_VIEW_PART = "toolbar:areasTemplatesViewPart";
    public static final String TOOLBAR_DEVICE_TEMPLATE_VIEW_PART = "toolbar:deviceTemplatesViewPart";
    public static final String TOOLBAR_DETAILED_DEVICE_TEMPLATE_VIEW_PART = "toolbar:detailedDeviceTemplateViewPart";
    public static final String TOOLBAR_MODIFYING_DEVICE_VIEW_PART = "toolbar:modifyingDeviceViewPart";
}