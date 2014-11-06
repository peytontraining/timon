package vn.enclave.peyton.fusion.control;

import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.provider.PlanTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PlanTreeLableProvider;
import vn.enclave.peyton.fusion.view.DeviceTableViewPart;

public class PlanTree {
    private TreeViewer planTreeViewer;
    private Tree planTree;
    private FilteredTree planFilteredTree;
    private MenuManager planTreeMenuManager;
    private IWorkbenchPartSite workbenchPartSite;
    private IWorkbenchPage workbenchPage;

    private void setWorkbenchPartSite(IWorkbenchPartSite workbenchPartSite) {
        this.workbenchPartSite = workbenchPartSite;
    }

    public PlanTree(Composite parent, IWorkbenchPartSite workbenchPartSite) {
        setWorkbenchPartSite(workbenchPartSite);
        createWorkbenchPage();
        createPlanTreeViewerInside(parent);
    }

    private void createWorkbenchPage() {
        workbenchPage = workbenchPartSite.getPage();
    }

    private void createPlanTreeViewerInside(Composite parent) {
        createPlanFilteredTree(parent);

        planTreeViewer = planFilteredTree.getViewer();
        planTreeViewer.setContentProvider(new PlanTreeContentProvider());
        planTreeViewer.setLabelProvider(new PlanTreeLableProvider());

        createPlanTree();
    }

    private void createPlanFilteredTree(Composite parent) {
        PatternFilter filter = new PatternFilter();

        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        int treeStyle = SWT.H_SCROLL | SWT.V_SCROLL;
        planFilteredTree = new FilteredTree(parent, treeStyle, filter, true);
        planFilteredTree.setLayoutData(layoutData);
    }

    private void createPlanTree() {
        planTree = planTreeViewer.getTree();
        planTree.addSelectionListener(createSelectionAdapterToPlanTree());

        createMenuManagerToPlanTree();
    }

    private void createMenuManagerToPlanTree() {
        planTreeMenuManager = new MenuManager();
        planTreeMenuManager.setRemoveAllWhenShown(true);
        createContextMenuToPlanTreeViewerFrom(planTreeMenuManager);
    }

    private SelectionAdapter createSelectionAdapterToPlanTree() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 3340331109114798554L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                Object selecteNode = getSelectedNodeOnNavigationTreeViewer();
                if (selecteNode instanceof Version) {
                    Version selectedVersion = (Version) selecteNode;
                    populateDeviceTableFrom(selectedVersion);
                } else {
                    clearRowsOnDeviceTable();
                }
            }
        };
    }

    private void createContextMenuToPlanTreeViewerFrom(MenuManager menuManager) {
        Menu contextMenu = menuManager.createContextMenu(planTree);
        planTree.setMenu(contextMenu);
    }

    public Object getSelectedNodeOnNavigationTreeViewer() {
        ISelection selection = planTreeViewer.getSelection();
        IStructuredSelection sselection = (IStructuredSelection) selection;
        return sselection.getFirstElement();
    }

    private void populateDeviceTableFrom(Version selectedVersion) {
        IViewPart viewPart = workbenchPage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewPart).populateDeviceTableFrom(selectedVersion);
    }

    private void clearRowsOnDeviceTable() {
        IViewPart viewPart = workbenchPage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewPart).clearRowsOnDeviceTable();
    }

    public void registerPlanTreeContextMenuToWorkbenchPartSite() {
        workbenchPartSite.registerContextMenu(planTreeMenuManager, planTreeViewer);
    }

    public void setSelectionProviderToWorkbenchPartSite() {
        workbenchPartSite.setSelectionProvider(planTreeViewer);
    }

    public void populatePlanTreeViewerFrom(List<Plan> plans) {
        planTreeViewer.setInput(plans);
    }

    public void addSelectionChangedListener(ISelectionChangedListener selectionChangedListener) {
        planTreeViewer.addSelectionChangedListener(selectionChangedListener);
    }

    public void refreshPlanTreeViewer() {
        planTreeViewer.refresh();
    }

    public void setSelectionToPlanTreeViewerBy(Project selectedProject) {
        planTreeViewer.setSelection(new StructuredSelection(selectedProject), true);
    }
    
    public void setSelectionToPlanTreeViewerBy(Version selectedVersion) {
        planTreeViewer.setSelection(new StructuredSelection(selectedVersion), true);
    }
}
