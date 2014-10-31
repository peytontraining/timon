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
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Icon;

/**
 * This class is used to show and collect all data of device in Details tab.
 * 
 * @author enclaveit
 * 
 */
public class DetailedDeviceForm {

    private Label iconLbl;

    private Label iconContentLbl;

    private Label nameLbl;

    private Label manufacturerLbl;

    private Label modelNumberLbl;

    private Label typesLbl;

    private Label notesLbl;

    private Label versionLbl;

    private Hyperlink lastModifiedLink;

    private Text nameTxt;

    private Text manufacturerTxt;

    private Text modelNumberTxt;

    private Text typesTxt;

    private Text notesTxt;

    private Text versionTxt;

    private Text lastModifiedTxt;

    private ScrolledForm scrolledForm;

    private Device device;

    public ScrolledForm getScrolledForm() {
        return scrolledForm;
    }

    public DetailedDeviceForm(Composite parent) {
        createForm(parent);
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
        manufacturerTxt.setEnabled(false);

        modelNumberLbl = buildLabel(formBody, toolkit, "Model Number:");
        modelNumberTxt = buildText(formBody, toolkit);
        modelNumberTxt.setEnabled(false);

        typesLbl = buildLabel(formBody, toolkit, "Types:");
        typesTxt = buildText(formBody, toolkit);
        typesTxt.setEnabled(false);

        notesLbl = buildLabel(formBody, toolkit, "Notes:");
        notesTxt = buildTextArea(formBody, toolkit);

        versionLbl = buildLabel(formBody, toolkit, "Version:");
        versionTxt = buildText(formBody, toolkit);
        versionTxt.setEnabled(false);

        lastModifiedLink = buildHyperlink(formBody, toolkit, "Last Modified");
        lastModifiedTxt = buildText(formBody, toolkit);
        lastModifiedTxt.setEnabled(false);
    }

    private ScrolledForm buildForm(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        ScrolledForm form = toolkit.createScrolledForm(parent);
        form.getBody().setLayout(layout);
        return form;
    }

    private
        Label buildLabel(Composite parent, FormToolkit toolkit, String text) {
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

    private Hyperlink buildHyperlink(
        Composite parent, FormToolkit toolkit, String text) {
        Hyperlink hyperlink = toolkit.createHyperlink(parent, text, SWT.NONE);
        return hyperlink;
    }

    public void fillInForm(Device device) {
        this.device = device;

        setIcon(device.getIcon());
        setText(device);
        /*
         * Layout all children of form. Fix an issue: icon doesn't show after
         * the first open of ViewPart.
         */
        scrolledForm.layout(true, true);
    }

    private void setIcon(Icon icon) {
        iconContentLbl.setImage(Utils.createImage(icon));
    }

    private void setText(Device device) {
        String lastModified =
            Utils.convertDate2String(device.getLastModified());

        nameTxt.setText(device.getName());
        manufacturerTxt.setText(device.getManufacturer());
        modelNumberTxt.setText(device.getModelNumber());
        typesTxt.setText(device.getDeviceType());
        notesTxt.setText(device.getNotes());
        versionTxt.setText(device.getVersionDevice());
        lastModifiedTxt.setText(lastModified);
    }

    public void addModifyListener(ModifyListener modifyListener) {
        nameTxt.addModifyListener(modifyListener);
        notesTxt.addModifyListener(modifyListener);
    }

    public Device getModifiedDevice() {
        device.setName(nameTxt.getText());
        device.setNotes(notesTxt.getText());
        device.setLastModified(new Date());
        return device;
    }
}
