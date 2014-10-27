package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;
import vn.enclave.peyton.fusion.view.NavigationViewPart;
import vn.enclave.peyton.fusion.view.NewDeviceViewPart;

public class SaveDeviceHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the current window.
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        // Get selected version node.
        IStructuredSelection selection =
            (IStructuredSelection) window.getActivePage().getSelection(
                NavigationViewPart.ID);
        Object firstElement = selection.getFirstElement();

        // If selected node is an instance of Version.
        if (firstElement instanceof Version) {
            Device newDevice =
                ((NewDeviceViewPart) HandlerUtil.getActivePart(event))
                    .getForm().getNewDevice();
            newDevice.setVersion((Version) firstElement);

            DeviceService deviceService = new DeviceService();
            newDevice = deviceService.save(newDevice);

            newDevice.setVersion((Version) firstElement);

            ((Version) firstElement).getDevices().add(newDevice);

            ((DeviceTableViewPart) window.getActivePage().findView(
                DeviceTableViewPart.ID)).getViewer().refresh();
        }
        return null;
    }

}
