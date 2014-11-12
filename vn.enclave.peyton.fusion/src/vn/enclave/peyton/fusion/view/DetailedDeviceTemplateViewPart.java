package vn.enclave.peyton.fusion.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.control.DevicePropertyTemplateSection;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.service.impl.DeviceTemplateService;
import vn.enclave.peyton.fusion.view.form.DetailedDeviceTemplateForm;

public class DetailedDeviceTemplateViewPart extends ViewPart implements ISaveablePart {
    public static final String ID = "vn.enclave.peyton.fusion.view.DetailedDeviceTemplateViewPart";
    private DetailedDeviceTemplateForm detailedDeviceTemplateForm;
    private DevicePropertyTemplateSection devicePropertyTemplateSection;
    private boolean isDirty;
    private boolean isFormReady;
    private IWorkbenchPage activePage;
    private DeviceTemplate selectedDeviceTemplate;

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        firePropertyChange(PROP_DIRTY);
    }

    public void setFormReady(boolean isFormReady) {
        this.isFormReady = isFormReady;
    }

    @Override
    public void createPartControl(Composite parent) {
        createActivePage();

        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);
        createToolbarCompositeInside(parent);

        createTabFolderCompositeInside(parent);
    }

    private void createActivePage() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        activePage = window.getActivePage();
    }

    private void createToolbarCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.None);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbarTo(toolbarComposite);
    }

    private void createTabFolderCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -10;
        layout.marginWidth = 0;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite tabFolderComposite = new Composite(parent, SWT.NONE);
        tabFolderComposite.setLayout(layout);
        tabFolderComposite.setLayoutData(layoutData);

        createTabFolderTo(tabFolderComposite);
    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        ToolBarManager toolBarManager = new ToolBarManager(toolBar);
        IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
        menuService.populateContributionManager(toolBarManager, Constant.TOOLBAR_DETAILED_DEVICE_TEMPLATE_VIEW_PART);
        toolBarManager.update(true);

    }

    private void createTabFolderTo(Composite tabFolderComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder tabFolder = new TabFolder(tabFolderComposite, SWT.NONE);
        tabFolder.setLayoutData(layoutData);

        createDetailsTabItemInside(tabFolder);

        createConfigureTabItemInside(tabFolder);
    }

    private void createDetailsTabItemInside(TabFolder tabFolder) {
        TabItem detailsTabItem = new TabItem(tabFolder, SWT.NONE);
        detailsTabItem.setText("Details");

        detailedDeviceTemplateForm = new DetailedDeviceTemplateForm(tabFolder);
        detailedDeviceTemplateForm.addModifyListener(new ModifyListener() {
            private static final long serialVersionUID = 3461426635293169609L;

            @Override
            public void modifyText(ModifyEvent event) {
                setDirty(true && isFormReady);
            }
        });

        detailsTabItem.setControl(detailedDeviceTemplateForm.getDetailedDeviceTemplateForm());
    }

    private void createConfigureTabItemInside(TabFolder tabFolder) {
        TabItem configureTabItem = new TabItem(tabFolder, SWT.NONE);
        configureTabItem.setText("Configure");

        devicePropertyTemplateSection = new DevicePropertyTemplateSection(tabFolder);

        configureTabItem.setControl(devicePropertyTemplateSection.getPropertyTemplateSection());
    }

    public void populateViewPartFrom(DeviceTemplate deviceTemplate) {
        selectedDeviceTemplate = deviceTemplate;
        changeNameAndImageViewPartFrom(deviceTemplate);
        detailedDeviceTemplateForm.populateDeviceTemplateFormFrom(deviceTemplate);
        devicePropertyTemplateSection.populatePropertyTreeViewerFrom(deviceTemplate);
        setFormReady(true);
    }

    private void changeNameAndImageViewPartFrom(DeviceTemplate template) {
        setPartName(template.getName());
        setTitleImage(Utils.createImageFromIcon(template.getIcon()));
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        updateDeviceTemplate();
        refreshDeviceTable();
    }

    public void updateDeviceTemplate() {
        selectedDeviceTemplate = getModifiedDeviceTemplate();
        DeviceTemplateService deviceTemplateService = new DeviceTemplateService();
        deviceTemplateService.update(selectedDeviceTemplate);
    }

    public void refreshDeviceTable() {
        IViewPart viewpart = activePage.findView(DeviceTemplateViewPart.ID);
        ((DeviceTemplateViewPart) viewpart).refreshDeviceTemplateTree();
    }

    public DeviceTemplate getModifiedDeviceTemplate() {
        return detailedDeviceTemplateForm.getModifiedDeviceTemplate();
    }

    public void changeTitleOfViewPart(String title) {
        setPartName(title);
    }

    @Override
    public void setFocus() {
    }

    @Override
    public void doSaveAs() {
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public boolean isSaveOnCloseNeeded() {
        return true;
    }

}
