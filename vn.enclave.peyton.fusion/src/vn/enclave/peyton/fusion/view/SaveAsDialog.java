package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SaveAsDialog extends Dialog {

    private static final long serialVersionUID = -8535113237085082924L;

    private Text versionNameText;
    private String versionName = "";

    public SaveAsDialog(Shell parentShell) {
        super(parentShell);
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setText("Version");

        versionNameText = new Text(container, SWT.BORDER);
        versionNameText.setLayoutData(new GridData(
            SWT.FILL, SWT.FILL, true, false, 1, 1));

        // Button button = new Button(container, SWT.PUSH);
        // button.setText("Press me");
        // button.addSelectionListener(new SelectionAdapter() {
        //
        // private static final long serialVersionUID = 1L;
        //
        // @Override
        // public void widgetSelected(SelectionEvent e) {
        // System.out.println("Pressed");
        // }
        // });
        return container;
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
        versionName = versionNameText.getText();
    }
}
