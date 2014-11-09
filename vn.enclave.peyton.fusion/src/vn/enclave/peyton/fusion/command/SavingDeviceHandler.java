package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.AddingDeviceViewPart;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class SavingDeviceHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;
    private IWorkbenchPage workbenchPage;
    private AddingDeviceViewPart currentAddingDeviceViewPart;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    private void createWorkbenchPage() {
        IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(executionEvent);
        workbenchPage = workbenchWindow.getActivePage();
    }

    private void setCurrentAddingDeviceViewPart() {
        currentAddingDeviceViewPart = (AddingDeviceViewPart) HandlerUtil.getActivePart(executionEvent);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);
        createWorkbenchPage();
        setCurrentAddingDeviceViewPart();
        if (currentAddingDeviceViewPart.isDirty()) {
            saveNewDevice();
        }
        return null;
    }

    private void saveNewDevice() {
        Object selectedNode = getSelectedNodeOnPlanTree();
        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;

            Device newDevice = addNewDeviceToDatabase(selectedVersion);

            addNewDeviceToDeviceTree(selectedVersion, newDevice);

            currentAddingDeviceViewPart.setDirty(false);
            currentAddingDeviceViewPart.setCanceled(false);
        } else {
            currentAddingDeviceViewPart.setCanceled(true);
            currentAddingDeviceViewPart.createWarningMessageDialog();
        }
    }

    private Object getSelectedNodeOnPlanTree() {
        ISelection selection = workbenchPage.getSelection(NavigationViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object selectedNode = sselection.getFirstElement();
        return selectedNode;
    }

    private Device addNewDeviceToDatabase(Version selectedVersion) {
        Device newDevice = currentAddingDeviceViewPart.prepareNewDevice();
        newDevice.setVersion(selectedVersion);

        DeviceService deviceService = new DeviceService();
        newDevice = deviceService.insert(newDevice);
        return newDevice;
    }

    private void addNewDeviceToDeviceTree(Version selectedVersion, Device newDevice) {
        /*
         * Set the selected version to the new device, which returned from
         * addDevice2Database() method.
         */
        newDevice.setVersion(selectedVersion);

        /*
         * Add the new device into the list devices of the selected version.
         */
        selectedVersion.addDevice(newDevice);

        /*
         * Refresh the device table to display the new device.
         */
        DeviceTableViewPart deviceTableViewPart = (DeviceTableViewPart) workbenchPage.findView(DeviceTableViewPart.ID);
        deviceTableViewPart.refreshDeviceTableViewer();
    }
}
