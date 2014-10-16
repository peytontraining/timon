package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class ProjectForm {

    private Text versionText;

    private Text projectText;

    private Text deployTimeText;

    private Text deploySourceText;

    private Text saveTimeText;

    private Text targetVersionText;

    private ControlDecoration versionDecoration;

    private ScrolledForm form;

    private Color gray;

    private Color white;

    public Text getVersionText() {
        return versionText;
    }

    public void setVersionText(Text versionText) {
        this.versionText = versionText;
    }

    public Text getProjectText() {
        return projectText;
    }

    public void setProjectText(Text projectText) {
        this.projectText = projectText;
    }

    public Text getDeployTimeText() {
        return deployTimeText;
    }

    public void setDeployTimeText(Text deployTimeText) {
        this.deployTimeText = deployTimeText;
    }

    public Text getDeploySourceText() {
        return deploySourceText;
    }

    public void setDeploySourceText(Text deploySourceText) {
        this.deploySourceText = deploySourceText;
    }

    public Text getSaveTimeText() {
        return saveTimeText;
    }

    public void setSaveTimeText(Text saveTimeText) {
        this.saveTimeText = saveTimeText;
    }

    public Text getTargetVersionText() {
        return targetVersionText;
    }

    public void setTargetVersionText(Text targetVersionText) {
        this.targetVersionText = targetVersionText;
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
        GridLayout layout = new GridLayout(2, false);
        form.getBody().setLayout(layout);

        // Create a Label and a Text for Version.
        Composite body = form.getBody();
        toolkit.createLabel(body, "Version");
        versionText = createText(toolkit, body, white, "");

        // Add ControlDecoration to versionText.
        versionDecoration = createDecoration(versionText);

        // Make versionDecoration is hidden.
        versionDecoration.hide();

        // Create a Label and a Text for Project.
        toolkit.createLabel(body, "Project");
        projectText = createText(toolkit, body, gray, "");

        // Create a Label and a Text for Deploy Time.
        toolkit.createLabel(body, "Deploy Time");
        deployTimeText = createText(toolkit, body, gray, "");

        // Create a Label and a Text for Deploy Source.
        toolkit.createLabel(body, "Deploy Source");
        deploySourceText = createText(toolkit, body, gray, "");

        // Create a Label and a Text for Save Time.
        toolkit.createLabel(body, "Save Time");
        saveTimeText = createText(toolkit, body, gray, "");

        // Create a Label and a Text for Target Version.
        toolkit.createLabel(body, "Target Version");
        targetVersionText = createText(toolkit, body, gray, "");

        // Set form is client of section.
        section.setClient(form);
    }

    /*
     * Create a Text.
     */
    private Text createText(FormToolkit toolkit, Composite parent, Color color,
            String value) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
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
        decoration.setImage(PlatformUI.getWorkbench().getSharedImages()
                .getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
        return decoration;
    }
}
