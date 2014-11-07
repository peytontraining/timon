package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;

public class AreasTemplatesViewPart extends ViewPart {
    public static final String ID = "vn.enclave.peyton.fusion.view.areasTemplatesViewPart";
    private FormToolkit toolkit;

    @Override
    public void createPartControl(Composite parent) {
        creatToolkitFrom(parent);

        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        parent.setLayout(layout);

        createToolbarCompositeInside(parent);
        createFormCompositeInside(parent);
    }

    private void creatToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createToolbarCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbarInside(toolbarComposite);
    }

    private void createFormCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(2, false);
        layout.marginTop = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite formComposite = new Composite(parent, SWT.NONE);
        formComposite.setLayout(layout);
        formComposite.setLayoutData(layoutData);

        createControlsTo(formComposite);
    }

    private void createToolbarInside(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        ToolBarManager toolBarManager = new ToolBarManager(toolBar);
        IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
        menuService.populateContributionManager(toolBarManager, Constant.TOOLBAR_AREAS_TEMPLATES_VIEW_PART);
        toolBarManager.update(true);
    }

    private void createControlsTo(Composite formComposite) {
        toolkit.createLabel(formComposite, "Template:");
        Combo combo = new Combo(formComposite, SWT.DROP_DOWN);
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        combo.setLayoutData(layoutData);

        toolkit.createLabel(formComposite, "Name:");
        toolkit.createText(formComposite, "").setLayoutData(layoutData);

        toolkit.createLabel(formComposite, "Notes:");
        toolkit.createText(formComposite, "", SWT.MULTI).setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1);
        toolkit.createText(formComposite, "").setLayoutData(layoutData);
    }

    @Override
    public void setFocus() {

    }

}
