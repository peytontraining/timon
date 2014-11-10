package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.view.AddingDeviceViewPart;

public class SavingDeviceHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;
    private AddingDeviceViewPart currentAddingDeviceViewPart;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    private void setCurrentAddingDeviceViewPart() {
        currentAddingDeviceViewPart = (AddingDeviceViewPart) HandlerUtil.getActivePart(executionEvent);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);
        setCurrentAddingDeviceViewPart();
        if (currentAddingDeviceViewPart.isDirty()) {
            if (currentAddingDeviceViewPart.canDoSave()) {
                currentAddingDeviceViewPart.saveNewDevice();
            } else {
                currentAddingDeviceViewPart.updateDevice();
            }
        }
        return null;
    }
}
