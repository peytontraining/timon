package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.DeleteVersionDialog;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

/**
 * This class is used to handle deleting a version. It is called when delete
 * menu item on context menu of Plan TreeViewer is clicked.
 */
public class DeletingVersionHandler extends AbstractHandler implements IHandler {

    private VersionService versionService = new VersionService();
    private ExecutionEvent executionEvent;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);

        Object selectedNode = getSelectedNodeOnPlanTree();

        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;
            if (selectedVersion.isNewVersion()) {
                return null;
            }
            DeleteVersionDialog dialog = new DeleteVersionDialog(HandlerUtil.getActiveShell(event));

            if (dialog.open() == Window.OK) {
                versionService.remove(selectedVersion);

                // Remove data of selected version from tree's data.
                selectedVersion.getProject().removeVersion(selectedVersion);
                selectedVersion.setProject(null);

                // Refresh the tree after deleting.
                ((NavigationViewPart) HandlerUtil.getActivePart(event)).refreshPlanTreeViewer();

                clearRowsOnDeviceTable();
            }
        }
        return null;
    }

    private Object getSelectedNodeOnPlanTree() {
        IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(executionEvent);
        Object selectedNode = selection.getFirstElement();
        return selectedNode;
    }

    private void clearRowsOnDeviceTable() {
        IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(executionEvent);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        IViewPart viewPart = workbenchPage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewPart).clearRowsOnDeviceTable();
    }
}
