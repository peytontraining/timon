package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.filter.TemplateFilter;
import vn.enclave.peyton.fusion.provider.TemplateTreeContentProvider;
import vn.enclave.peyton.fusion.provider.TemplateTreeTableLabelProvider;
import vn.enclave.peyton.fusion.service.impl.ModuleService;

public class DeviceTemplateViewPart extends ViewPart implements IDoubleClickListener {
    public static final String ID = "vn.enclave.peyton.fusion.view.deviceTemplatesViewPart";
    private static final String[] TITLES = {"Name", "Last Modified", "Manufacture", "Model Number", "Version"};
    private static final int DEFAULT_WIDTH = 120;
    private TreeViewer treeViewer;
    private Text filterText;
    private Text finderText;
    private Button clear;
    private Button up;
    private Button down;
    private TemplateFilter filter;
    private IWorkbenchPage activePage;

    @Override
    public void createPartControl(Composite parent) {
        createActivePage();

        // Layout the parent.
        GridLayout layout = new GridLayout(9, false);
        parent.setLayout(layout);

        // Create the filter part.
        createFilter(parent);

        // Create the finder part.
        createFinder(parent);

        // Create and layout a Tree.
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 9, 1);
        int style = SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
        Tree tree = new Tree(parent, style);
        tree.setLayoutData(layoutData);

        // Make the header and line of tree is visible.
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);

        // Create columns for tree.
        createColumns(tree);

        // Create a TreeViewer.
        treeViewer = new TreeViewer(tree);

        // Set ContentProvider and LabelProvider for treeViewer.
        treeViewer.setContentProvider(new TemplateTreeContentProvider());
        treeViewer.setLabelProvider(new TemplateTreeTableLabelProvider());

        // Set data for treeViewer.
        ModuleService moduleService = new ModuleService();
        treeViewer.setInput(moduleService.getAll());

        // Create and set filter for treeViewer.
        filter = new TemplateFilter();
        treeViewer.addFilter(filter);

        treeViewer.addDoubleClickListener(this);

        getSite().setSelectionProvider(treeViewer);
    }

    private void createActivePage() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        activePage = window.getActivePage();
    }

    @Override
    public void setFocus() {
        treeViewer.getTree().setFocus();
    }

    private void createFilter(Composite parent) {
        // Create filter label.
        Label label = new Label(parent, SWT.NONE);
        label.setText("Filter:");

        // Create and layout filter text.
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        int style = SWT.BORDER | SWT.SEARCH;
        filterText = new Text(parent, style);
        filterText.setLayoutData(layoutData);
        filterText.addModifyListener(new ModifyListener() {

            private static final long serialVersionUID = 4906617449904102933L;

            @Override
            public void modifyText(ModifyEvent event) {
                filter.setFilterString(filterText.getText());
                treeViewer.refresh();
                boolean enabled = filterText.getText() != null && filterText.getText().length() != 0;
                clear.setEnabled(enabled);
            }
        });

        // Create clear button
        clear = new Button(parent, SWT.NONE);
        clear.setImage(Constant.IMAGE_EDIT);
        clear.setEnabled(false);
        clear.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = 8727982726761507228L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                filterText.setText("");
                clear.setEnabled(false);
            }
        });
    }

    private void createFinder(Composite parent) {
        // Create filter label.
        GridData layoutData = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
        Label label = new Label(parent, SWT.NONE);
        label.setText("Find:");
        label.setLayoutData(layoutData);

        // Create and layout filter text.
        layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        int style = SWT.BORDER | SWT.SEARCH;
        finderText = new Text(parent, style);
        finderText.setLayoutData(layoutData);

        // Create up button
        up = new Button(parent, SWT.NONE);
        up.setImage(Constant.IMAGE_ARROW_UP);

        // Create down button
        down = new Button(parent, SWT.NONE);
        down.setImage(Constant.IMAGE_ARROW_DOWN);
    }

    private void createColumns(Tree parent) {
        for (String title : TITLES) {
            createTreeColumn(parent, title);
        }
    }

    private void createTreeColumn(Tree parent, String title) {
        TreeColumn column = new TreeColumn(parent, SWT.NONE);
        column.setText(title);
        column.setWidth(DEFAULT_WIDTH);
    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
        ISelection selection = event.getSelection();
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object firstElement = sselection.getFirstElement();
        if (firstElement instanceof DeviceTemplate) {
            try {
                DeviceTemplate deviceTemplate = (DeviceTemplate) firstElement;
                showTemplateDetailViewPartAndPopulateFrom(deviceTemplate);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTemplateDetailViewPartAndPopulateFrom(DeviceTemplate template) throws PartInitException {
        String viewId = DetailedDeviceTemplateViewPart.ID;
        String secondaryId = String.valueOf(template.getId());
        int mode = IWorkbenchPage.VIEW_ACTIVATE;
        IViewPart viewPart = null;
        if (isViewPartOpened(viewId, secondaryId)) {
            viewPart = activePage.showView(viewId, secondaryId, mode);
            ((DetailedDeviceTemplateViewPart) viewPart).populateViewPartFrom(template);
        } else {
            viewPart = activePage.showView(viewId, secondaryId, mode);
        }

    }

    private boolean isViewPartOpened(String viewId, String secondaryId) {
        return activePage.findViewReference(viewId, secondaryId) == null;
    }
}