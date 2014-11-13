package vn.enclave.peyton.fusion.view;

import java.util.List;

import org.eclipse.jface.action.ToolBarManager;
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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.Module;
import vn.enclave.peyton.fusion.filter.TemplatePatternFilter;
import vn.enclave.peyton.fusion.provider.TemplateTreeContentProvider;
import vn.enclave.peyton.fusion.provider.TemplateTreeTableLabelProvider;
import vn.enclave.peyton.fusion.service.impl.ModuleService;

public class DeviceTemplateViewPart extends ViewPart implements IDoubleClickListener {
    public static final String ID = "vn.enclave.peyton.fusion.view.deviceTemplatesViewPart";
    private static final String[] TITLES = {"Name", "Last Modified", "Manufacture", "Model Number", "Version"};
    private static final int DEFAULT_WIDTH = 120;
    private Tree deviceTemplateTree;
    private TreeViewer deviceTemplatetreeViewer;
    private Label filterLbl;
    private Label finderLbl;
    private Text filterTxt;
    @SuppressWarnings("unused")
    private Text finderTxt;
    private Button clearBtn;
    private Button upBtn;
    private Button downBtn;
    // TODO private TemplateFilter templateFilter = new TemplateFilter();
    TemplatePatternFilter patternFilter = new TemplatePatternFilter();
    private IWorkbenchPage activePage;

    @Override
    public void createPartControl(Composite parent) {
        createActivePage();

        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginRight = -5;
        layout.marginLeft = -5;
        layout.horizontalSpacing = 0;
        parent.setLayout(layout);

        createToolbarCompositeInside(parent);
        createFilterAndFinderCompositeInside(parent);
        createDeviceTemplateTreeViewerCompositeInside(parent);
    }

    private void createActivePage() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        activePage = window.getActivePage();
    }

    private void createToolbarCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbarTo(toolbarComposite);
    }

    private void createFilterAndFinderCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(2, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite filterAndFinderComposite = new Composite(parent, SWT.NONE);
        filterAndFinderComposite.setLayout(layout);
        filterAndFinderComposite.setLayoutData(layoutData);

        createFilterCompositeInside(filterAndFinderComposite);
        createFinderCompositeInside(filterAndFinderComposite);
    }

    private void createDeviceTemplateTreeViewerCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite deviceTemplateTreeComposite = new Composite(parent, SWT.NONE);
        deviceTemplateTreeComposite.setLayout(layout);
        deviceTemplateTreeComposite.setLayoutData(layoutData);

        createDeviceTemplateTreeTo(deviceTemplateTreeComposite);
    }

    private void createDeviceTemplateTreeTo(Composite deviceTemplateTreeComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        int style = SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
        deviceTemplateTree = new Tree(deviceTemplateTreeComposite, style);
        deviceTemplateTree.setHeaderVisible(true);
        deviceTemplateTree.setLinesVisible(true);
        deviceTemplateTree.setLayoutData(layoutData);

        deviceTemplatetreeViewer = new TreeViewer(deviceTemplateTree);
        deviceTemplatetreeViewer.setContentProvider(new TemplateTreeContentProvider());
        deviceTemplatetreeViewer.setLabelProvider(new TemplateTreeTableLabelProvider());
        // TODO deviceTemplatetreeViewer.addFilter(templateFilter);
        deviceTemplatetreeViewer.addFilter(patternFilter);
        deviceTemplatetreeViewer.addDoubleClickListener(this);

        getSite().setSelectionProvider(deviceTemplatetreeViewer);

        createAllColumnsToDeviceTemplateTreeViewer();

        populateDeviceTemplateTree();

    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        ToolBarManager toolBarManager = new ToolBarManager(toolBar);
        IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
        menuService.populateContributionManager(toolBarManager, Constant.TOOLBAR_DEVICE_TEMPLATE_VIEW_PART);
        toolBarManager.update(true);
    }

    private void createFilterCompositeInside(Composite filterAndFinderComposite) {
        GridLayout layout = new GridLayout(3, false);
        layout.marginRight = 20;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite filterComposite = new Composite(filterAndFinderComposite, SWT.NONE);
        filterComposite.setLayout(layout);
        filterComposite.setLayoutData(layoutData);

        createFilterControlsInside(filterComposite);
    }

    private void createFinderCompositeInside(Composite filterAndFinderComposite) {
        GridLayout layout = new GridLayout(4, false);
        layout.marginLeft = 20;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite finderComposite = new Composite(filterAndFinderComposite, SWT.NONE);
        finderComposite.setLayout(layout);
        finderComposite.setLayoutData(layoutData);

        createFinderControlsInside(finderComposite);
    }

    private void createFilterControlsInside(Composite filterComposite) {
        filterLbl = new Label(filterComposite, SWT.NONE);
        filterLbl.setText("Filter:");

        filterTxt = createText(filterComposite);
        filterTxt.addModifyListener(createModifyListenerToFilterText());

        clearBtn = createButton(filterComposite);
        clearBtn.setImage(Constant.IMAGE_EDIT);
        clearBtn.setEnabled(false);
        clearBtn.addSelectionListener(createSelectionAdapterToClearButton());
    }

    private void createFinderControlsInside(Composite finderComposite) {
        finderLbl = new Label(finderComposite, SWT.NONE);
        finderLbl.setText("Find:");

        finderTxt = createText(finderComposite);

        upBtn = createButton(finderComposite);
        upBtn.setImage(Constant.IMAGE_ARROW_UP);

        downBtn = createButton(finderComposite);
        downBtn.setImage(Constant.IMAGE_ARROW_DOWN);
    }

    private Text createText(Composite composite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Text text = new Text(composite, SWT.BORDER);
        text.setLayoutData(layoutData);
        return text;
    }

    private ModifyListener createModifyListenerToFilterText() {
        return new ModifyListener() {
            private static final long serialVersionUID = 4906617449904102933L;

            @Override
            public void modifyText(ModifyEvent event) {
                // templateFilter.setFilterString(filterTxt.getText());
                patternFilter.setPattern(filterTxt.getText());
                deviceTemplatetreeViewer.refresh();
                clearBtn.setEnabled(isEnableClearButton());
            }
        };
    }

    private boolean isEnableClearButton() {
        return filterTxt.getText() != null & filterTxt.getText().length() != 0;
    }

    private Button createButton(Composite composite) {
        Button button = new Button(composite, SWT.PUSH);
        return button;
    }

    private SelectionAdapter createSelectionAdapterToClearButton() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 8727982726761507228L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                filterTxt.setText("");
                clearBtn.setEnabled(false);
            }
        };
    }

    private void createAllColumnsToDeviceTemplateTreeViewer() {
        for (String title : TITLES) {
            createTreeColumnHas(title);
        }
    }

    private void populateDeviceTemplateTree() {
        ModuleService moduleService = new ModuleService();
        List<Module> modules = moduleService.getAll();
        deviceTemplatetreeViewer.setInput(modules);
    }

    private void createTreeColumnHas(String title) {
        TreeColumn column = new TreeColumn(deviceTemplateTree, SWT.NONE);
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

    public void refreshDeviceTemplateTree() {
        deviceTemplatetreeViewer.refresh();
    }

    @Override
    public void setFocus() {
    }
}