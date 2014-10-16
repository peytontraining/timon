package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.PlanFilter;
import vn.enclave.peyton.fusion.provider.PlanTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PlanTreeLableProvider;
import vn.enclave.peyton.fusion.service.impl.PlanService;

public class NavigationViewPart2 extends ViewPart {

    public static final String ID = "vn.enclave.peyton.fusion.view.navigationViewPart2";

    private static final int TOP_COMPOSITE = 65;

    private static final int BOTTOM_COMPOSITE = 100 - TOP_COMPOSITE;

    private TreeViewer viewer;

    private VersionForm versionForm;
    
    private ProjectForm projectForm;

    private Section versionSection;
    
    private Section projectSection;

    private PlanService planService = new PlanService();

    private StackLayout stackLayout = new StackLayout();

    public TreeViewer getViewer() {
        return viewer;
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

        // Set the menu to the viewer.
        viewer.getTree().setMenu(menu);

        // Register context menu.
        getSite().registerContextMenu(menuManager, viewer);

        // Set the content for the Viewer,
        // setInput will call getElements in the ContentProvider.
        viewer.setInput(planService.getAll());

        // Create bottomComposite.
        Composite bottomComposite = createBottomComposite(sashForm);

        // Create Section inside bottomComposite.
        versionSection = createSection(bottomComposite, "Version Properties");

        // Create a ScrolledFrom inside Section.
        versionForm = new VersionForm(versionSection);

        projectSection = createSection(bottomComposite, "Project Properties");
        
        projectForm = new ProjectForm(projectSection);

        // Set viewer is a one the
        getSite().setSelectionProvider(viewer);
        sashForm.setWeights(new int[]{TOP_COMPOSITE, BOTTOM_COMPOSITE});
        
        createSelectionListener();
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    /*
     * Create SashForm.
     */
    private SashForm createSashForm(Composite parent) {
        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayout(new FillLayout());
        sashForm.SASH_WIDTH = 0;
        return sashForm;
    }

    /*
     * Create Composite to store TreeViewer.
     */
    private Composite createTopComposite(Composite parent) {
        // Create and layout the composite.
        GridLayout layout = new GridLayout(1, false);
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(layout);
        return composite;
    }

    /*
     * Create TreeViewer.
     */
    private TreeViewer createTreeViewer(Composite parent) {
        TreeViewer treeViewer = null;
        // Create the PatternFilter.
        PatternFilter filter = new PatternFilter();

        // Create and layout the FilteredTree.
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
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
     * Create Composite to store Section.
     */
    private Composite createBottomComposite(Composite parent) {
        // Create and layout a composite.
        int style = SWT.NONE | SWT.BORDER;
        Composite composite = new Composite(parent, style);
        composite.setLayout(stackLayout);
        return composite;
    }

    /*
     * Create Section to store Form.
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

    private void createSelectionListener() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        ISelectionService service = window.getSelectionService();
        service.addSelectionListener(ID, new ISelectionListener() {
            
            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
                IStructuredSelection sselection = (IStructuredSelection) selection;
                Object firstObject = sselection.getFirstElement();
                if (firstObject instanceof Version) {
                    versionSection.setVisible(true);
                    projectSection.setVisible(false);
                    stackLayout.topControl = versionSection;
                } else if (firstObject instanceof Project) {
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