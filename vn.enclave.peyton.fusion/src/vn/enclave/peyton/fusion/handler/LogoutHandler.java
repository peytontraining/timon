package vn.enclave.peyton.fusion.handler;

import org.eclipse.core.commands.*;
import org.eclipse.ui.handlers.HandlerUtil;

public class LogoutHandler extends AbstractHandler {
	private boolean enabled = true;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HandlerUtil.getActiveWorkbenchWindow(event).close();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
