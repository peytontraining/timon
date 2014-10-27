package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.view.form.DeviceDetailsForm;

public class EditDeviceViewPart extends ViewPart {

    public static final String ID =
        "vn.enalve.peyton.fusion.view.editDeviceViewPart";

    private DeviceDetailsForm form;

    public DeviceDetailsForm getForm() {
        return form;
    }

    @Override
    public void createPartControl(Composite parent) {
        // Create the TabFolder.
        TabFolder folder = new TabFolder(parent, SWT.NONE);

        // Create the Detail TabItem.
        createDetailsTab(parent, folder);

        // Create the Configure TabItem.
        createConfigureTab(parent, folder);
    }

    @Override
    public void setFocus() {

    }

    private void createDetailsTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.NONE);
        item.setText("Details");

        // Create the ScrolledForm and set layout for it.
        form = new DeviceDetailsForm(folder);
        ScrolledForm scrolledForm = form.getForm();
        scrolledForm.getBody().setLayout(new GridLayout(2, false));

        // Set the form is the TabItem's control.
        item.setControl(scrolledForm);
    }

    /*
     * Create the Configure TabItem inside TabFolder.
     */
    private void createConfigureTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.BORDER);
        item.setText("Configure");
    }

    public void setData(Device device) {
        String pluginId = device.getIcon().getPluginId();
        String imageFilePath = device.getIcon().getImageFilePath();

        // change the viewpart name.
        setPartName(device.getName());
        setTitleImage(Utils.createImage(pluginId, imageFilePath));

        form.setDataInForm(device);
    }
}
