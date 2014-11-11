package vn.enclave.peyton.fusion.listener;

import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowListener implements IWindowListener {
    private Map<Shell, ToolBar> toolbars;
    private PerspectiveListener perspectiveListener;

    public WindowListener(Map<Shell, ToolBar> toolbars, PerspectiveListener perspectiveListener) {
        this.toolbars = toolbars;
        this.perspectiveListener = perspectiveListener;
    }

    @Override
    public void windowActivated(IWorkbenchWindow window) {
    }

    @Override
    public void windowDeactivated(IWorkbenchWindow window) {
    }

    @Override
    public void windowClosed(IWorkbenchWindow window) {
        toolbars.remove(window.getShell());
        window.removePerspectiveListener(perspectiveListener);
    }

    @Override
    public void windowOpened(IWorkbenchWindow window) {
        window.addPerspectiveListener(perspectiveListener);
        Shell shell = window.getShell();
        IPerspectiveDescriptor perspective = window.getActivePage().getPerspective();
        perspectiveListener.updateToolbar(shell, perspective);
    }

}
