package vn.enclave.peyton.fusion.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.Module;
import vn.enclave.peyton.fusion.provider.TemplateTreeContentProvider;
import vn.enclave.peyton.fusion.provider.TemplateTreeLabelProvider;
import vn.enclave.peyton.fusion.service.impl.ModuleService;

public class AddDeviceDialog extends Dialog {

    private static final long serialVersionUID = 3338235051289446950L;

    private TreeViewer treeViewer;

    private DeviceTemplate template;

    public AddDeviceDialog(Shell parentShell) {
        super(parentShell);
    }

    public DeviceTemplate getTemplate() {
        return template;
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        // Create and layout Composite.
        Composite container = (Composite) super.createDialogArea(parent);
        GridLayout layout = new GridLayout(2, false);
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
        layoutData.heightHint = 400;
        layoutData.widthHint = 400;
        container.setLayout(layout);
        container.setLayoutData(layoutData);

        // Create and layout Section.
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());
        Section section = toolkit.createSection(container, Section.TITLE_BAR);
        section.setText("Device Templates");
        section.setLayout(new FillLayout());
        section.setLayoutData(layoutData);

        // Create PatternFilter;
        PatternFilter filter = new PatternFilter();

        // Create FilteredTree.
        int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
        FilteredTree filteredTree =
            new FilteredTree(section, treeStyle, filter, true);

        // Set filteredTree is client of section.
        section.setClient(filteredTree);

        // Get treeviewer from filteredTree.
        treeViewer = filteredTree.getViewer();

        // Set ContentProvider and LableProvider.
        treeViewer.setContentProvider(new TemplateTreeContentProvider());
        treeViewer.setLabelProvider(new TemplateTreeLabelProvider());

        // Set data to tree.
        ModuleService moduleService = new ModuleService();
        List<Module> modules = moduleService.getAll();
        treeViewer.setInput(modules);

        toolkit.createLabel(container, "Communication Method:");
        CCombo combo = new CCombo(container, SWT.BORDER);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        return container;
    }

    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Select Template");
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, "OK", false);
        createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
    }

    @Override
    protected void okPressed() {
        Object firstElement =
            ((IStructuredSelection) treeViewer.getSelection())
                .getFirstElement();
        if (firstElement instanceof DeviceTemplate) {
            template = (DeviceTemplate) firstElement;
        }
        super.okPressed();
    }
}