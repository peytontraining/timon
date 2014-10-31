package vn.enclave.peyton.fusion.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.control.DevicePropertySection;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.form.DetailedDeviceForm;

public class ModifyingDeviceViewPart extends ViewPart implements ISaveablePart {

    public static final String ID =
        "vn.enalve.peyton.fusion.view.modifyingDeviceViewPart";

    private boolean isDirty;

    private boolean isFilled;

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        firePropertyChange(PROP_DIRTY);
    }

    public void setFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    private Device selectedDevice;

    private DetailedDeviceForm detailedDeviceForm;

    private DevicePropertySection devicePropertySection;

    @Override
    public void doSave(IProgressMonitor monitor) {
        updateDevice();
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

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);

        createToolbarComposite(parent);

        createTabFolderComposite(parent);
    }

    private void createToolbarComposite(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbar(toolbarComposite);
    }

    private void createToolbar(Composite parent) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar bar = new ToolBar(parent, SWT.FLAT);
        bar.setLayoutData(layoutData);

        createToolItems(bar);
    }

    private void createToolItems(ToolBar parent) {
        ToolItem save = createToolItem(parent, Constant.IMAGE_SAVE);
        save.addSelectionListener(saveAdapter);

        ToolItem saveAndClose =
            createToolItem(parent, Constant.IMAGE_SAVE_CLOSE);

        new ToolItem(parent, SWT.SEPARATOR);

        ToolItem updateDevcie =
            createToolItem(parent, Constant.IMAGE_UPDATE_DEVICE);

        ToolItem showDevice =
            createToolItem(parent, Constant.IMAGE_SHOW_DEVICE);

        new ToolItem(parent, SWT.SEPARATOR);

        ToolItem editService =
            createToolItem(parent, Constant.IMAGE_EDIT_SERVICE);
    }

    private ToolItem createToolItem(ToolBar parent, Image image) {
        ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
        toolItem.setImage(image);
        return toolItem;
    }

    private void createTabFolderComposite(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -10;
        layout.marginWidth = 0;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite tabFolderComposite = new Composite(parent, SWT.NONE);
        tabFolderComposite.setLayout(layout);
        tabFolderComposite.setLayoutData(layoutData);

        createTabFolder(tabFolderComposite);
    }

    private void createTabFolder(Composite parent) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder folder = new TabFolder(parent, SWT.NONE);
        folder.setLayoutData(layoutData);

        createDetailsTabItem(folder);

        createConfigureTabItem(folder);
    }

    private void createDetailsTabItem(TabFolder parent) {
        detailedDeviceForm = new DetailedDeviceForm(parent);
        detailedDeviceForm.addModifyListener(modifyListener);

        TabItem detailsTabItem = new TabItem(parent, SWT.NONE);
        detailsTabItem.setText("Details");
        detailsTabItem.setControl(detailedDeviceForm.getScrolledForm());
    }

    private void createConfigureTabItem(TabFolder parent) {
        devicePropertySection = new DevicePropertySection(parent);

        TabItem configureTabItem = new TabItem(parent, SWT.NONE);
        configureTabItem.setText("Configure");
        configureTabItem.setControl(devicePropertySection.getSection());
    }

    @Override
    public void setFocus() {
    }

    public void setData(Device device) {
        selectedDevice = device;
        setPartName(device.getName());
        setTitleImage(Utils.createImage(device.getIcon()));
        detailedDeviceForm.fillInForm(device);
        devicePropertySection.fillInTree(device.getProperties());
        setFilled(true);
    }

    private SelectionAdapter saveAdapter = new SelectionAdapter() {

        private static final long serialVersionUID = -1772039129772087714L;

        @Override
        public void widgetSelected(SelectionEvent e) {
            updateDevice();
            refreshDeviceTable();
            setPartName(selectedDevice.getName());
            setDirty(false);
        }
    };

    private void updateDevice() {
        selectedDevice = detailedDeviceForm.getModifiedDevice();
        DeviceService deviceService = new DeviceService();
        deviceService.update(selectedDevice);
    }

    private void refreshDeviceTable() {
        IViewPart viewPart =
            getSite().getPage().findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewPart).getViewer().refresh();
    }

    private ModifyListener modifyListener = new ModifyListener() {

        private static final long serialVersionUID = 5212741420498281275L;

        @Override
        public void modifyText(ModifyEvent event) {
            setDirty(true && isFilled);
        }
    };

}