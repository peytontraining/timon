package vn.enclave.peyton.fusion.view.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;

public class DetailedDeviceTemplateForm {

    private Label certifiedLbl;

    private Label iconLbl;

    private Label iconContentLbl;

    private Label nameLbl;

    private Label manufacturerLbl;

    private Label modelNumberLbl;

    private Label typesLbl;

    private Label typesContentLbl;

    private Label deviceDriverLbl;

    private Label deviceDriverContentLbl;

    private Label notesLbl;

    private Button certifiedChkBox;

    private Button setBtn;

    private Button removeBtn;

    private Button editTypesBtn;

    private Button selectDriverBtn;

    private Text nameTxt;

    private Text manufacturerTxt;

    private Text modelNumberTxt;

    private Text notesTxt;

    private ScrolledForm scrolledForm;

    public ScrolledForm getDetailedDeviceTemplateForm() {
        return scrolledForm;
    }

    public DetailedDeviceTemplateForm(Composite parent) {
        createForm(parent);
    }

    private void createForm(Composite parent) {
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        scrolledForm = buildForm(parent, toolkit);
    }

    private ScrolledForm buildForm(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        ScrolledForm form = toolkit.createScrolledForm(parent);
        form.getBody().setLayout(layout);

        createFormChildren(form.getBody(), toolkit);

        return form;
    }

    private void createFormChildren(Composite parent, FormToolkit toolkit) {
        certifiedLbl = buildLabel(parent, toolkit, "Certified:");
        certifiedChkBox = buildCheckBox(parent, toolkit);

        iconLbl = buildLabel(parent, toolkit, "Icon");
        buildIconComposite(parent, toolkit);

        nameLbl = buildLabel(parent, toolkit, "Name:");
        nameTxt = buildText(parent, toolkit);

        manufacturerLbl = buildLabel(parent, toolkit, "Manufacturer:");
        manufacturerTxt = buildText(parent, toolkit);

        modelNumberLbl = buildLabel(parent, toolkit, "Model Number:");
        modelNumberTxt = buildText(parent, toolkit);

        typesLbl = buildLabel(parent, toolkit, "Types:");
        buildTypesComposite(parent, toolkit);

        deviceDriverLbl = buildLabel(parent, toolkit, "Device Driver:");
        buildDeviceDriverComposite(parent, toolkit);

        notesLbl = buildLabel(parent, toolkit, "Notes:");
        notesTxt = buildAreaText(parent, toolkit);
    }

    private Button buildCheckBox(Composite parent, FormToolkit toolkit) {
        Button checkBox = new Button(parent, SWT.CHECK);
        return checkBox;
    }

    private
        Label buildLabel(Composite parent, FormToolkit toolkit, String text) {
        Label label = toolkit.createLabel(parent, text, SWT.WRAP);
        return label;
    }

    private Text buildText(Composite parent, FormToolkit toolkit) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        Text text = toolkit.createText(parent, "", SWT.NONE);
        text.setLayoutData(layoutData);
        return text;
    }

    private Composite buildIconComposite(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        Composite composite = toolkit.createComposite(parent, SWT.NONE);
        composite.setLayout(layout);
        composite.setLayoutData(layoutData);

        iconContentLbl = buildLabel(composite, toolkit, "");
        setBtn = buildButton(toolkit, composite, "Set");
        removeBtn = buildButton(toolkit, composite, "Remove");
        return composite;
    }

    private Button buildButton(
        FormToolkit toolkit, Composite parent, String text) {
        GridData layoutData = new GridData(SWT.NONE, SWT.NONE, false, false);
        Button button = toolkit.createButton(parent, text, SWT.PUSH);
        button.setLayoutData(layoutData);
        return button;
    }

    private
        Composite buildTypesComposite(Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        layout.marginTop = -3;
        layout.marginRight = -3;
        layout.marginBottom = -3;
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        Composite composite = toolkit.createComposite(parent, SWT.BORDER);
        composite.setLayout(layout);
        composite.setLayoutData(layoutData);

        typesContentLbl = buildLabel(composite, toolkit, "Type Content");
        editTypesBtn = buildRightButton(toolkit, composite, "Edit Types");

        return composite;
    }

    private Composite buildDeviceDriverComposite(
        Composite parent, FormToolkit toolkit) {
        GridLayout layout = new GridLayout(2, false);
        layout.marginTop = -3;
        layout.marginRight = -3;
        layout.marginBottom = -3;
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        Composite composite = toolkit.createComposite(parent, SWT.BORDER);
        composite.setLayout(layout);
        composite.setLayoutData(layoutData);

        deviceDriverContentLbl = buildLabel(composite, toolkit, "");
        selectDriverBtn = buildRightButton(toolkit, composite, "Select Driver");

        return composite;
    }

    private Button buildRightButton(
        FormToolkit toolkit, Composite composite, String text) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, true);
        Button button = toolkit.createButton(composite, text, SWT.PUSH);
        button.setLayoutData(layoutData);
        return button;
    }

    private Text buildAreaText(Composite parent, FormToolkit toolkit) {
        Text areaText = toolkit.createText(parent, "", SWT.MULTI);
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        layoutData.heightHint = areaText.getLineHeight() * 5;
        areaText.setLayoutData(layoutData);
        return areaText;
    }

    public void populateDeviceTemplateFormFrom(DeviceTemplate template) {
        setCheckBox(template);
        setIcon(template);
        setText(template);
        setLabel(template);

        /*
         * Layout all children of form. Fix an issue: icon doesn't show after
         * the first open of ViewPart.
         */
        scrolledForm.layout(true, true);
    }

    private void setCheckBox(DeviceTemplate template) {
        certifiedChkBox.setSelection(template.isCertified());
    }

    private void setIcon(DeviceTemplate template) {
        iconContentLbl.setImage(Utils.createImage(template.getIcon()));
    }

    private void setText(DeviceTemplate template) {
        nameTxt.setText(template.getName());
        manufacturerTxt.setText(template.getManufacturer());
        modelNumberTxt.setText(template.getModelNumber());
        notesTxt.setText(template.getNotes());
    }

    private void setLabel(DeviceTemplate template) {
        typesContentLbl.setText(template.getDeviceType().getName());
        deviceDriverContentLbl.setText(template.getDeviceDriver());
    }
}
