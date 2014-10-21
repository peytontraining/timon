package vn.enclave.peyton.fusion.view.form;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

/**
 * This class is used in NavigationViewPart class to create a form, which shows
 * all information of version properties.
 */
public class VersionForm {

    private static final String VERSION_PATTERN = "[0-9]++\\.[0-9]++\\.[0-9]++";

    private static final String VERSION_FORMAT_ERROR =
        "The format is not true.";

    private static final String VERSION_DULICATE_ERROR =
        "The version has been already existed.";

    private IWorkbenchWindow window;

    private IWorkbenchPart part;

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

    public VersionForm(Section section) {
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
        versionText.addModifyListener(createModifyListener());
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
    private Text createText(
        FormToolkit toolkit, Composite parent, Color color, String value) {
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
        decoration.setImage(PlatformUI
            .getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
        return decoration;
    }

    /*
     * If selected node is the new node which isn't been save into database, two
     * fields deploySource and targetVersion is setEnable(true). Otherwise,
     * setEnable(false).
     */
    public void setDisplayedData(Version version) {
        if (version.getId() == Constant.DEFAULT_VERSION_ID) {
            int endIndex = version.getName().lastIndexOf(" *");
            versionText.setText(version.getName().substring(0, endIndex));
            deploySourceText.setEditable(true);
            targetVersionText.setEditable(true);
            deploySourceText.setBackground(white);
            targetVersionText.setBackground(white);
        } else {
            versionText.setText(version.getName());
            deploySourceText.setEditable(false);
            targetVersionText.setEditable(false);
            deploySourceText.setBackground(gray);
            targetVersionText.setBackground(gray);
        }
        projectText.setText(version.getProject().getName());
        if (version.getDeployTime() != null) {
            deployTimeText.setText(Utils.convertDate2String(version
                .getDeployTime()));
        } else {
            deployTimeText.setText("");
        }
        deploySourceText.setText(version.getDeploySource());
        if (version.getSaveTime() != null) {
            saveTimeText
                .setText(Utils.convertDate2String(version.getSaveTime()));
        } else {
            saveTimeText.setText("");
        }
        targetVersionText.setText(version.getTargetVersion());

        // Set editable for version Textbox.
        boolean editable = version.isEditable();
        versionText.setEditable(editable);
    }

    /*
     * Create a ModifyListener for versionText.
     */
    private ModifyListener createModifyListener() {
        return new ModifyListener() {

            private static final long serialVersionUID = -2592628530216331949L;

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
                if (firstObject instanceof Version) {
                    // Change enable state of save tool item on version section.
                    changeSaveVersionState((Version) firstObject);

                    // Validate modified text on version text box.
                    validateModifiedText((Version) firstObject);

                    // Add a star on ViewPart title.
                    if (!((NavigationViewPart) part)
                        .getViewer().getTree().isFocusControl()) {
                        ((NavigationViewPart) part).setPartName("* "
                            .concat("Project"));
                    }
                }
            }
        };
    }

    private void changeSaveVersionState(Version version) {

        // Set enable to save ToolItem.
        boolean focus =
            ((NavigationViewPart) part).getViewer().getTree().isFocusControl();
        boolean editable = version.isEditable();
        /*
         * If version.getId() == 0, that means the selected version just created
         * but isn't been saved, isSaved = false. Otherwise, isSaved = true.
         */
        boolean isSaved = version.getId() != Constant.DEFAULT_VERSION_ID;

        // If isSave = false, setEnable(true)
        if (!isSaved) {
            ((NavigationViewPart) part).getSaveVersion().setEnabled(!isSaved);
        } else {
            ((NavigationViewPart) part).getSaveVersion().setEnabled(
                !focus && editable);
        }
    }

    private void validateModifiedText(Version version) {
        String name = versionText.getText().intern();
        boolean format = name.matches(VERSION_PATTERN);
        if (!format) {
            versionDecoration.setDescriptionText(VERSION_FORMAT_ERROR);
            versionDecoration.show();
            ((NavigationViewPart) part).getSaveVersion().setEnabled(false);
            return;
        }

        int id = version.getId();
        List<Version> versions = version.getProject().getVersions();
        for (Version v : versions) {
            if (v.getName().intern() == name && v.getId() != id) {
                versionDecoration.setDescriptionText(VERSION_DULICATE_ERROR);
                versionDecoration.show();
                ((NavigationViewPart) part).getSaveVersion().setEnabled(false);
                return;
            } else {
                versionDecoration.hide();
            }
        }
    }
}
