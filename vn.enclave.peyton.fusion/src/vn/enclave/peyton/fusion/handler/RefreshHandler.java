package vn.enclave.peyton.fusion.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class RefreshHandler extends AbstractHandler {
	private boolean enabled = true;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(), "Refresh Dialog",
				"Refresh");
		return null;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
