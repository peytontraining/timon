package vn.enclave.peyton.fusion;

import org.eclipse.ui.*;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class SystemPerspective implements IPerspectiveFactory {

    public static final String ID = "vn.enclave.peyton.fusion.systemPerspective";

    @SuppressWarnings("unused")
    private IFolderLayout topLeft;
    @SuppressWarnings("unused")
    private IFolderLayout topRight;
    @SuppressWarnings("unused")
    private IFolderLayout bottomRight;

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.35f,
                IPageLayout.ID_EDITOR_AREA);

        bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM,
                0.7f, editorArea);

        topRight = layout.createFolder("topRight", IPageLayout.TOP, 0.7f,
                editorArea);

    }
}
