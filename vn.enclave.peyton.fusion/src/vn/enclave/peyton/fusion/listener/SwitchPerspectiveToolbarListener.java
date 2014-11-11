package vn.enclave.peyton.fusion.listener;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import vn.enclave.peyton.fusion.common.Constant;

public class SwitchPerspectiveToolbarListener extends SelectionAdapter {
    private static final long serialVersionUID = -2529416882302782387L;

    public SwitchPerspectiveToolbarListener() {
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        ToolItem item = (ToolItem) e.widget;
        if (item.getSelection()) {
            IWorkbenchPage page = getActivePage();
            if (page != null) {
                IPerspectiveDescriptor perspective =
                    (IPerspectiveDescriptor) item.getData(Constant.KEY_PERSPECTIVE_DESCRIPTOR);
                page.setPerspective(perspective);
            }
        }
    }

    private static IWorkbenchPage getActivePage() {
        IWorkbenchPage activePage = null;
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            activePage = window.getActivePage();
        }
        return activePage;
    }
}
