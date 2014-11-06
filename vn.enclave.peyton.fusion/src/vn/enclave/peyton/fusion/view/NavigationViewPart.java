package vn.enclave.peyton.fusion.view;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.control.PlanTree;
import vn.enclave.peyton.fusion.control.ProjectPropertySection;
import vn.enclave.peyton.fusion.control.VersionPropertySection;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.PlanService;

public class NavigationViewPart extends ViewPart implements ISaveablePart {
    public static final String ID = "vn.enclave.peyton.fusion.view.navigationViewPart";
    private static final int PLAN_TREE_COMPOSITE = 65;
    private static final int PROPERTY_COMPOSITE = 100 - PLAN_TREE_COMPOSITE;

    private TreeViewer viewer;

    private StackLayout propertyCompositeStackLayout;
    private PlanService planService = new PlanService();
    private PlanTree planTree;

    public TreeViewer getViewer() {
        return viewer;
    }

    public void refreshPlanTreeViewer() {
        planTree.refreshPlanTreeViewer();
    }

    public void setSelectionToPlanTreeViewerBy(Project newProject) {
        planTree.setSelectionToPlanTreeViewerBy(newProject);
    }

    public void setSelectionToPlanTreeViewerBy(Version newVersion) {
        planTree.setSelectionToPlanTreeViewerBy(newVersion);
    }

    public void setPartName(String partName) {
        super.setPartName(partName);
    }

    private ProjectPropertySection projectPropertySection;
    private VersionPropertySection versionPropertySection;
    private boolean isDirty;
    private boolean isSectionReady;

    public void setDirty(boolean isDirty) {
        this.isDirty = isDirty;
        firePropertyChange(PROP_DIRTY);
    }

    public void setSectionReady(boolean isSectionReady) {
        this.isSectionReady = isSectionReady;
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

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
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbar(toolbarComposite);
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

    private void createToolbar(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        createToolItems(toolBar);
    }

    private void createPlanTreeCompositeInside(SashForm planTreeAndPropertySectionSashForm) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginBottom = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite planTreeComposite = new Composite(planTreeAndPropertySectionSashForm, SWT.NONE);
        planTreeComposite.setLayout(layout);
        planTreeComposite.setLayoutData(layoutData);

        createPlanTreeTo(planTreeComposite);
    }

    private void createPropertyCompositeInside(SashForm planTreeAndPropertyCompositeSashForm) {
        createPropertyCompositeStackLayout();

        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite propertyComposite = new Composite(planTreeAndPropertyCompositeSashForm, SWT.BORDER);
        propertyComposite.setLayout(propertyCompositeStackLayout);
        propertyComposite.setLayoutData(layoutData);

        createProjectPropertySectionInside(propertyComposite);
        createVersionPropertySectionInside(propertyComposite);
    }

    private void createToolItems(ToolBar toolBar) {
        ToolItem refreshToolItem = createToolItemTo(toolBar);
        refreshToolItem.setImage(Constant.IMAGE_REFRESH);

        createSeparatorTo(toolBar);

        ToolItem showDeviceToolItem = createToolItemTo(toolBar);
        showDeviceToolItem.setImage(Constant.IMAGE_SHOW_DEVICES);

        createSeparatorTo(toolBar);

        ToolItem addFolderToolItem = createToolItemTo(toolBar);
        addFolderToolItem.setImage(Constant.IMAGE_ADD_FOLDER);

        createSeparatorTo(toolBar);

        ToolItem cloneVersionToolItem = createToolItemTo(toolBar);
        cloneVersionToolItem.setImage(Constant.IMAGE_CLONE_VERSION);

        ToolItem saveAsToolItem = createToolItemTo(toolBar);
        saveAsToolItem.setImage(Constant.IMAGE_SAVE_AS);

        ToolItem deleteToolItem = createToolItemTo(toolBar);
        deleteToolItem.setImage(Constant.IMAGE_DELETE);
    }

    private void createPlanTreeTo(Composite planTreeComposite) {
        List<Plan> plans = planService.getAll();

        planTree = new PlanTree(planTreeComposite, getSite());
        planTree.registerPlanTreeContextMenuToWorkbenchPartSite();
        planTree.setSelectionProviderToWorkbenchPartSite();
        planTree.populatePlanTreeViewerFrom(plans);
        planTree.addSelectionChangedListener(createSelectionChangedListenerToPlanTree());
    }

    private ToolItem createToolItemTo(ToolBar toolBar) {
        ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
        return toolItem;
    }

