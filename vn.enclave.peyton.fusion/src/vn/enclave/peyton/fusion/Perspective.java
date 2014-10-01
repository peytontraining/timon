package vn.enclave.peyton.fusion;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import vn.enclave.peyton.fusion.view.AreaTreeViewPart;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;
import vn.enclave.peyton.fusion.view.DriverTreeViewPart;
import vn.enclave.peyton.fusion.view.ProjectTreeViewPart;
import vn.enclave.peyton.fusion.view.RuleTableViewPart;
import vn.enclave.peyton.fusion.view.SceneTableViewPart;
import vn.enclave.peyton.fusion.view.ServiceTableViewPart;
import vn.enclave.peyton.fusion.view.VersionViewPart;

/**
 * Configures the perspective layout. This class is contributed through the
 * plugin.xml.
 */
public class Perspective implements IPerspectiveFactory {

	private IFolderLayout topLeft;
	private IFolderLayout topRight;
	@SuppressWarnings("unused")
	private IFolderLayout bottomRight;

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);

		topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.4f,
				IPageLayout.ID_EDITOR_AREA);
		topLeft.addView(ProjectTreeViewPart.ID);
		topLeft.addView(DriverTreeViewPart.ID);
		topLeft.addView(AreaTreeViewPart.ID);
		layout.getViewLayout(ProjectTreeViewPart.ID).setCloseable(false);
		layout.getViewLayout(DriverTreeViewPart.ID).setCloseable(false);
		layout.getViewLayout(AreaTreeViewPart.ID).setCloseable(false);

		topRight = layout.createFolder("topRight", IPageLayout.TOP, 0.7f,
				editorArea);
		topRight.addView(DeviceTableViewPart.ID);
		topRight.addView(ServiceTableViewPart.ID);
		topRight.addView(SceneTableViewPart.ID);
		topRight.addView(RuleTableViewPart.ID);
		layout.getViewLayout(DeviceTableViewPart.ID).setCloseable(false);
		layout.getViewLayout(ServiceTableViewPart.ID).setCloseable(false);
		layout.getViewLayout(SceneTableViewPart.ID).setCloseable(false);
		layout.getViewLayout(RuleTableViewPart.ID).setCloseable(false);

		bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM,
				0.7f, "topRight");

		layout.addStandaloneView(VersionViewPart.ID, false, IPageLayout.BOTTOM,
				0.7f, "topLeft");
		layout.getViewLayout(VersionViewPart.ID).setCloseable(false);

	}
}
