package vn.enclave.peyton.fusion.view.form;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
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
import vn.enclave.peyton.fusion.entity.Icon;

public class NewDeviceForm {

    @SuppressWarnings("unused")
    private Label iconLbl;

    private Label iconContentLbl;

    @SuppressWarnings("unused")
    private Label nameLbl;

    @SuppressWarnings("unused")
    private Label manufacturerLbl;

    @SuppressWarnings("unused")
    private Label modelNumberLbl;

    @SuppressWarnings("unused")
    private Label typesLbl;

    @SuppressWarnings("unused")
    private Label notesLbl;

    @SuppressWarnings("unused")
    private Label versionLbl;

    @SuppressWarnings("unused")
    private Label lastModifiedLbl;

    private Text nameTxt;

    private Text manufacturerTxt;

    private Text modelNumberTxt;

    private Text typesTxt;

    private Text notesTxt;

    private Text versionTxt;

    private Text lastModifiedTxt;

    private ScrolledForm scrolledForm;

    private DeviceTemplate template;

    private Device selectedDevice;

    public ScrolledForm getScrolledForm() {
        return scrolledForm;
    }

    public NewDeviceForm(Composite parent) {
        createForm(parent);
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    private void createForm(Composite parent) {
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        scrolledForm = buildForm(parent, toolkit);
        Composite formBody = scrolledForm.getBody();

        iconLbl = buildLabel(formBody, toolkit, "Icon");
        iconContentLbl = buildLabel(formBody, toolkit, "");

        nameLbl = buildLabel(formBody, toolkit, "Name:");
        nameTxt = buildText(formBody, toolkit);

        manufacturerLbl = buildLabel(formBody, toolkit, "Manufacturer:");
        manufacturerTxt = buildText(formBody, toolkit);

        modelNumberLbl = buildLabel(formBody, toolkit, "Model Number:");
        modelNumberTxt = buildText(formBody, toolkit);

        typesLbl = buildLabel(formBody, toolkit, "Types:");
        typesTxt = buildText(formBody, toolkit);

        notesLbl = buildLabel(formBody, toolkit, "Notes:");
        notesTxt = buildTextArea(formBody, toolkit);

        versionLbl = buildLabel(formBody, toolkit, "Version:");
        versionTxt = buildText(formBody, toolkit);

        lastModifiedLbl = buildLabel(formBody, toolkit, "Last Modified:");
        lastModifiedTxt = buildText(formBody, toolkit);
    }

    private ScrolledForm buildForm(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        ScrolledForm form = toolkit.createScrolledForm(parent);
        form.getBody().setLayout(layout);
        return form;
    }

    private Label buildLabel(Composite parent, FormToolkit toolkit, String text) {
        Label label = toolkit.createLabel(parent, text);
        return label;
    }

    private Text buildText(Composite parent, FormToolkit toolkit) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        Text text = toolkit.createText(parent, "");
        text.setLayoutData(layoutData);
        return text;
    }

    private Text buildTextArea(Composite parent, FormToolkit toolkit) {
        Text text = toolkit.createText(parent, "", SWT.MULTI);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        layoutData.heightHint = text.getLineHeight() * 5;
        text.setLayoutData(layoutData);
        return text;
    }

    public void populateNewDeviceFormFrom(DeviceTemplate template) {
        /*
         * Set data to template for using in getDevice() method.
         */
        this.template = template;

        setIcon(template.getIcon());
        setDefaultText(template);

        /*
         * Layout all children of form. Fix an issue: icon doesn't show after
         * the first open of ViewPart.
         */
        scrolledForm.layout(true, true);
    }

    private void setDefaultText(DeviceTemplate template) {
        String lastModified = Utils.convertDate2String(template.getLastModified());

        nameTxt.setText(template.getName());
        manufacturerTxt.setText(template.getManufacturer());
        modelNumberTxt.setText(template.getModelNumber());
        typesTxt.setText(template.getDeviceType().getName());
        notesTxt.setText(template.getNotes());
        versionTxt.setText(template.getVersion());
        lastModifiedTxt.setText(lastModified);
    }

    private void setIcon(Icon icon) {
        iconContentLbl.setImage(Utils.createImageFromIcon(icon));
    }

    public Device getNewDevice() {
        Device newDevice = new Device();
        newDevice.setIcon(template.getIcon());
        newDevice.setName(nameTxt.getText());
        newDevice.setManufacturer(manufacturerTxt.getText());
        newDevice.setModelNumber(modelNumberTxt.getText());
        newDevice.setDeviceType(typesTxt.getText());
        newDevice.setNotes(notesTxt.getText());
        newDevice.setVersionDevice(versionTxt.getText());
        newDevice.setLastModified(Utils.convertString2Date(lastModifiedTxt.getText()));
        newDevice.setAppModule(template.getDeviceType().getModule().getName());
        return newDevice;
    }

    public void addModifyListener(ModifyListener modifyListener) {
        nameTxt.addModifyListener(modifyListener);
        manufacturerTxt.addModifyListener(modifyListener);
        modelNumberTxt.addModifyListener(modifyListener);
        typesTxt.addModifyListener(modifyListener);
        notesTxt.addModifyListener(modifyListener);
        versionTxt.addModifyListener(modifyListener);
    }

    public Device prepareModifiedDevice() {
        selectedDevice.setName(nameTxt.getText());
        selectedDevice.setManufacturer(manufacturerTxt.getText());
        selectedDevice.setModelNumber(modelNumberTxt.getText());
        selectedDevice.setNotes(notesTxt.getText());
        selectedDevice.setLastModified(new Date());
        return selectedDevice;
    }
}
