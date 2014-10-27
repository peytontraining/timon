package vn.enclave.peyton.fusion.view.form;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;

public class DeviceDetailsForm {

    private Label icon;

    private Text nameText;

    private Text manufacturerText;

    private Text modelNumberText;

    private Text typesText;

    private Text notesText;

    private Text versionText;

    private Text lastModifiedText;

    private ScrolledForm form;

    private DeviceTemplate device;

    public Label getIcon() {
        return icon;
    }

    public void setIcon(Label icon) {
        this.icon = icon;
    }

    public Text getNameText() {
        return nameText;
    }

    public void setNameText(Text nameText) {
        this.nameText = nameText;
    }

    public Text getManufacturerText() {
        return manufacturerText;
    }

    public void setManufacturerText(Text manufacturerText) {
        this.manufacturerText = manufacturerText;
    }

    public Text getModelNumberText() {
        return modelNumberText;
    }

    public void setModelNumberText(Text modelNumberText) {
        this.modelNumberText = modelNumberText;
    }

    public Text getTypesText() {
        return typesText;
    }

    public void setTypesText(Text typesText) {
        this.typesText = typesText;
    }

    public Text getNotesText() {
        return notesText;
    }

    public void setNotesText(Text notesText) {
        this.notesText = notesText;
    }

    public Text getVersionText() {
        return versionText;
    }

    public void setVersionText(Text versionText) {
        this.versionText = versionText;
    }

    public Text getLastModifiedText() {
        return lastModifiedText;
    }

    public void setLastModifiedText(Text lastModifiedText) {
        this.lastModifiedText = lastModifiedText;
    }

    public ScrolledForm getForm() {
        return form;
    }

    public DeviceDetailsForm(Composite parent) {
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        // Create the ScrolledForm and set layout for it.
        form = toolkit.createScrolledForm(parent);
        form.getBody().setLayout(new GridLayout(2, false));

        // Create content for the form.
        createDetailsFormContent(toolkit, form);
    }

    /*
     * Create all buttons, labels and texts for form.
     */
    private
        void createDetailsFormContent(FormToolkit toolkit, ScrolledForm form) {
        GridData layoutData;

        layoutData = new GridData(SWT.LEFT, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Icon:");
        icon = toolkit.createLabel(form.getBody(), "");
        icon.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Name:");
        nameText = toolkit.createText(form.getBody(), "");
        nameText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Manufacturer:");
        manufacturerText = toolkit.createText(form.getBody(), "");
        manufacturerText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Model Number:");
        modelNumberText = toolkit.createText(form.getBody(), "");
        modelNumberText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Types:");
        typesText = toolkit.createText(form.getBody(), "");
        typesText.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Notes:");
        notesText = toolkit.createText(form.getBody(), "", SWT.MULTI);
        layoutData.heightHint = notesText.getLineHeight() * 5;
        notesText.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Version:");
        versionText = toolkit.createText(form.getBody(), "");
        versionText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Last Modfied:");
        lastModifiedText = toolkit.createText(form.getBody(), "");
        lastModifiedText.setLayoutData(layoutData);
    }

    public void setDataInForm(DeviceTemplate template) {
        this.device = template;
        String name = template.getName();
        String manufacturer = template.getManufacturer();
        String modelNumber = template.getModelNumber();
        String types = template.getDeviceType().getName();
        String notes = template.getNotes();
        String version = template.getVersion();
        String lastModified =
            Utils.convertDate2String(template.getLastModified());
        String pluginId = template.getIcon().getPluginId();
        String imageFilePath = template.getIcon().getImageFilePath();

        icon.setImage(Utils.createImage(pluginId, imageFilePath));
        nameText.setText(name);
        manufacturerText.setText(manufacturer);
        modelNumberText.setText(modelNumber);
        typesText.setText(types);
        notesText.setText(notes);
        versionText.setText(version);
        lastModifiedText.setText(lastModified);
    }

    public Device getNewDevice() {
        Device newDevice = new Device();
        newDevice.setAppModule(device.getDeviceType().getModule().getName());
        newDevice.setDeviceType(device.getDeviceType().getName());
        newDevice.setIcon(device.getIcon());
        newDevice.setLastModified(new Date());
        newDevice.setManufacture(device.getManufacturer());
        newDevice.setModelNumber(device.getModelNumber());
        newDevice.setName(nameText.getText());
        newDevice.setNotes(notesText.getText());
        newDevice.setVersionDevice(device.getVersion());
        newDevice.setPhysicalLocation("");

        return newDevice;
    }

    public void setDataInForm(Device device) {
        String name = device.getName();
        String manufacturer = device.getManufacture();
        String modelNumber = device.getModelNumber();
        String types = device.getDeviceType();
        String notes = device.getNotes();
        String version = device.getVersionDevice();
        String lastModified =
            Utils.convertDate2String(device.getLastModified());
        String pluginId = device.getIcon().getPluginId();
        String imageFilePath = device.getIcon().getImageFilePath();

        icon.setImage(Utils.createImage(pluginId, imageFilePath));
        nameText.setText(name);
        manufacturerText.setText(manufacturer);
        modelNumberText.setText(modelNumber);
        typesText.setText(types);
        notesText.setText(notes);
        versionText.setText(version);
        lastModifiedText.setText(lastModified);
    }
}
