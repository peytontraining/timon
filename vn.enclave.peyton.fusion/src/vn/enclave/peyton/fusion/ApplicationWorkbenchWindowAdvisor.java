package vn.enclave.peyton.fusion;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.*;

/**
 * Configures the initial size and appearance of a workbench window.
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public void preWindowOpen() {
	}

	@Override
	public void postWindowOpen() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
				.setFullScreen(true);
	}
}