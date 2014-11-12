package vn.enclave.peyton.fusion;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.WorkbenchAdvisor;

import vn.enclave.peyton.fusion.common.Constant;

/**
 * This class controls all aspects of the application's execution and is
 * contributed through the plugin.xml.
 */
public class Application implements IApplication {

    public Object start(IApplicationContext context) throws Exception {
        Display display = PlatformUI.createDisplay();
        WorkbenchAdvisor advisor = new ApplicationWorkbenchAdvisor();
        return PlatformUI.createAndRunWorkbench(display, advisor);
    }

    public void stop() {
        disposeAllImage();
    }

    protected void disposeAllImage() {
        Constant.IMAGE_ARROW_DOWN.dispose();
        Constant.IMAGE_ARROW_UP.dispose();
        Constant.IMAGE_COMPANY_GROUP.dispose();
        Constant.IMAGE_EDIT.dispose();
        Constant.IMAGE_NODE_ADD.dispose();
        Constant.IMAGE_NODE_DELETE.dispose();
        Constant.IMAGE_PROJECT.dispose();
        Constant.IMAGE_SAVE_AS.dispose();
        Constant.IMAGE_VERSION.dispose();
    }
}