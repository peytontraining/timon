package vn.enclave.peyton.fusion;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import vn.enclave.peyton.fusion.view.AddingDeviceViewPart;
import vn.enclave.peyton.fusion.view.AreaTreeViewPart;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;
import vn.enclave.peyton.fusion.view.DriverTreeViewPart;
import vn.enclave.peyton.fusion.view.ModifyingDeviceViewPart;
import vn.enclave.peyton.fusion.view.NavigationViewPart;
import vn.enclave.peyton.fusion.view.RuleTableViewPart;
import vn.enclave.peyton.fusion.view.SceneTableViewPart;
import vn.enclave.peyton.fusion.view.ServiceTableViewPart;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class ProjectPerspective implements IPerspectiveFactory {

    public static final String ID = "vn.enclave.peyton.fusion.projectPerspective";

    private IFolderLayout left;

    private IFolderLayout topRight;

    private IFolderLayout bottomRight;

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);
        layout.setFixed(true);

        left = layout.createFolder("left", IPageLayout.LEFT, 0.35f, IPageLayout.ID_EDITOR_AREA);
        left.addView(NavigationViewPart.ID);
        left.addView(DriverTreeViewPart.ID);
        left.addView(AreaTreeViewPart.ID);

        topRight = layout.createFolder("topRight", IPageLayout.TOP, 0.7f, editorArea);
        topRight.addView(DeviceTableViewPart.ID);
        topRight.addView(ServiceTableViewPart.ID);
        topRight.addView(SceneTableViewPart.ID);
        topRight.addView(RuleTableViewPart.ID);

        bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM, 0.9f, editorArea);
        bottomRight.addPlaceholder(AddingDeviceViewPart.ID + ":*");
        bottomRight.addPlaceholder(ModifyingDeviceViewPart.ID + ":*");
    }

}
