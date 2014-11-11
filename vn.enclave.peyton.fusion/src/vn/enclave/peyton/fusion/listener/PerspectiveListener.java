package vn.enclave.peyton.fusion.listener;

import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;

import vn.enclave.peyton.fusion.common.Constant;

public class PerspectiveListener implements IPerspectiveListener {
    private Map<Shell, ToolBar> toolbars;

    public PerspectiveListener(Map<Shell, ToolBar> toolbars) {
        this.toolbars = toolbars;
    }

    @Override
    public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
        Shell shell = page.getWorkbenchWindow().getShell();
        updateToolbar(shell, page.getPerspective());
    }

    @Override
    public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
    }

    public void updateToolbar(Shell shell, IPerspectiveDescriptor perspective) {
        ToolBar toolBar = toolbars.get(shell);
        if (toolBar == null) {
            return;
        }
        if (toolBar.isDisposed()) {
            toolbars.remove(shell);
            return;
        }
        ToolItem[] items = toolBar.getItems();
        for (ToolItem item : items) {
            boolean isSelected = perspective == item.getData(Constant.KEY_PERSPECTIVE_DESCRIPTOR);
            if (isSelected) {
                item.setSelection(isSelected);
                return;
            }
        }
    }
}
