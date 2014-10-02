package vn.enclave.peyton.fusion.handler;

import org.eclipse.core.commands.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SystemHandler extends AbstractHandler {
	private boolean enabled = true;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "System Dialog",
				"System");
		return null;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}