package vn.enclave.peyton.fusion.view.form;

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

public class DeviceForm {

    @SuppressWarnings("unused")
    private Label iconLbl;

    private Label iconContentLbl;

    @SuppressWarnings("unused")
    private Label nameLbl;

    @SuppressWarnings("unused")
    private Label manufacturerLbl;

    @SuppressWarnings("unused")
    private Label modelNumbeLbl;

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

    public ScrolledForm getScrolledForm() {
        return scrolledForm;
    }

    public DeviceForm(Composite parent) {
        createForm(parent);
    }

    private void createForm(Composite parent) {
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        GridLayout layout = new GridLayout(2, false);
        scrolledForm = buildForm(parent, toolkit);
        scrolledForm.getBody().setLayout(layout);

        iconLbl = buildLabel(scrolledForm.getBody(), toolkit, "Icon");
        iconContentLbl = buildLabel(scrolledForm.getBody(), toolkit, "");

        nameLbl = buildLabel(scrolledForm.getBody(), toolkit, "Name:");
        nameTxt = buildText(scrolledForm.getBody(), toolkit);

        manufacturerLbl =
            buildLabel(scrolledForm.getBody(), toolkit, "Manufacturer:");
        manufacturerTxt = buildText(scrolledForm.getBody(), toolkit);

        modelNumbeLbl =
            buildLabel(scrolledForm.getBody(), toolkit, "Model Number:");
        modelNumberTxt = buildText(scrolledForm.getBody(), toolkit);

        typesLbl = buildLabel(scrolledForm.getBody(), toolkit, "Types:");
        typesTxt = buildText(scrolledForm.getBody(), toolkit);

        notesLbl = buildLabel(scrolledForm.getBody(), toolkit, "Notes:");
        notesTxt = buildText(scrolledForm.getBody(), toolkit);

        versionLbl = buildLabel(scrolledForm.getBody(), toolkit, "Version:");
        versionTxt = buildText(scrolledForm.getBody(), toolkit);

        lastModifiedLbl =
            buildLabel(scrolledForm.getBody(), toolkit, "Last Modified:");
        lastModifiedTxt = buildText(scrolledForm.getBody(), toolkit);
    }

    private Text buildText(Composite parent, FormToolkit toolkit) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        Text text = toolkit.createText(parent, "");
        text.setLayoutData(layoutData);
        return text;
    }

    private
        Label buildLabel(Composite parent, FormToolkit toolkit, String text) {
        Label label = toolkit.createLabel(parent, text);
        return label;
    }

    private ScrolledForm buildForm(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        ScrolledForm form = toolkit.createScrolledForm(parent);
        form.setLayout(layout);
        return form;
    }

    public void fillInForm(DeviceTemplate template) {
        this.template = template;
        setIcon(template);
        setDefaultText(template);
    }

    private void setDefaultText(DeviceTemplate template) {
        nameTxt.setText(template.getName());
        manufacturerTxt.setText(template.getManufacturer());
        modelNumberTxt.setText(template.getModelNumber());
        typesTxt.setText(template.getDeviceType().getName());
        notesTxt.setText(template.getNotes());
        versionTxt.setText(template.getVersion());
        String lastModified =
            Utils.convertDate2String(template.getLastModified());
        lastModifiedTxt.setText(lastModified);
    }

    private void setIcon(DeviceTemplate template) {
        String pluginId = template.getIcon().getPluginId();
        String imageFilePath = template.getIcon().getImageFilePath();
        iconContentLbl.setImage(Utils.createImage(pluginId, imageFilePath));
    }

    public Device getDevice() {
        Device newDevice = new Device();
        newDevice.setIcon(template.getIcon());
        newDevice.setName(nameTxt.getText());
        newDevice.setManufacturer(manufacturerTxt.getText());
        newDevice.setModelNumber(modelNumberTxt.getText());
        newDevice.setDeviceType(typesTxt.getText());
        newDevice.setNotes(notesTxt.getText());
        newDevice.setVersionDevice(versionTxt.getText());
        newDevice.setLastModified(Utils.convertString2Date(lastModifiedTxt
            .getText()));
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
}
