package vn.enclave.peyton.fusion.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.handlers.HandlerUtil;

public class LogoutHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        HandlerUtil.getActiveWorkbenchWindow(event).close();
        return null;
    }

}
