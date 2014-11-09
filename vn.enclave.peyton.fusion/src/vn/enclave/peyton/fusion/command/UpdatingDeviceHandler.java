package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.ModifyingDeviceViewPart;

public class UpdatingDeviceHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;
    private ModifyingDeviceViewPart currentModifyingDeviceViewPart;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    private void setCurrentModifyingDeviceViewPart() {
        currentModifyingDeviceViewPart = (ModifyingDeviceViewPart) HandlerUtil.getActivePart(executionEvent);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);
        setCurrentModifyingDeviceViewPart();
        if (currentModifyingDeviceViewPart.isDirty()) {
            updateDevice();
        }
        return null;
    }

    private void updateDevice() {
        Device selectedDevice = currentModifyingDeviceViewPart.getModifiedDevice();
        DeviceService deviceService = new DeviceService();
        deviceService.update(selectedDevice);

        currentModifyingDeviceViewPart.refreshDeviceTable();
        currentModifyingDeviceViewPart.changeTitleOfViewPart(selectedDevice.getName());
        currentModifyingDeviceViewPart.setDirty(false);
    }
}
