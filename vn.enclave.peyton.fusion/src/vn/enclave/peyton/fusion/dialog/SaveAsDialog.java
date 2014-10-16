package vn.enclave.peyton.fusion.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import vn.enclave.peyton.fusion.entity.Version;

public class SaveAsDialog extends Dialog {

    private static final long serialVersionUID = -8535113237085082924L;
    private static final String VERSION_PATTERN = "[0-9]++\\.[0-9]++\\.[0-9]++";
    private static final String VERSION_FORMAT_ERROR =
        "The format is not true.";
    private static final String VERSION_DULICATE_ERROR =
        "The version has been already existed.";

    private Text nameText;
    private String name = "";
    private Version selectedVersion;

    public SaveAsDialog(Shell parentShell, Version selectedVersion) {
        super(parentShell);
        this.selectedVersion = selectedVersion;
    }

    public String getName() {
        return name;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // Create a suggesting name for new Version.
        String suggestingName = getNewVersionName(selectedVersion);

        // Create and layout Composite.
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        // Create Label.
        Label label = new Label(container, SWT.NONE);
        label.setText("Version");

        // Create and layout Text.
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
        nameText = new Text(container, SWT.BORDER);
        nameText.setLayoutData(layoutData);
        nameText.setText(suggestingName);

        // Create ControlDecoration and make it be hidden.
        final ControlDecoration decoration = createDecoration(nameText);
        decoration.hide();

        // Add ModifyListener to versionNameText to validate the inputted
        // version name.
        nameText.addModifyListener(createModifyListener(decoration));
        return container;
    }

    private ModifyListener createModifyListener(
        final ControlDecoration decoration) {
        return new ModifyListener() {

            private static final long serialVersionUID = 855402140550697735L;

            @Override
            public void modifyText(ModifyEvent event) {
                String name = nameText.getText().intern();

                // Check format.
                boolean format = name.matches(VERSION_PATTERN);
                if (!format) {
                    decoration.setDescriptionText(VERSION_FORMAT_ERROR);
                    decoration.show();
                    return;
                }

                int id = selectedVersion.getId();
                List<Version> versions =
                    selectedVersion.getProject().getVersions();
                // Check dulicate.
                for (Version version : versions) {
                    if (version.getName().intern() == name
                        && version.getId() != id) {
                        decoration.setDescriptionText(VERSION_DULICATE_ERROR);
                        decoration.show();
                        return;
                    } else {
                        decoration.hide();
                    }
                }
            }
        };
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Clone Version");
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "OK", true);
        createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
    }

    @Override
    protected void okPressed() {
        name = nameText.getText();
        super.okPressed();
    }

    /*
     * Create the new name for the new Version with format 1.0.x
     */
    private String getNewVersionName(Version version) {
        // Get the latest version.
        Version latestVersion = version.getProject().getVersions().get(0);

        // Get the last number in version name. For example: 1.0.13, then
        // tailName = 13
        String tailName = latestVersion.getName().split("\\.")[2];

        // Convert tailName to int.
        int number = Integer.valueOf(tailName);

        // Increase number by one, then convert to String.
        tailName = String.valueOf(++number);

        return "1.0.".concat(tailName);
    }

    private ControlDecoration createDecoration(Control control) {
        ControlDecoration decoration =
            new ControlDecoration(control, SWT.TOP | SWT.LEFT);
        decoration.setImage(PlatformUI
            .getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
        return decoration;
    }
}
