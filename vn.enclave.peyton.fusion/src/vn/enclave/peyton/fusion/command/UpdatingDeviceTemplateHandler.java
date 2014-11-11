package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.service.impl.DeviceTemplateService;
import vn.enclave.peyton.fusion.view.DetailedDeviceTemplateViewPart;

public class UpdatingDeviceTemplateHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;
    private DetailedDeviceTemplateViewPart currentDetailedDeviceTemplateViewPart;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    private void setCurrentDetailedDeviceTemplateViewPart() {
        currentDetailedDeviceTemplateViewPart =
            (DetailedDeviceTemplateViewPart) HandlerUtil.getActivePart(executionEvent);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);
        setCurrentDetailedDeviceTemplateViewPart();
        if (currentDetailedDeviceTemplateViewPart.isDirty()) {
            updateDeviceTemplate();
        }
        return null;
    }

    private void updateDeviceTemplate() {
        DeviceTemplate selectedDeviceTemplate = currentDetailedDeviceTemplateViewPart.getModifiedDeviceTemplate();
        DeviceTemplateService deviceTemplateService = new DeviceTemplateService();
        deviceTemplateService.update(selectedDeviceTemplate);

        currentDetailedDeviceTemplateViewPart.refreshDeviceTable();
        currentDetailedDeviceTemplateViewPart.changeTitleOfViewPart(selectedDeviceTemplate.getName());
        currentDetailedDeviceTemplateViewPart.setDirty(false);
    }
}
