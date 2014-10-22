package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.entity.DeviceTemplate;

public class DeviceTemplateDetailViewPart extends ViewPart {

    public static final String ID =
        "vn.enclave.peyton.fusion.view.DeviceTemplateDetailViewPart";

    private Button certifiedCheck;

    private Button set;

    private Button remove;

    private Text nameText;

    private Text manufacturerText;

    private Text modelNumberText;

    private Text typesText;

    private Text deviceDriverText;

    private Text notesText;

    @Override
    public void createPartControl(Composite parent) {
        // Create the TabFolder.
        TabFolder folder = new TabFolder(parent, SWT.NONE);

        // Create the Detail TabItem.
        createDetailsTab(parent, folder);

        // Create the Configure TabItem.
        createConfigureTab(parent, folder);

        // Change the name of ViewPart.
        setData();
    }

    /*
     * Create the Detail TabItem inside TabFolder.
     */
    private void createDetailsTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.NONE);
        item.setText("Details");

        // Create the FormToolKit.
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        // Create the ScrolledForm and set layout for it.
        ScrolledForm form = toolkit.createScrolledForm(folder);
        form.getBody().setLayout(new GridLayout(3, false));

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

        layoutData = new GridData(SWT.LEFT, SWT.NONE, true, false, 2, 1);
        toolkit.createLabel(form.getBody(), "Certified:");
        certifiedCheck = toolkit.createButton(form.getBody(), "", SWT.CHECK);
        certifiedCheck.setLayoutData(layoutData);

        layoutData = new GridData(SWT.LEFT, SWT.NONE, false, false, 1, 1);
        toolkit.createLabel(form.getBody(), "Icon:");
        set = toolkit.createButton(form.getBody(), "Set...", SWT.PUSH);
        set.setLayoutData(layoutData);
        remove = toolkit.createButton(form.getBody(), "Remove", SWT.PUSH);
        remove.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1);
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

        toolkit.createLabel(form.getBody(), "Device Driver:");
        deviceDriverText = toolkit.createText(form.getBody(), "");
        deviceDriverText.setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1);
        toolkit.createLabel(form.getBody(), "Notes:");
        notesText = toolkit.createText(form.getBody(), "", SWT.MULTI);
        layoutData.heightHint = notesText.getLineHeight() * 5;
        notesText.setLayoutData(layoutData);
    }

    /*
     * Create the Configure TabItem inside TabFolder.
     */
    private void createConfigureTab(Composite parent, TabFolder folder) {
        // Create TabItem and set its title.
        TabItem item = new TabItem(folder, SWT.BORDER);
        item.setText("Configure");
    }

    /*
     * Change the name of the ViewPart is same as the name of the selected
     * device template row. Set data to Details form.
     */
    private void setData() {
        ISelection selection =
            getSite()
                .getWorkbenchWindow().getSelectionService()
                .getSelection(DeviceTemplateViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object firstElement = sselection.getFirstElement();
        if (firstElement instanceof DeviceTemplate) {
            DeviceTemplate template = (DeviceTemplate) firstElement;
            boolean certified = template.getCertified();
            String name = template.getName();
            String manufacturer = template.getManufacture();
            String modelNumber = template.getModelNumber();
            String types = template.getDeviceType().getName();
            String deviceDriver = template.getDeviceDriver();
            String notes = template.getNotes();

            // Change the viewpart name.
            setPartName(((DeviceTemplate) firstElement).getName());

            // Set data to form.
            certifiedCheck.setSelection(certified);
            nameText.setText(name);
            manufacturerText.setText(manufacturer);
            modelNumberText.setText(modelNumber);
            typesText.setText(types);
            deviceDriverText.setText(deviceDriver);
            notesText.setText(notes);
        }
    }

    @Override
    public void setFocus() {
    }

}
