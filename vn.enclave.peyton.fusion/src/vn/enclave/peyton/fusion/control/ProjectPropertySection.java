package vn.enclave.peyton.fusion.control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

//TODO: ProjectPropertySection
public class ProjectPropertySection {
    private Label nameLbl;
    private Label gatewayLbl;
    private Label hostLbl;
    private Label portLbl;
    private Label uuidLbl;
    private Label licenseLbl;
    private Text nameTxt;
    private Text hostTxt;
    private Text uuidTxt;
    private Text notesTxt;
    private Button uuidRadioBtn;
    private Button hostPortRadioBtn;
    private Button deploymentLockedChkBox;
    private Button editNotesBtn;
    private Spinner portSpinner;
    private Combo licenseCombo;
    private Section projectPropertySection;
    private ScrolledForm projectPropertyScrolledForm;
    private FormToolkit toolkit;

    public Section getProjectPropertySection() {
        return projectPropertySection;
    }

    public ProjectPropertySection(Composite parent) {
        createFormToolkitFrom(parent);
        createProjectPropertySectionInside(parent);
    }

    private void createFormToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createProjectPropertySectionInside(Composite parent) {
        projectPropertySection = toolkit.createSection(parent, Section.TITLE_BAR);
        projectPropertySection.setText("Project Properties");

        createProjectPropertyScrolledFromInside(projectPropertySection);
    }

    private void createProjectPropertyScrolledFromInside(Section projectPropertySection) {
        GridLayout layout = new GridLayout(2, false);
        projectPropertyScrolledForm = toolkit.createScrolledForm(projectPropertySection);

        Composite projectPropertyFormBody = projectPropertyScrolledForm.getBody();
        projectPropertyFormBody.setLayout(layout);

        createControlsTo(projectPropertyFormBody);

        projectPropertySection.setClient(projectPropertyScrolledForm);
    }

    private void createControlsTo(Composite projectPropertyFormBody) {
        nameLbl = createLabelTo(projectPropertyFormBody);
        nameLbl.setText("Name:");

        nameTxt = createTextTo(projectPropertyFormBody);

        gatewayLbl = createLabelTo(projectPropertyFormBody);
        gatewayLbl.setText("Gateway uses:");

        createRadioButtonCompositeInside(projectPropertyFormBody);

        hostLbl = createLabelTo(projectPropertyFormBody);
        hostLbl.setText("Gateway Host:");

        hostTxt = createTextTo(projectPropertyFormBody);

        portLbl = createLabelTo(projectPropertyFormBody);
        portLbl.setText("Gateway Port:");

        portSpinner = createSpinnerTo(projectPropertyFormBody);

        uuidLbl = createLabelTo(projectPropertyFormBody);
        uuidLbl.setText("Gateway UUIDL:");

        uuidTxt = createTextTo(projectPropertyFormBody);

        licenseLbl = createLabelTo(projectPropertyFormBody);
        licenseLbl.setText("License:");

        licenseCombo = createComboTo(projectPropertyFormBody);
    }

    private Label createLabelTo(Composite projectPropertyFormBody) {
        return toolkit.createLabel(projectPropertyFormBody, "");
    }

    private Text createTextTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Text text = toolkit.createText(projectPropertyFormBody, "");
        text.setLayoutData(layoutData);
        return text;
    }

    private void createRadioButtonCompositeInside(Composite projectPropertyFormBody) {
        GridLayout layout = new GridLayout(2, false);
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite radioButtonComposite = new Composite(projectPropertyFormBody, SWT.BORDER);
        radioButtonComposite.setLayout(layout);
        radioButtonComposite.setLayoutData(layoutData);

        createUUIDRadioButtonTo(radioButtonComposite);
        createHostPortRadioButtonTo(radioButtonComposite);
    }

    private Spinner createSpinnerTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Spinner spinner = new Spinner(projectPropertyFormBody, SWT.BORDER);
        spinner.setLayoutData(layoutData);
        spinner.setMaximum(99999);
        spinner.setTextLimit(5);
        return spinner;
    }

    private Combo createComboTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Combo combo = new Combo(projectPropertyFormBody, SWT.NONE);
        combo.setLayoutData(layoutData);
        return combo;
    }

    private void createUUIDRadioButtonTo(Composite radioButtonComposite) {
        uuidRadioBtn = createRadioButtonTo(radioButtonComposite);
        uuidRadioBtn.setText("UUID");
    }

    private void createHostPortRadioButtonTo(Composite radioButtonComposite) {
        hostPortRadioBtn = createRadioButtonTo(radioButtonComposite);
        hostPortRadioBtn.setText("Host/Port");
    }

    private Button createRadioButtonTo(Composite radioButtonComposite) {
        GridData layoutData = new GridData(SWT.LEFT, SWT.NONE, false, false);
        Button radioBtn = toolkit.createButton(radioButtonComposite, "", SWT.RADIO);
        radioBtn.setLayoutData(layoutData);
        return radioBtn;
    }

    public void setVisible(boolean isVisible) {
        projectPropertySection.setVisible(isVisible);
    }
}
