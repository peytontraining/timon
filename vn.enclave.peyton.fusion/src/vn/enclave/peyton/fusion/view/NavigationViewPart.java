package vn.enclave.peyton.fusion.view;

import java.util.Date;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.PlanFilter;
import vn.enclave.peyton.fusion.provider.PlanTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PlanTreeLableProvider;
import vn.enclave.peyton.fusion.service.impl.PlanService;
import vn.enclave.peyton.fusion.service.impl.ProjectService;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.form.ProjectForm;
import vn.enclave.peyton.fusion.view.form.VersionForm;

public class NavigationViewPart extends ViewPart {

    public static final String ID =
        "vn.enclave.peyton.fusion.view.navigationViewPart";

    private static final int TOP_COMPOSITE = 65;

    private static final int BOTTOM_COMPOSITE = 100 - TOP_COMPOSITE;

    private TreeViewer viewer;

    private VersionForm versionForm;

    private ProjectForm projectForm;

    private Section versionSection;

    private Section projectSection;

    private ToolBar versionBar;

    private ToolBar projectBar;

    private ToolItem saveVersion;

    private ToolItem saveProject;

    private StackLayout stackLayout = new StackLayout();

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

    @Override
    public void createPartControl(Composite parent) {
        // Set layout for the parent.
        FillLayout layout = new FillLayout(SWT.VERTICAL);
        parent.setLayout(layout);

        // Create a SashForm.
        SashForm sashForm = createSashForm(parent);

        // Create topComposite.
        Composite topComposite = createTopComposite(sashForm);

        // Create TreeViewer inside topComposite.
        viewer = createTreeViewer(topComposite);

        // Set menu for the Viewer.
        MenuManager menuManager = new MenuManager();
        menuManager.setRemoveAllWhenShown(true);
        Menu menu = menuManager.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite().registerContextMenu(menuManager, viewer);

        // Set the content for the Viewer,
        // setInput will call getElements in the ContentProvider.
        viewer.setInput(planService.getAll());

        // Create bottomComposite.
        Composite bottomComposite = createBottomComposite(sashForm);

        // Create two Section inside bottomComposite.
        versionSection = createSection(bottomComposite, "Version Properties");
        projectSection = createSection(bottomComposite, "Project Properties");

        // Create a tool bar for each section.
        versionBar = createToolbar(versionSection);
        projectBar = createToolbar(projectSection);

        // Create a tool item for each tool bar.
        saveVersion = createToolItem(versionBar);
        saveProject = createToolItem(projectBar);

        // Add a selection listener to each save tool item on each section.
        saveVersion.addSelectionListener(createSaveVersionListener());
        saveProject.addSelectionListener(createSaveProjectListener());

        // Create a ScrolledFrom inside each section.
        versionForm = new VersionForm(versionSection);
        projectForm = new ProjectForm(projectSection);

        // Set weight for each composite.
        sashForm.setWeights(new int[]{TOP_COMPOSITE, BOTTOM_COMPOSITE});

        // Set viewer is a one of the selection providers.
        getSite().setSelectionProvider(viewer);

        // Create selection listener for bottom composite.
        createSelectionListener();
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    /*
     * Create a SashForm.
     */
    private SashForm createSashForm(Composite parent) {
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayout(new FillLayout());
        sashForm.SASH_WIDTH = 0;
        return sashForm;
    }

    /*
     * Create a Composite to store TreeViewer.
     */
    private Composite createTopComposite(Composite parent) {
        // Create and layout the composite.
        GridLayout layout = new GridLayout(1, false);
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(layout);
        return composite;
    }

    /*
     * Create a TreeViewer.
     */
    private TreeViewer createTreeViewer(Composite parent) {
        TreeViewer treeViewer = null;
        // Create the PatternFilter.
        PatternFilter filter = new PatternFilter();

        // Create and layout the FilteredTree.
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        int style = SWT.H_SCROLL | SWT.V_SCROLL;
        FilteredTree filteredTree = new PlanFilter(parent, style, filter, true);
        filteredTree.setLayoutData(layoutData);

        // Create a TreeViewer from the FilteredTree.
        treeViewer = filteredTree.getViewer();

        // Set the ContentProvider.
        treeViewer.setContentProvider(new PlanTreeContentProvider());

        // Set the LableProvider.
        treeViewer.setLabelProvider(new PlanTreeLableProvider());

        return treeViewer;
    }

    /*
     * Create a Composite to store Section.
     */
    private Composite createBottomComposite(Composite parent) {
        // Create and layout a composite.
        int style = SWT.NONE | SWT.BORDER;
        Composite composite = new Composite(parent, style);
        composite.setLayout(stackLayout);
        return composite;
    }

    /*
     * Create a Section to store Form.
     */
    private Section createSection(Composite parent, String title) {
        Display display = parent.getDisplay();
        // Create a FormToolKit.
        FormToolkit toolkit = new FormToolkit(display);

        // Create and layout a Section.
        Section section = toolkit.createSection(parent, Section.TITLE_BAR);

        // Set text and fore color for title.
        section.setText(title);
        section.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
        return section;
    }

    /*
     * Create a Tool bar with a save Tool item.
     */
    private ToolBar createToolbar(Section section) {
        // Create and layout a Toolbar.
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolBar = new ToolBar(section, style);

        // Set TextClient to section.
        section.setTextClient(toolBar);
        return toolBar;
    }

    private ToolItem createToolItem(ToolBar parent) {
        // Create a ToolItem.
        ToolItem item = new ToolItem(parent, SWT.PUSH);
        item.setImage(Constant.IMAGE_SAVE);
        item.setEnabled(false);
        return item;
    }

    /*
     * Create a SelectionAdapter for save tool item on version section.
     */
    private SelectionAdapter createSaveVersionListener() {
        return new SelectionAdapter() {

            private static final long serialVersionUID = -5483634617709186172L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection =
                    (IStructuredSelection) viewer.getSelection();
                Object firstElement = selection.getFirstElement();
                if (firstElement instanceof Version) {
                    String name = versionForm.getVersionText().getText();
                    if (((Version) firstElement).getId() == Constant.DEFAULT_VERSION_ID) {
                        String targetVersion =
                            versionForm.getTargetVersionText().getText();
                        String deploySource =
                            versionForm.getDeploySourceText().getText();
                        ((Version) firstElement).setDeploySource(deploySource);
                        ((Version) firstElement).setName(name);
                        ((Version) firstElement).setSaveTime(new Date());
                        ((Version) firstElement)
                            .setTargetVersion(targetVersion);

                        // Get the current project.
                        Project currentProject =
                            ((Version) firstElement).getProject();

                        /*
                         * After add() method run, the return variable
                         * newVersion has a new Project pointer.
                         */
                        firstElement =
                            versionService.add((Version) firstElement);

                        // Set current Project pointer for the new Version Node.
                        ((Version) firstElement).setProject(currentProject);

                        // Remove the temp object in treeview and add the new
                        // object that got from add query.
                        ((Version) firstElement)
                            .getProject().getVersions()
                            .set(0, (Version) firstElement);
                    } else {
                        ((Version) firstElement).setName(name);

                        // Update name to database.
                        versionService.update((Version) firstElement);
                    }

                    // Make save ToolItem is enable.
                    saveVersion.setEnabled(false);

                    // Refresh the Viewer.
                    viewer.refresh();

                    // Remove the star on ViewPart title.
                    setPartName("Project");
                }
            }
        };
    }

