package vn.enclave.peyton.fusion.view;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.control.PlanTree;
import vn.enclave.peyton.fusion.control.ProjectPropertySection;
import vn.enclave.peyton.fusion.control.VersionPropertySection;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.service.impl.PlanService;
import vn.enclave.peyton.fusion.service.impl.ProjectService;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.form.ProjectForm;
import vn.enclave.peyton.fusion.view.form.VersionForm;

public class NavigationViewPart extends ViewPart {

    public static final String ID = "vn.enclave.peyton.fusion.view.navigationViewPart";

    private static final int PLAN_TREE_COMPOSITE = 65;

    private static final int PROPERTY_COMPOSITE = 100 - PLAN_TREE_COMPOSITE;

    private TreeViewer viewer;

    private VersionForm versionForm;

    private ProjectForm projectForm;

    private Section versionSection;

    private Section projectSection;

    private ToolBar versionBar;

    private ToolBar projectBar;

    private ToolItem saveVersion;

    private ToolItem saveProject;

    private StackLayout propertyCompositeStackLayout;

    private PlanService planService = new PlanService();

    private ProjectService projectService = new ProjectService();

    private VersionService versionService = new VersionService();

    public ToolItem getSaveVersion() {
        return saveVersion;
    }

    public ToolItem getSaveProject() {
        return saveProject;
    }

    public TreeViewer getViewer() {
        return viewer;
    }

    public void setPartName(String partName) {
        super.setPartName(partName);
    }

    private ProjectPropertySection projectPropertySection;
    private VersionPropertySection versionPropertySection;

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginRight = -5;
        layout.marginLeft = -5;
        parent.setLayout(layout);

        createToolbarCompositeInside(parent);
        createPlanTreeAndPropertyCompositeSashFormInside(parent);
    }

    private void createToolbarCompositeInside(Composite parent) {
        // TODO createToolbarCompositeInside(Composite parent)
    }

    private void createPlanTreeAndPropertyCompositeSashFormInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        SashForm planTreeAndPropertyCompositeSashForm = new SashForm(parent, SWT.VERTICAL);
        planTreeAndPropertyCompositeSashForm.setLayout(layout);
        planTreeAndPropertyCompositeSashForm.setLayoutData(layoutData);
        planTreeAndPropertyCompositeSashForm.setSashWidth(0);

        createPlanTreeCompositeInside(planTreeAndPropertyCompositeSashForm);
        createPropertyCompositeInside(planTreeAndPropertyCompositeSashForm);

        planTreeAndPropertyCompositeSashForm.setWeights(new int[]{PLAN_TREE_COMPOSITE, PROPERTY_COMPOSITE});
    }

    private void createPlanTreeCompositeInside(SashForm planTreeAndPropertySectionSashForm) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite planTreeComposite = new Composite(planTreeAndPropertySectionSashForm, SWT.BORDER);
        planTreeComposite.setLayout(layout);
        planTreeComposite.setLayoutData(layoutData);

        PlanTree planTree = new PlanTree(planTreeComposite, getSite());

        PlanService planService = new PlanService();
        List<Plan> plans = planService.getAll();
        planTree.populatePlanTreeViewerFrom(plans);
        planTree.registerPlanTreeContextMenuToWorkbenchPartSite();
        planTree.setSelectionProviderToWorkbenchPartSite();
    }

    private void createPropertyCompositeInside(SashForm planTreeAndPropertyCompositeSashForm) {
        createPropertyCompositeStackLayout();

        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite propertyComposite = new Composite(planTreeAndPropertyCompositeSashForm, SWT.BORDER);
        propertyComposite.setLayout(propertyCompositeStackLayout);
        propertyComposite.setLayoutData(layoutData);

        createProjectPropertySectionInside(propertyComposite);
        createVersionPropertySectionInside(propertyComposite);
         displayProjectPropertySection();
        // displayVersionPropertySection();
    }

    private void createPropertyCompositeStackLayout() {
        propertyCompositeStackLayout = new StackLayout();
    }

    private void createProjectPropertySectionInside(Composite propertyComposite) {
        // TODO createProjectPropertySectionInside(Composite propertyComposite)
        projectPropertySection = new ProjectPropertySection(propertyComposite);
    }

    private void createVersionPropertySectionInside(Composite propertyComposite) {
        // TODO createVersionPropertySectionInside(Composite propertyComposite)
        versionPropertySection = new VersionPropertySection(propertyComposite);
    }

    private void displayProjectPropertySection() {
        propertyCompositeStackLayout.topControl = projectPropertySection.getProjectPropertySection();
        projectPropertySection.setVisible(true);
        // versionPropertySection.setVisible(false);
    }

    private void displayVersionPropertySection() {
        propertyCompositeStackLayout.topControl = versionPropertySection.getVersionPropertySection();
        projectPropertySection.setVisible(false);
        versionPropertySection.setVisible(true);
    }

    private void hideAllPropertySection() {
        propertyCompositeStackLayout.topControl = null;
        projectPropertySection.setVisible(false);
        versionPropertySection.setVisible(false);
    }

    @Override
    public void setFocus() {
    }
}