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
        getWindowConfigurer().setShowPerspectiveBar(true);
        IPreferenceStore preferenceStore = PlatformUI.getPreferenceStore();
        Object[] templateIds =
            new Object[]{
                ProjectPerspective.ID, TemplatePerspective.ID, SystemPerspective.ID, UserPerspective.ID,
                LogoutPerspective.ID};
        String value = String.format("%s, %s, %s, %s, %s", templateIds);
        preferenceStore.setValue(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS, value);
        preferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR, false);
        preferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, "topRight");
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