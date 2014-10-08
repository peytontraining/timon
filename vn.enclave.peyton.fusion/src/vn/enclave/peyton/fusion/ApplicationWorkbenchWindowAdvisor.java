package vn.enclave.peyton.fusion;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.*;

/**
 * Configures the initial size and appearance of a workbench window.
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private static final String DELIMITER = ",";

    public ApplicationWorkbenchWindowAdvisor(
            IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    @Override
    public void preWindowOpen() {
        getWindowConfigurer().setShellStyle(SWT.NO_TRIM);
        getWindowConfigurer().setShowMenuBar(false);
        getWindowConfigurer().setShowPerspectiveBar(true);
        PlatformUI.getPreferenceStore().
            setValue(IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS,
                ProjectPerspective.ID.concat(DELIMITER)
                .concat(TemplatePerspective.ID).concat(DELIMITER)
                .concat(SystemPerspective.ID).concat(DELIMITER)
                .concat(UserPerspective.ID).concat(DELIMITER)
                .concat(LogoutPerspective.ID));
        PlatformUI.getPreferenceStore().
            setValue(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR,
                false);
        PlatformUI.getPreferenceStore().
        setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
            "topRight");
        PlatformUI.getPreferenceStore().
            setValue(IWorkbenchPreferenceConstants.LOCK_TRIM, true);
    }

    @Override
    public void postWindowOpen() {
        getWindowConfigurer().getWindow().getShell().setMaximized(true);
    }
}