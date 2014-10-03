package vn.enclave.peyton.fusion;

import org.eclipse.swt.SWT;
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
		getWindowConfigurer().setShellStyle(SWT.NO_TRIM);
		getWindowConfigurer().setShowMenuBar(false);
	}

	@Override
	public void postWindowOpen() {
		getWindowConfigurer().getWindow().getShell().setMaximized(true);
	}
}