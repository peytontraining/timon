package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
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
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.view.form.DeviceTemplateForm;

public class DeviceTemplateDetailViewPart extends ViewPart {

    public static final String ID =
        "vn.enclave.peyton.fusion.view.DeviceTemplateDetailViewPart";

    private DeviceTemplateForm templateForm;

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);

        createToolbarComposite(parent);

        createTabFolderComposite(parent);
    }

    private void createToolbarComposite(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.None);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbar(toolbarComposite);
    }

    private void createToolbar(Composite parent) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar bar = new ToolBar(parent, SWT.FLAT);
        bar.setLayoutData(layoutData);

        createToolItems(bar);
    }

    private void createToolItems(ToolBar parent) {
        ToolItem save = createToolItem(parent, Constant.IMAGE_SAVE);

        ToolItem saveAndClose =
            createToolItem(parent, Constant.IMAGE_SAVE_CLOSE);

        new ToolItem(parent, SWT.SEPARATOR);

        ToolItem newTemplate =
            createToolItem(parent, Constant.IMAGE_NEW_TEMPLATE);

        ToolItem templateChild =
            createToolItem(parent, Constant.IMAGE_TEMPLATE_CHILD);
    }

    private ToolItem createToolItem(ToolBar parent, Image image) {
        ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
        toolItem.setImage(image);
        return toolItem;
    }

    private void createTabFolderComposite(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -10;
        layout.marginWidth = 0;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite tabFolderComposite = new Composite(parent, SWT.NONE);
        tabFolderComposite.setLayout(layout);
        tabFolderComposite.setLayoutData(layoutData);

        createTabFolder(tabFolderComposite);
    }

    private void createTabFolder(Composite parent) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        TabFolder folder = new TabFolder(parent, SWT.NONE);
        folder.setLayoutData(layoutData);

        createDetailsTabItem(folder);

        createConfigureTabItem(folder);
    }

    private void createDetailsTabItem(TabFolder parent) {
        TabItem detailsTabItem = new TabItem(parent, SWT.NONE);
        detailsTabItem.setText("Details");

        templateForm = new DeviceTemplateForm(parent);
        

        detailsTabItem.setControl(templateForm.getScrolledForm());
    }

    private void createConfigureTabItem(TabFolder parent) {
        TabItem configureTabItem = new TabItem(parent, SWT.NONE);
        configureTabItem.setText("Configure");
    }

    public void setData(DeviceTemplate template) {
        changeTitleAndImage(template);
        templateForm.fillInForm(template);
    }

    private void changeTitleAndImage(DeviceTemplate template) {
        setTitleImage(Utils.createImage(template.getIcon()));
        setPartName(template.getName());
    }

    @Override
    public void setFocus() {
    }

}
