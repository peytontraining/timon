package vn.enclave.peyton.fusion;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.listener.PerspectiveListener;
import vn.enclave.peyton.fusion.listener.SwitchPerspectiveToolbarListener;
import vn.enclave.peyton.fusion.listener.WindowListener;

public class PerspectiveSwicherToolbar extends ContributionItem {
    private static final long serialVersionUID = 3685370561895178666L;
    private static final Map<Shell, ToolBar> TOOLBARS = new HashMap<Shell, ToolBar>();
    private static final PerspectiveListener PERSPECTIVE_LISTENER = new PerspectiveListener(TOOLBARS);

    private final IWindowListener WINDOW_LISTENER = new WindowListener(TOOLBARS, PERSPECTIVE_LISTENER);

    private static IWorkbenchPage getActivePage() {
        IWorkbenchPage activePage = null;
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            activePage = window.getActivePage();
        }
        return activePage;
    }

    private static String getPerspectiveId() {
        String perspectiveId = null;
        IWorkbenchPage page = getActivePage();
        if (page != null) {
            IPerspectiveDescriptor descriptor = page.getPerspective();
            if (descriptor != null) {
                perspectiveId = descriptor.getId();
            }
        }
        return perspectiveId;
    }

    public PerspectiveSwicherToolbar() {
        this(null);
    }

    public PerspectiveSwicherToolbar(String id) {
        super(id);
        PlatformUI.getWorkbench().addWindowListener(WINDOW_LISTENER);
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public void fill(ToolBar parent, int index) {
        String activePerspective = getPerspectiveId();
        final SelectionListener toolbarListener = new SwitchPerspectiveToolbarListener();
        IPerspectiveDescriptor[] groupOfPerspectives =
            PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();
        for (IPerspectiveDescriptor perspective : groupOfPerspectives) {
            final Image image = perspective.getImageDescriptor().createImage();
            ToolItem toolItem = new ToolItem(parent, SWT.RADIO);
            toolItem.setImage(image);
            toolItem.setText(perspective.getLabel());
            toolItem.setData(Constant.KEY_PERSPECTIVE_DESCRIPTOR, perspective);
            toolItem.addSelectionListener(toolbarListener);
            toolItem.addDisposeListener(new DisposeListener() {
                private static final long serialVersionUID = -1261207884166132058L;

                @Override
                public void widgetDisposed(DisposeEvent event) {
                    image.dispose();
                }
            });
            if (perspective.getId().equals(activePerspective)) {
                toolItem.setSelection(true);
            }
        }
        TOOLBARS.put(parent.getShell(), parent);
    }
}
