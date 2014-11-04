package vn.enclave.peyton.fusion.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.control.NewDevicePropertySection;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.Property;
import vn.enclave.peyton.fusion.entity.PropertyTemplate;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.DeviceService;
import vn.enclave.peyton.fusion.view.form.NewDeviceForm;

public class AddingDeviceViewPart extends ViewPart implements ISaveablePart {
    public static final String ID = "vn.enclave.peyton.fusion.view.addingDeviceViewPart";
    private boolean isDirty;
    private boolean isCanceled;
    private NewDeviceForm newDeviceForm;
    private NewDevicePropertySection newDevicePropertySection;
    private IWorkbenchPage activePage;

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        firePropertyChange(PROP_DIRTY);
    }

    public void setCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
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

        createTabFolderTo(tabFolderComposite);
    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        createAllToolItemsTo(toolBar);
    }

    private void createTabFolderTo(Composite tabFolderComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder tabFolder = new TabFolder(tabFolderComposite, SWT.NONE);
        tabFolder.setLayoutData(layoutData);

        createDetailsTabItemInside(tabFolder);

        createConfigureTabItemInside(tabFolder);
    }

    private void createAllToolItemsTo(ToolBar toolBar) {
        ToolItem saveToolItem = createToolItemInside(toolBar);
        saveToolItem.setImage(Constant.IMAGE_SAVE_AS);
        saveToolItem.addSelectionListener(getSelectionAdapterToSaveToolItem());

        ToolItem saveAndCloseToolItem = createToolItemInside(toolBar);
        saveAndCloseToolItem.setImage(Constant.IMAGE_SAVE_AND_CLOSE);

        createSeparatorInside(toolBar);

        ToolItem updateDeviceToolItem = createToolItemInside(toolBar);
        updateDeviceToolItem.setImage(Constant.IMAGE_DEVICE_UPDATE);

        ToolItem showDeviceToolItem = createToolItemInside(toolBar);
        showDeviceToolItem.setImage(Constant.IMAGE_SHOW_DEVICES);

        createSeparatorInside(toolBar);

        ToolItem editServiceToolItem = createToolItemInside(toolBar);
        editServiceToolItem.setImage(Constant.IMAGE_SERVICE);
    }

    private void createDetailsTabItemInside(TabFolder tabFolder) {
        TabItem detailsTabItem = new TabItem(tabFolder, SWT.NONE);
        detailsTabItem.setText("Details");

        newDeviceForm = new NewDeviceForm(tabFolder);
        newDeviceForm.addModifyListener(getModifyListerTo());

        detailsTabItem.setControl(newDeviceForm.getScrolledForm());
    }

    private void createConfigureTabItemInside(TabFolder tabFolder) {
        TabItem configureTabItem = new TabItem(tabFolder, SWT.NONE);
        configureTabItem.setText("Configure");

        newDevicePropertySection = new NewDevicePropertySection(tabFolder);

        configureTabItem.setControl(newDevicePropertySection.getPropertySection());
    }

    private ToolItem createToolItemInside(ToolBar toolBar) {
        ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
        return toolItem;
    }

    private void createSeparatorInside(ToolBar toolBar) {
        new ToolItem(toolBar, SWT.SEPARATOR);
    }

    private SelectionAdapter getSelectionAdapterToSaveToolItem() {
        return new SelectionAdapter() {

            private static final long serialVersionUID = 8118227841225144756L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                saveNewDevice();
            }
        };
    }

    private ModifyListener getModifyListerTo() {
        return new ModifyListener() {

            private static final long serialVersionUID = -417147842389328557L;

            @Override
            public void modifyText(ModifyEvent event) {
                setDirty(true);
            }
        };
    }

    private void saveNewDevice() {
        ISelection selection = activePage.getSelection(NavigationViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object firstElement = sselection.getFirstElement();
        if (firstElement instanceof Version) {
            Version selectedVersion = (Version) firstElement;

            Device newDevice = addNewDeviceToDatabase(selectedVersion);

            addNewDeviceToDeviceTree(selectedVersion, newDevice);

            setDirty(false);
            setCanceled(false);
        } else {
            setCanceled(true);
            createWarningMessageDialog();
        }
    }

    private Device addNewDeviceToDatabase(Version selectedVersion) {
        Device newDevice = prepareNewDevice();
        newDevice.setVersion(selectedVersion);

        DeviceService deviceService = new DeviceService();
        newDevice = deviceService.insert(newDevice);
        return newDevice;
    }

    private void addNewDeviceToDeviceTree(Version selectedVersion, Device newDevice) {
        /*
         * Set the selected version to the new device, which returned from
         * addDevice2Database() method.
         */
        newDevice.setVersion(selectedVersion);

        /*
         * Add the new device into the list devices of the selected version.
         */
        selectedVersion.addDevice(newDevice);

        /*
         * Refresh the device table to display the new device.
         */
        IViewPart viewpart = activePage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewpart).refreshDeviceTableViewer();
    }

    private void createWarningMessageDialog() {
        Shell shell = getSite().getShell();
        String title = "Warning";
        String message = "Please choose a version to add the device!";
        MessageDialog.openWarning(shell, title, message);
    }

    private Device prepareNewDevice() {
        Device newDevice = newDeviceForm.getDevice();
        addPropertyDeviceTo(newDevice);
        return newDevice;
    }

    private void addPropertyDeviceTo(Device newDevice) {
        List<PropertyTemplate> propertyTemplates = newDevicePropertySection.getNewDeviceProperties();
        List<Property> properties = new ArrayList<Property>();
        for (PropertyTemplate propertyTemplate : propertyTemplates) {
            Property property = new Property();
            property.setName(propertyTemplate.getName());
            property.setValue(propertyTemplate.getValue());
            property.setMandatory(propertyTemplate.isMandatory());
            property.setDescription(propertyTemplate.getDescription());
            property.setDevice(newDevice);
            properties.add(property);
        }
        newDevice.setProperties(properties);
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        saveNewDevice();
        monitor.setCanceled(isCanceled);
    }

    public void populateViewPartFrom(DeviceTemplate deviceTemplate) {
        changeNameAndImageViewPartFrom(deviceTemplate);
        newDeviceForm.populateNewDeviceFormFrom(deviceTemplate);
        newDevicePropertySection.populatePropertyTreeViewerFrom(deviceTemplate);
    }

    private void changeNameAndImageViewPartFrom(DeviceTemplate template) {
        setPartName(template.getName());
        setTitleImage(Utils.createImageFromIcon(template.getIcon()));
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
    public void setFocus() {
    }

    @Override
    public void doSaveAs() {
    }
}
