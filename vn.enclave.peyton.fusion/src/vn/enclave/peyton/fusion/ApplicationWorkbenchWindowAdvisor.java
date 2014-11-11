package vn.enclave.peyton.fusion;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.*;

/**
 * Configures the initial size and appearance of a workbench window.
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public void preWindowOpen() {
        getWindowConfigurer().setShellStyle(SWT.NO_TRIM);
        getWindowConfigurer().setShowMenuBar(false);
        getWindowConfigurer().setShowPerspectiveBar(false);
        IPreferenceStore preferenceStore = PlatformUI.getPreferenceStore();
        preferenceStore.setValue(IWorkbenchPreferenceConstants.LOCK_TRIM, true);
    }

    @Override
    public void postWindowOpen() {
        getWindowConfigurer().getWindow().getShell().setMaximized(true);
    }

    @Override
    public boolean isDurableFolder(String perspectiveId, String folderId) {
        return true;
    }

}