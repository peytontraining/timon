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
import vn.enclave.peyton.fusion.control.DevicePropertySection;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.form.DetailedDeviceForm;

public class ModifyingDeviceViewPart extends ViewPart implements ISaveablePart {
    public static final String ID = "vn.enalve.peyton.fusion.view.modifyingDeviceViewPart";
    private boolean isDirty;
    private boolean isFormReady;
    private Device selectedDevice;
    private DetailedDeviceForm detailedDeviceForm;
    private DevicePropertySection devicePropertySection;
    private IWorkbenchPage activePage;

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
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
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

        createTabFolderInside(tabFolderComposite);
    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        ToolBarManager toolBarManager = new ToolBarManager(toolBar);
        IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
        menuService.populateContributionManager(toolBarManager, Constant.TOOLBAR_MODIFYING_DEVICE_VIEW_PART);
        toolBarManager.update(true);
    }

    private void createTabFolderInside(Composite tabFolderComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder tabFolder = new TabFolder(tabFolderComposite, SWT.NONE);
        tabFolder.setLayoutData(layoutData);

        createDetailsTabItemTo(tabFolder);
        createConfigureTabItemTo(tabFolder);
    }

    private void createDetailsTabItemTo(TabFolder tabFolder) {
        detailedDeviceForm = new DetailedDeviceForm(tabFolder);
        detailedDeviceForm.addModifyListener(createModifyListenerToDetailedDeviceForm());

        TabItem detailsTabItem = new TabItem(tabFolder, SWT.NONE);
        detailsTabItem.setText("Details");
        detailsTabItem.setControl(detailedDeviceForm.getScrolledForm());
    }

    private void createConfigureTabItemTo(TabFolder tabFolder) {
        devicePropertySection = new DevicePropertySection(tabFolder);

        TabItem configureTabItem = new TabItem(tabFolder, SWT.NONE);
        configureTabItem.setText("Configure");
        configureTabItem.setControl(devicePropertySection.getSection());
    }

    private ModifyListener createModifyListenerToDetailedDeviceForm() {
        return new ModifyListener() {

            private static final long serialVersionUID = 5212741420498281275L;

            @Override
            public void modifyText(ModifyEvent event) {
                setDirty(true && isFormReady);
            }
        };
    }

    @Override
    public void setFocus() {}

    public void populateViewPartFrom(Device device) {
        selectedDevice = device;
        setPartName(device.getName());
        setTitleImage(Utils.createImageFromIcon(device.getIcon()));
        detailedDeviceForm.fillInForm(device);
        devicePropertySection.fillInTree(device.getProperties());
        setFormReady(true);
    }

    private void updateDevice() {
        selectedDevice = getModifiedDevice();
        DeviceService deviceService = new DeviceService();
        deviceService.update(selectedDevice);
    }

    public void refreshDeviceTable() {
        IViewPart viewpart = activePage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewpart).refreshDeviceTableViewer();
    }

    public Device getModifiedDevice() {
        return detailedDeviceForm.getModifiedDevice();
    }

    public void changeTitleOfViewPart(String title) {
        setPartName(title);
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        updateDevice();
        refreshDeviceTable();
    }

    @Override
    public void doSaveAs() {}

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
