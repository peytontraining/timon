package vn.enclave.peyton.fusion;

import org.eclipse.ui.*;

import vn.enclave.peyton.fusion.view.*;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class ProjectPerspective implements IPerspectiveFactory {

    public static final String
        ID = "vn.enclave.peyton.fusion.projectPerspective";

    private IFolderLayout topLeft;
    private IFolderLayout topRight;
    @SuppressWarnings("unused")
    private IFolderLayout bottomRight;

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        topLeft = layout
            .createFolder("topLeft", IPageLayout.LEFT,
                0.35f, IPageLayout.ID_EDITOR_AREA);
        topLeft.addView(NavigationViewPart2.ID);
        topLeft.addView(DriverTreeViewPart.ID);
        topLeft.addView(AreaTreeViewPart.ID);
        layout.getViewLayout(NavigationViewPart2.ID).setCloseable(false);
        layout.getViewLayout(DriverTreeViewPart.ID).setCloseable(false);
        layout.getViewLayout(AreaTreeViewPart.ID).setCloseable(false);

        bottomRight = layout
            .createFolder("bottomRight", IPageLayout.BOTTOM, 0.7f, editorArea);

        topRight = layout
            .createFolder("topRight", IPageLayout.TOP, 0.7f, editorArea);
        topRight.addView(DeviceTableViewPart.ID);
        topRight.addView(ServiceTableViewPart.ID);
        topRight.addView(SceneTableViewPart.ID);
        topRight.addView(RuleTableViewPart.ID);
        layout.getViewLayout(DeviceTableViewPart.ID).setCloseable(false);
        layout.getViewLayout(ServiceTableViewPart.ID).setCloseable(false);
        layout.getViewLayout(SceneTableViewPart.ID).setCloseable(false);
        layout.getViewLayout(RuleTableViewPart.ID).setCloseable(false);
    }
}