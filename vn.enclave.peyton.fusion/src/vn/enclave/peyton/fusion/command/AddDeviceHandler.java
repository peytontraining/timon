package vn.enclave.peyton.fusion.command;

import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.AddDeviceDialog;
import vn.enclave.peyton.fusion.view.NewDeviceViewPart;

/**
 * This class is used to handle adding a device from device template list. It is
 * called when add toolbar item toolbar of Device is clicked.
 */
public class AddDeviceHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the current window.
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        AddDeviceDialog dialog = new AddDeviceDialog(window.getShell());

        if (dialog.open() == Window.OK && dialog.getTemplate() != null) {
            try {
                window.getActivePage().showView(
                    NewDeviceViewPart.ID,
                    String.valueOf((new Date()).getTime()),
                    IWorkbenchPage.VIEW_ACTIVATE);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