    /*
     * Create a SelectionAdapter for save tool item on version section.
     */
    private SelectionListener createSaveProjectListener() {
        return new SelectionAdapter() {

            private static final long serialVersionUID = -5483634617709186172L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection =
                    (IStructuredSelection) viewer.getSelection();
                Object firstElement = selection.getFirstElement();
                if (firstElement instanceof Project) {
                    String name = projectForm.getNameText().getText();
                    if (((Project) firstElement).getId() == Constant.DEFAULT_PROJECT_ID) {
                        boolean gateway =
                            projectForm.getUuidRadio().getSelection();
                        String host = projectForm.getHostText().getText();
                        String license =
                            projectForm.getLicenseCombo().getText();
                        int port = projectForm.getPortSpinner().getSelection();
                        String uuid = projectForm.getUuidText().getText();
                        ((Project) firstElement).setName(name);
                        ((Project) firstElement).setGateway(gateway);
                        ((Project) firstElement).setHost(host);
                        ((Project) firstElement).setLicense(license);
                        ((Project) firstElement).setPort(port);
                        ((Project) firstElement).setUuid(uuid);
                        ((Project) firstElement)
                            .getVersions().get(0).setName("1.0.0");
                        ((Project) firstElement)
                            .getVersions().get(0).setSaveTime(new Date());
                        // Get the current plan.
                        Plan currentPlan = ((Project) firstElement).getPlan();

                        /*
                         * After add() method run, the return variable new
                         * Project has a new Plan pointer.
                         */
                        firstElement =
                            projectService.add((Project) firstElement);

                        // Set current Plan pointer for the new Project Node.
                        ((Project) firstElement).setPlan(currentPlan);

                        // Remove the temp object in treeview and add the new
                        // object that got from add query.
                        ((Project) firstElement)
                            .getPlan().getProjects()
                            .set(0, (Project) firstElement);
                    } else {
                        ((Project) firstElement).setName(name);

                        // Update name to database.
                        projectService.update((Project) firstElement);
                    }

                    // Make save ToolItem is enable.
                    saveProject.setEnabled(false);

                    // Refresh the Viewer.
                    viewer.refresh();

                    // Remove the star on ViewPart title.
                    setPartName("Project");
                }
            }
        };
    }

    /*
     * Create selection listener for bottom composite.
     */
    private void createSelectionListener() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        ISelectionService service = window.getSelectionService();
        service.addSelectionListener(ID, new ISelectionListener() {

            @Override
            public void selectionChanged(
                IWorkbenchPart part, ISelection selection) {
                IStructuredSelection sselection =
                    (IStructuredSelection) selection;
                Object firstObject = sselection.getFirstElement();
                if (firstObject instanceof Version) {
                    versionForm.setDisplayedData((Version) firstObject);
                    versionSection.setVisible(true);
                    projectSection.setVisible(false);
                    stackLayout.topControl = versionSection;
                } else if (firstObject instanceof Project) {
                    projectForm.setDisplayedData((Project) firstObject);
                    versionSection.setVisible(false);
                    projectSection.setVisible(true);
                    stackLayout.topControl = projectSection;
                } else {
                    versionSection.setVisible(false);
                    projectSection.setVisible(false);
                    stackLayout.topControl = null;
                }
            }
        });
    }
}