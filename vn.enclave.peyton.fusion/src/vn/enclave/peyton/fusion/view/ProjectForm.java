package vn.enclave.peyton.fusion.view;

import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Project;

public class ProjectForm {

    private static final String PROJECT_DULICATE_ERROR =
        "The project has been already existed.";

    private static final String PROJECT_EMPTY_ERROR =
        "Name of project cannot be empty";

    private IWorkbenchWindow window;

    private IWorkbenchPart part;

    private Text nameText;

    private ControlDecoration nameDecoration;

    private Button uuidRadio;

    private Button hostPortRadio;

    private Text hostText;

    private Spinner portSpinner;

    private Text uuidText;

    private Combo licenseCombo;

    private Text noteText;

    private Button lockedCheck;

    private Button editNotesButton;

    private ScrolledForm form;

    private Color gray;

    private Color white;

    public Text getNameText() {
        return nameText;
    }

    public void setNameText(Text nameText) {
        this.nameText = nameText;
    }

    public ControlDecoration getNameDecoration() {
        return nameDecoration;
    }

    public void setNameDecoration(ControlDecoration nameDecoration) {
        this.nameDecoration = nameDecoration;
    }

    public Button getUuidRadio() {
        return uuidRadio;
    }

    public void setUuidRadio(Button uuidRadio) {
        this.uuidRadio = uuidRadio;
    }

    public Button getHostPortRadio() {
        return hostPortRadio;
    }

    public void setHostPortRadio(Button hostPortRadio) {
        this.hostPortRadio = hostPortRadio;
    }

    public Text getHostText() {
        return hostText;
    }

    public void setHostText(Text hostText) {
        this.hostText = hostText;
    }

    public Spinner getPortSpinner() {
        return portSpinner;
    }

    public void setPortSpinner(Spinner portSpinner) {
        this.portSpinner = portSpinner;
    }

    public Text getUuidText() {
        return uuidText;
    }

    public void setUuidText(Text uuidText) {
        this.uuidText = uuidText;
    }

    public Combo getLicenseCombo() {
        return licenseCombo;
    }

    public void setLicenseCombo(Combo licenseCombo) {
        this.licenseCombo = licenseCombo;
    }

    public Text getNoteText() {
        return noteText;
    }

    public void setNoteText(Text noteText) {
        this.noteText = noteText;
    }

    public Button getLockedCheck() {
        return lockedCheck;
    }

    public void setLockedCheck(Button lockedCheck) {
        this.lockedCheck = lockedCheck;
    }

    public Button getEditNotesButton() {
        return editNotesButton;
    }

    public void setEditNotesButton(Button editNotesButton) {
        this.editNotesButton = editNotesButton;
    }

    public ScrolledForm getForm() {
        return form;
    }

    public void setForm(ScrolledForm form) {
        this.form = form;
    }

    public ProjectForm(Section section) {
        gray = section.getDisplay().getSystemColor(SWT.COLOR_GRAY);
        white = section.getDisplay().getSystemColor(SWT.COLOR_WHITE);

        // Create a FormToolkit.
        FormToolkit toolkit = new FormToolkit(section.getDisplay());

        // Create a ScrolledForm.
        form = toolkit.createScrolledForm(section);

        // Layout the body of form.
        GridLayout layout = new GridLayout(3, false);
        form.getBody().setLayout(layout);

        // Create a Label and a Text for Name.
        Composite body = form.getBody();
        toolkit.createLabel(body, "Name:");
        nameText = createText(toolkit, body, white, "");
        nameText.addModifyListener(createModifyListener());

        // Add a ControlDecoration to name.
        nameDecoration = createDecoration(nameText);

        // Make decoration be hidden.
        nameDecoration.hide();

        // Create a Label and two radio button for Gateway uses.
        // The uuid radio button is selected as default.
        toolkit.createLabel(body, "Gateway uses:");
        uuidRadio = createRadio(toolkit, body, "UUID");
        uuidRadio.setSelection(true);
        hostPortRadio = createRadio(toolkit, body, "Host/Port");

        // Create a Label and a Text for Gateway host.
        toolkit.createLabel(body, "Gateway Host:");
        hostText = createText(toolkit, body, white, "");

        // Create a Label and a Spinner for Gateway port.
        toolkit.createLabel(body, "Gateway Port:");
        portSpinner = createSpinner(body);

        // Create a Label and a Text for Gateway UUID.
        toolkit.createLabel(body, "Gateway UUID:");
        uuidText = createText(toolkit, body, white, "");

        // Create a Label and a Combo for License.
        toolkit.createLabel(body, "License:");
        licenseCombo = createCombo(body);

        // Create a Button for Deployment locked.
        lockedCheck = createCheck(toolkit, body, "Deployment locked");

        // Create a Text and a button for Note.
        noteText = createAreaText(toolkit, body, gray, "");

        // Create a Button for Edit note.
        editNotesButton = createButton(toolkit, body, "Edit Notes...");

        // Set form is client of section.
        section.setClient(form);
    }

    private Button createButton(
        FormToolkit toolkit, Composite parent, String label) {
        GridData layoutData =
            new GridData(SWT.RIGHT, SWT.FILL, true, false, 3, 1);
        Button button = toolkit.createButton(parent, label, SWT.PUSH);
        button.setLayoutData(layoutData);
        button.setEnabled(false);
        return button;
    }