    private void createSeparatorTo(ToolBar toolBar) {
        new ToolItem(toolBar, SWT.SEPARATOR);
    }

    private ISelectionChangedListener createSelectionChangedListenerToPlanTree() {
        return new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                Object selectedNode = planTree.getSelectedNodeOnNavigationTreeViewer();
                resetStateOfViewPart();
                if (selectedNode instanceof Project) {
                    Project selectedProject = (Project) selectedNode;
                    populateProjectPropertySectionFrom(selectedProject);
                    displayProjectPropertySection();
                    setSectionReady(true);
                    projectPropertySection.setEnableSaveToolItem(selectedProject.isNewProject());
                    return;
                }
                if (selectedNode instanceof Version) {
                    Version selectedVersion = (Version) selectedNode;
                    populateVersionPropertySectionFrom(selectedVersion);
                    displayVersionPropertySection();
                    setSectionReady(true);
                    versionPropertySection.setEnableSaveToolItem(selectedVersion.isNewVersion());
                    return;
                }
                hideAllPropertySection();
            }
        };
    }

    private void resetStateOfViewPart() {
        setSectionReady(false);
        setDirty(false);
    }

    private void populateProjectPropertySectionFrom(Project selectedProject) {
        projectPropertySection.populatePropertyScrolledFormFrom(selectedProject);
    }

    private void populateVersionPropertySectionFrom(Version selectedVersion) {
        versionPropertySection.populatePropertyScrolledFormFrom(selectedVersion);
    }

    private void createPropertyCompositeStackLayout() {
        propertyCompositeStackLayout = new StackLayout();
    }

    private void createProjectPropertySectionInside(Composite propertyComposite) {
        // TODO createProjectPropertySectionInside(Composite propertyComposite)
        projectPropertySection = new ProjectPropertySection(propertyComposite, getSite());
        projectPropertySection.addModifyListenerToPropertySection(createModifyListenerToProjectPropertySection());
        projectPropertySection.addSelectionAdapterToPropertySection(createSelectionAdapterToProjectPropertySection());
    }

    private void createVersionPropertySectionInside(Composite propertyComposite) {
        // TODO createVersionPropertySectionInside(Composite propertyComposite)
        versionPropertySection = new VersionPropertySection(propertyComposite, getSite());
        versionPropertySection.addModifyListenerToPropertySection(createModifyListenerToVersionPropertySection());
        versionPropertySection.addSelectionAdapterToPropertySection(createSelectionAdapterToVersionPropertySection());
    }

    private ModifyListener createModifyListenerToProjectPropertySection() {
        return new ModifyListener() {
            private static final long serialVersionUID = -7831361091809878509L;

            @Override
            public void modifyText(ModifyEvent event) {
                setDirty(isSectionReady);
                projectPropertySection.setEnableSaveToolItem(isSectionReady);
            }
        };
    }

    /*
     * Trigger saving new project as the save tool item is clicked.
     */
    private SelectionAdapter createSelectionAdapterToProjectPropertySection() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 5010629306840681616L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                Project selectedProject = projectPropertySection.saveModifiedProject();
                projectPropertySection.setEnableSaveToolItem(false);
                planTree.refreshPlanTreeViewer();
                planTree.setSelectionToPlanTreeViewerBy(selectedProject);
                setDirty(false);
            }
        };
    }

    private ModifyListener createModifyListenerToVersionPropertySection() {
        return new ModifyListener() {
            private static final long serialVersionUID = -7831361091809878509L;

            @Override
            public void modifyText(ModifyEvent event) {
                setDirty(isSectionReady);
                versionPropertySection.setEnableSaveToolItem(isSectionReady);
            }
        };
    }

    private SelectionAdapter createSelectionAdapterToVersionPropertySection() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 5010629306840681616L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                Version selectedVersion = versionPropertySection.saveModifiedVersion();
                versionPropertySection.setEnableSaveToolItem(false);
                planTree.refreshPlanTreeViewer();
                planTree.setSelectionToPlanTreeViewerBy(selectedVersion);
                setDirty(false);
            }
        };
    }

    private void displayProjectPropertySection() {
        propertyCompositeStackLayout.topControl = projectPropertySection.getProjectPropertySection();
        projectPropertySection.setVisible(true);
        versionPropertySection.setVisible(false);
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

    @Override
    public void doSave(IProgressMonitor monitor) {
    }

    @Override
    public void doSaveAs() {
    }

    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
    public boolean isSaveOnCloseNeeded() {
        return false;
    }
}