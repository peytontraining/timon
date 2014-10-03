package vn.enclave.peyton.fusion.common;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class Constant {

	public static final String PERSISTENCE_UNIT_NAME = "vn.enclave.peyton.fusion";
	
	/* Node Level */
	public static final int PLAN_NODE_LEVEL = 1;
	public static final int PROJECT_NODE_LEVEL = 2;
	public static final int VERSION_NODE_LEVEL = 3;

	/* Image */
	public static final Image PLAN_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/company_group.png").createImage();
	public static final Image PROJECT_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/project.png").createImage();
	public static final Image VERSION_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/version.png").createImage();
	public static final Image CAMERA_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/camera.png").createImage();
	public static final Image DVR_NVR_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/cctv.png").createImage();
	public static final Image FIREPLACE_CONTROLLER_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/fireplace-controller.png").createImage();
	public static final Image ARROW_UP_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/arrow_up.png").createImage();
	public static final Image ARROW_DOWN_IMAGE = AbstractUIPlugin
			.imageDescriptorFromPlugin("vn.enclave.peyton.fusion",
					"/icons/arrow_down.png").createImage();

	/* Device Type */
	public static final String DVR_NVR = "DVR/NVR";
	public static final String FIREPLACE_CONTROLLER = "Fireplace Controller";
	public static final String IP_CAMERAS = "IP Cameras";
}