    private Text createAreaText(
        FormToolkit toolkit, Composite parent, Color color, String value) {
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 3, 2);
        int style = SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL;
        Text text = toolkit.createText(parent, value, style);
        layoutData.heightHint = 5 * text.getLineHeight();
        text.setLayoutData(layoutData);
        text.setBackground(color);
        return text;
    }

    private Button createCheck(
        FormToolkit toolkit, Composite parent, String label) {
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
        Button check = toolkit.createButton(parent, label, SWT.CHECK);
        check.setLayoutData(layoutData);
        check.setEnabled(false);
        return check;
    }

    private Combo createCombo(Composite parent) {
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        Combo combo = new Combo(parent, SWT.NONE);
        combo.setLayoutData(layoutData);
        combo.setEnabled(false);
        return combo;
    }

    private Spinner createSpinner(Composite parent) {
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        Spinner spinner = new Spinner(parent, SWT.BORDER);
        spinner.setLayoutData(layoutData);
        spinner.setMaximum(99999);
        spinner.setTextLimit(5);
        spinner.setEnabled(false);
        return spinner;
    }

    private Button createRadio(
        FormToolkit toolkit, Composite parent, String label) {
        Button radio = toolkit.createButton(parent, label, SWT.RADIO);
        radio.setEnabled(false);
        return radio;
    }

    /*
     * Create a Text.
     */
    private Text createText(
        FormToolkit toolkit, Composite parent, Color color, String value) {
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        Text text = toolkit.createText(parent, value, SWT.READ_ONLY);
        text.setLayoutData(layoutData);
        text.setBackground(color);
        return text;
    }

    /*
     * Create a ControlDecoration..
     */
    private ControlDecoration createDecoration(Control control) {
        int position = SWT.TOP | SWT.LEFT;
        ControlDecoration decoration = new ControlDecoration(control, position);
        decoration.setImage(PlatformUI
            .getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
        return decoration;
    }

    public void setDisplayedData(Project project) {
        if (project.getId() == Constant.DEFAULT_PROJECT_ID) {
            changeState(true);
            int endIndex = project.getName().lastIndexOf(" *");
            nameText.setText(project.getName().substring(0, endIndex));
        } else {
            changeState(false);
            nameText.setText(project.getName());
        }
        if (project.getGateway()) {
            uuidRadio.setSelection(true);
            hostPortRadio.setSelection(false);
        } else {
            uuidRadio.setSelection(false);
            hostPortRadio.setSelection(true);
        }

        hostText.setText(project.getHost());
        portSpinner.setSelection(project.getPort());
        uuidText.setText(project.getUuid());
        licenseCombo.setText(project.getLicense());
        lockedCheck.setSelection(project.isDeploymentLocked());
        noteText.setText(project.getNote());

        // Set editable for version Textbox.
        boolean editable = project.isEditable();
        nameText.setEditable(editable);
    }

    private void changeState(boolean enable) {
        nameText.setEditable(enable);
        uuidRadio.setEnabled(enable);
        hostPortRadio.setEnabled(enable);
        hostText.setEditable(enable);
        portSpinner.setEnabled(enable);
        uuidText.setEditable(enable);
        licenseCombo.setEnabled(enable);
    }

    /*
     * Create a ModifyListener for nameText.
     */
    private ModifyListener createModifyListener() {
        return new ModifyListener() {

            private static final long serialVersionUID = 6220756688941181551L;

            @Override
            public void modifyText(ModifyEvent event) {
                window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                part = window.getPartService().getActivePart();
                ISelection selection =
                    window.getSelectionService().getSelection(
                        NavigationViewPart.ID);
                IStructuredSelection sselection =
                    (IStructuredSelection) selection;
                Object firstObject = sselection.getFirstElement();
                if (firstObject instanceof Project) {
                    // Change enable state of save tool item on version section.
                    changeSaveProjectState((Project) firstObject);

                    // Validate modified text on version text box.
                    validateModifiedText((Project) firstObject);
                }
            }
        };
    }

    private void changeSaveProjectState(Project project) {
        // Set enable to save ToolItem.
        boolean focus =
            ((NavigationViewPart) part).getViewer().getTree().isFocusControl();
        boolean editable = project.isEditable();

        /*
         * If project.getId() == -1, that means the selected project just
         * created but isn't been saved, isSaved = false. Otherwise, isSaved =
         * true.
         */
        boolean isSaved = project.getId() != Constant.DEFAULT_PROJECT_ID;

        if (!isSaved) {
            ((NavigationViewPart) part).getSaveProject().setEnabled(!isSaved);
        } else {
            ((NavigationViewPart) part).getSaveProject().setEnabled(
                !focus && editable);
        }

    }

    private void validateModifiedText(Project project) {
        String name = nameText.getText().intern();
        if (name.length() <= 0) {
            nameDecoration.setDescriptionText(PROJECT_EMPTY_ERROR);
            nameDecoration.show();
            ((NavigationViewPart) part).getSaveProject().setEnabled(false);
            return;
        }

        int id = project.getId();
        List<Project> projects = project.getPlan().getProjects();
        for (Project p : projects) {
            if (p.getName().intern() == name && p.getId() != id) {
                nameDecoration.setDescriptionText(PROJECT_DULICATE_ERROR);
                nameDecoration.show();
                ((NavigationViewPart) part).getSaveProject().setEnabled(false);
                return;
            } else {
                nameDecoration.hide();
            }
        }
    }

}
