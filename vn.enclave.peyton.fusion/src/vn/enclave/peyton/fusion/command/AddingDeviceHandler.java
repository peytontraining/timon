package vn.enclave.peyton.fusion.command;

import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.AddDeviceDialog;
import vn.enclave.peyton.fusion.view.AddingDeviceViewPart;

/**
 * This class is used to handle adding a device from device template list. It is
 * called when add toolbar item toolbar of Device is clicked.
 */
public class AddingDeviceHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Shell shell = HandlerUtil.getActiveShell(event);
        AddDeviceDialog dialog = new AddDeviceDialog(shell);

        if (dialog.open() == Window.OK && dialog.getTemplate() != null) {
            try {
                String viewId = AddingDeviceViewPart.ID;
                String secondaryId = String.valueOf((new Date()).getTime());
                int mode = IWorkbenchPage.VIEW_ACTIVATE;
                IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
                IWorkbenchPage page = window.getActivePage();
                AddingDeviceViewPart addingDeviceViewPart =
                    (AddingDeviceViewPart) page.showView(viewId, secondaryId, mode);
                addingDeviceViewPart.populateViewPartFrom(dialog.getTemplate());
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
