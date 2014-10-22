package vn.enclave.peyton.fusion;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import vn.enclave.peyton.fusion.view.AreasTemplatesViewPart;
import vn.enclave.peyton.fusion.view.DeviceTemplateDetailViewPart;
import vn.enclave.peyton.fusion.view.DeviceTemplateViewPart;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class TemplatePerspective implements IPerspectiveFactory {

    public static final String ID =
        "vn.enclave.peyton.fusion.templatePerspective";

    private IFolderLayout left;

    private IFolderLayout topRight;

    private IFolderLayout bottomRight;

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        left =
            layout.createFolder(
                "left", IPageLayout.LEFT, 0.35f, IPageLayout.ID_EDITOR_AREA);
        left.addView(AreasTemplatesViewPart.ID);

        topRight =
            layout.createFolder("topRight", IPageLayout.TOP, 0.7f, editorArea);
        topRight.addView(DeviceTemplateViewPart.ID);

        bottomRight =
            layout.createFolder(
                "bottomRight", IPageLayout.BOTTOM, 0.7f, editorArea);
        bottomRight.addPlaceholder(DeviceTemplateDetailViewPart.ID + ":*");
    }
}