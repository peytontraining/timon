package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.DeleteVersionDialog;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

/**
 * This class is used to handle deleting a version. It is called when delete
 * menu item on context menu of Plan TreeViewer is clicked.
 */
public class DeleteVersionHandler extends AbstractHandler implements IHandler {

    private VersionService versionService = new VersionService();

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the current window.
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        // Get selected version node.
        IStructuredSelection selection =
            (IStructuredSelection) window.getActivePage().getSelection(
                NavigationViewPart.ID);
        Object firstElement = selection.getFirstElement();

        if (firstElement instanceof Version) {
            DeleteVersionDialog dialog =
                new DeleteVersionDialog(window.getShell());

            if (dialog.open() == Window.OK) {
                // Remove data of seleted version from database.
                versionService.remove((Version) firstElement);

                // Remove data of selected version from tree's data.
                ((Version) firstElement).getProject().removeVersion(
                    (Version) firstElement);

                // Refresh the tree after deleting.
                ((NavigationViewPart) HandlerUtil.getActivePart(event))
                    .getViewer().refresh();
            }
        }
        return null;
    }
}
