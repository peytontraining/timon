package vn.enclave.peyton.fusion.view;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.control.PlanTree;
import vn.enclave.peyton.fusion.control.ProjectPropertySection;
import vn.enclave.peyton.fusion.control.VersionPropertySection;
import vn.enclave.peyton.fusion.dialog.DeleteVersionDialog;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.PlanService;
import vn.enclave.peyton.fusion.service.impl.VersionService;

public class NavigationViewPart extends ViewPart implements ISaveablePart {
    public static final String ID = "vn.enclave.peyton.fusion.view.navigationViewPart";
    private static final int PLAN_TREE_COMPOSITE = 65;
    private static final int PROPERTY_COMPOSITE = 100 - PLAN_TREE_COMPOSITE;
    private StackLayout propertyCompositeStackLayout;
    private PlanService planService = new PlanService();
    private VersionService versionService = new VersionService();
    private PlanTree planTree;
    private ProjectPropertySection projectPropertySection;
    private VersionPropertySection versionPropertySection;
    private boolean isDirty;
    private boolean isSectionReady;

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

        createToolbarInside(toolbarComposite);
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

    private void createToolbarInside(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        ToolBarManager toolBarManager = new ToolBarManager(toolBar);
        IMenuService menuService = (IMenuService) getSite().getService(IMenuService.class);
        menuService.populateContributionManager(toolBarManager, Constant.TOOLBAR_NAVIGATION_VIEW_PART);
        toolBarManager.update(true);
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

    private void createPlanTreeTo(Composite planTreeComposite) {
        List<Plan> plans = planService.getAll();

        planTree = new PlanTree(planTreeComposite, getSite());
        planTree.registerPlanTreeContextMenuToWorkbenchPartSite();
        planTree.setSelectionProviderToWorkbenchPartSite();
        planTree.populatePlanTreeViewerFrom(plans);
        planTree.addSelectionChangedListener(createSelectionChangedListenerToPlanTree());
        planTree.addKeyAdapter(createKeyAdapterToPlanTree());
    }

    private ISelectionChangedListener createSelectionChangedListenerToPlanTree() {
        return new ISelectionChangedListener() {

            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                Object selectedNode = planTree.getSelectedNodeOnPlanTreeViewer();
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

    private KeyAdapter createKeyAdapterToPlanTree() {
        return new KeyAdapter() {
            private static final long serialVersionUID = -9190506839482994554L;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.DEL) {
                    deleteSelectedVersion();
                }
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
        projectPropertySection = new ProjectPropertySection(propertyComposite, getSite());
        projectPropertySection.addModifyListenerToPropertySection(createModifyListenerToProjectPropertySection());
        projectPropertySection.addSelectionAdapterToPropertySection(createSelectionAdapterToProjectPropertySection());
    }

    private void createVersionPropertySectionInside(Composite propertyComposite) {
        versionPropertySection = new VersionPropertySection(propertyComposite, getSite());
        versionPropertySection.addModifyListenerToPropertySection(createModifyListenerToVersionPropertySection());
        versionPropertySection.addSelectionAdapterToPropertySection(createSelectionAdapterToVersionPropertySection());
    }

    private void deleteSelectedVersion() {
        Object selectedNode = planTree.getSelectedNodeOnPlanTreeViewer();
        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;
            DeleteVersionDialog dialog = new DeleteVersionDialog(getSite().getShell());

            if (dialog.open() == Window.OK) {
                versionService.remove(selectedVersion);

                // Remove data of selected version from tree's data.
                selectedVersion.getProject().removeVersion(selectedVersion);
                selectedVersion.setProject(null);

                // Refresh the tree after deleting.
                planTree.refreshPlanTreeViewer();

                clearRowsOnDeviceTable();
            }
        }
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

    private void clearRowsOnDeviceTable() {
        IWorkbenchPage workbenchPage = getViewSite().getPage();
        IViewPart viewPart = workbenchPage.findView(DeviceTableViewPart.ID);
        ((DeviceTableViewPart) viewPart).clearRowsOnDeviceTable();
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