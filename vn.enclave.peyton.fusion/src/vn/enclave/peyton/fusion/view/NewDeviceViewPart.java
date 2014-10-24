package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

public class NewDeviceViewPart extends ViewPart {

    public static final String ID =
        "vn.enclave.peyton.fusion.newDeviceViewPart";

    private Label icon;

    private Text nameText;

    private Text manufacturerText;

    private Text modelNumberText;

    private Text typesText;

    private Text notesText;

    private Text versionText;

    private Text lastModifiedText;

    @Override
    public void createPartControl(Composite parent) {
        // Create the TabFolder.
        TabFolder folder = new TabFolder(parent, SWT.NONE);

        // Create the Detail TabItem.
        createDetailsTab(parent, folder);

        // Create the Configure TabItem.
        createConfigureTab(parent, folder);
    }

    @Override
    public void setFocus() {

    }

    private void createDetailsTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.NONE);
        item.setText("Details");

        // Create the FormToolKit.
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        // Create the ScrolledForm and set layout for it.
        ScrolledForm form = toolkit.createScrolledForm(folder);
        form.getBody().setLayout(new GridLayout(2, false));

        // Create content for the form.
        createDetailsFormContent(toolkit, form);

        // Set the form is the TabItem's control.
        item.setControl(form);
    }

    /*
     * Create all buttons, labels and texts for form.
     */
    private
        void createDetailsFormContent(FormToolkit toolkit, ScrolledForm form) {
        GridData layoutData;

        layoutData = new GridData(SWT.LEFT, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Icon:");
        icon = toolkit.createLabel(form.getBody(), "");
        icon.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Name:");
        nameText = toolkit.createText(form.getBody(), "");
        nameText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Manufacturer:");
        manufacturerText = toolkit.createText(form.getBody(), "");
        manufacturerText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Model Number:");
        modelNumberText = toolkit.createText(form.getBody(), "");
        modelNumberText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Types:");
        typesText = toolkit.createText(form.getBody(), "");
        typesText.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Notes:");
        notesText = toolkit.createText(form.getBody(), "", SWT.MULTI);
        layoutData.heightHint = notesText.getLineHeight() * 5;
        notesText.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Version:");
        versionText = toolkit.createText(form.getBody(), "");
        versionText.setLayoutData(layoutData);

        toolkit.createLabel(form.getBody(), "Last Modfied:");
        lastModifiedText = toolkit.createText(form.getBody(), "");
        lastModifiedText.setLayoutData(layoutData);
    }

    /*
     * Create the Configure TabItem inside TabFolder.
     */
    private void createConfigureTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.BORDER);
        item.setText("Configure");
    }
}
