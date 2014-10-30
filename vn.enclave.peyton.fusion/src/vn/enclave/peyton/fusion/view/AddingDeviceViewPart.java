package vn.enclave.peyton.fusion.view;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.form.NewDeviceForm;

public class AddingDeviceViewPart extends ViewPart implements ISaveablePart {

    public static final String ID =
        "vn.enclave.peyton.fusion.view.addingDeviceViewPart";

    private boolean isDirty;

    private boolean isCanceled;

    private NewDeviceForm newDeviceForm;

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        firePropertyChange(PROP_DIRTY);
    }

    public void setCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public NewDeviceForm getNewDeviceForm() {
        return newDeviceForm;
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        saveDevice();
        monitor.setCanceled(isCanceled);
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

        ToolItem updateDevice =
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
        TabItem detailsTabItem = new TabItem(parent, SWT.NONE);
        detailsTabItem.setText("Details");

        newDeviceForm = new NewDeviceForm(parent);
        newDeviceForm.addModifyListener(modifyListener);

        detailsTabItem.setControl(newDeviceForm.getScrolledForm());
    }

    private void createConfigureTabItem(TabFolder parent) {
        TabItem configureTabItem = new TabItem(parent, SWT.NONE);
        configureTabItem.setText("Configure");
    }

    @Override
    public void setFocus() {

    }

    public void setData(DeviceTemplate template) {
        changeTitleAndImage(template);
        newDeviceForm.fillInForm(template);
    }

    private void changeTitleAndImage(DeviceTemplate template) {
        String pluginId = template.getIcon().getPluginId();
        String imageFilePath = template.getIcon().getImageFilePath();
        setTitleImage(Utils.createImage(pluginId, imageFilePath));
        setPartName(template.getName());
    }

    private ModifyListener modifyListener = new ModifyListener() {

        private static final long serialVersionUID = -417147842389328557L;

        @Override
        public void modifyText(ModifyEvent event) {
            setDirty(true);
        }
    };

    private SelectionAdapter saveAdapter = new SelectionAdapter() {

        private static final long serialVersionUID = 8118227841225144756L;

        @Override
        public void widgetSelected(SelectionEvent e) {
            saveDevice();
        }

    };

    private void saveDevice() {
        ISelection selection =
            getSite().getPage().getSelection(NavigationViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object firstElement = sselection.getFirstElement();
        if (firstElement instanceof Version) {
            Version selectedVersion = (Version) firstElement;

            Device newDevice = addDevice2Database(selectedVersion);

            addDevice2Tree(selectedVersion, newDevice);

            setDirty(false);
            setCanceled(false);
        } else {
            setCanceled(true);
            createWarningMessageDialog();
        }
    }

    private Device addDevice2Database(Version selectedVersion) {
        /*
         * Get new device from form, then set the selected version to it.
         */
        Device newDevice = newDeviceForm.getDevice();
        newDevice.setVersion(selectedVersion);

        /*
         * Save device into database.
         */
        DeviceService deviceService = new DeviceService();
        newDevice = deviceService.insert(newDevice);
        return newDevice;
    }

    private void addDevice2Tree(Version selectedVersion, Device newDevice) {
        /*
         * Set the selected version to the new device, which returned from
         * addDevice2Database() method.
         */
        newDevice.setVersion(selectedVersion);

        /*
         * Add the new device into the list devices of the selected version.
         */
        selectedVersion.getDevices().add(newDevice);

        /*
         * Refresh the device table to display the new device.
         */
        ((DeviceTableViewPart) getSite()
            .getWorkbenchWindow().getActivePage()
            .findView(DeviceTableViewPart.ID)).getViewer().refresh();
    }

    private void createWarningMessageDialog() {
        Shell shell = getSite().getShell();
        String title = "Warning";
        String message = "Please choose a version to add the device!";
        MessageDialog.openWarning(shell, title, message);
    }
}
