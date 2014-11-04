package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.control.DevicePropertyTemplateSection;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.view.form.DetailedDeviceTemplateForm;

public class DetailedDeviceTemplateViewPart extends ViewPart {
    public static final String ID = "vn.enclave.peyton.fusion.view.DetailedDeviceTemplateViewPart";
    private DetailedDeviceTemplateForm detailedDeviceTemplateForm;
    private DevicePropertyTemplateSection devicePropertyTemplateSection;

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);

        createToolbarCompositeInside(parent);

        createTabFolderCompositeInside(parent);
    }

    private void createToolbarCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.None);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbarTo(toolbarComposite);
    }

    private void createTabFolderCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -10;
        layout.marginWidth = 0;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite tabFolderComposite = new Composite(parent, SWT.NONE);
        tabFolderComposite.setLayout(layout);
        tabFolderComposite.setLayoutData(layoutData);

        createTabFolderTo(tabFolderComposite);
    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        createAllToolItemsTo(toolBar);
    }

    private void createTabFolderTo(Composite tabFolderComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder tabFolder = new TabFolder(tabFolderComposite, SWT.NONE);
        tabFolder.setLayoutData(layoutData);

        createDetailsTabItemInside(tabFolder);

        createConfigureTabItemInside(tabFolder);
    }

    private void createAllToolItemsTo(ToolBar toolBar) {
        ToolItem saveToolItem = createToolItemTo(toolBar);
        saveToolItem.setImage(Constant.IMAGE_SAVE_AS);

        ToolItem saveAndCloseToolItem = createToolItemTo(toolBar);
        saveAndCloseToolItem.setImage(Constant.IMAGE_SAVE_AND_CLOSE);

        createSeparatorTo(toolBar);

        ToolItem newTemplateToolItem = createToolItemTo(toolBar);
        newTemplateToolItem.setImage(Constant.IMAGE_NEW_TEMPLATE);

        ToolItem templateChildToolItem = createToolItemTo(toolBar);
        templateChildToolItem.setImage(Constant.IMAGE_TEMPLATE_CHILD);
    }

    private void createDetailsTabItemInside(TabFolder tabFolder) {
        TabItem detailsTabItem = new TabItem(tabFolder, SWT.NONE);
        detailsTabItem.setText("Details");

        detailedDeviceTemplateForm = new DetailedDeviceTemplateForm(tabFolder);

        detailsTabItem.setControl(detailedDeviceTemplateForm.getDetailedDeviceTemplateForm());
    }

    private void createConfigureTabItemInside(TabFolder tabFolder) {
        TabItem configureTabItem = new TabItem(tabFolder, SWT.NONE);
        configureTabItem.setText("Configure");

        devicePropertyTemplateSection = new DevicePropertyTemplateSection(tabFolder);

        configureTabItem.setControl(devicePropertyTemplateSection.getPropertyTemplateSection());
    }

    private ToolItem createToolItemTo(ToolBar toolBar) {
        ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
        return toolItem;
    }

    private void createSeparatorTo(ToolBar toolBar) {
        new ToolItem(toolBar, SWT.SEPARATOR);
    }

    public void populateViewPartFrom(DeviceTemplate deviceTemplate) {
        changeNameAndImageViewPartFrom(deviceTemplate);
        detailedDeviceTemplateForm.populateDeviceTemplateFormFrom(deviceTemplate);
        devicePropertyTemplateSection.populatePropertyTreeViewerFrom(deviceTemplate);
    }

    private void changeNameAndImageViewPartFrom(DeviceTemplate template) {
        setPartName(template.getName());
        setTitleImage(Utils.createImageFromIcon(template.getIcon()));
    }

    @Override
    public void setFocus() {}

}
