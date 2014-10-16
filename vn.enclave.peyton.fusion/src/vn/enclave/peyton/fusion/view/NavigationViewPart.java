package vn.enclave.peyton.fusion.view;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.PlanFilter;
import vn.enclave.peyton.fusion.provider.PlanTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PlanTreeLableProvider;
import vn.enclave.peyton.fusion.service.impl.PlanService;
import vn.enclave.peyton.fusion.service.impl.VersionService;

public class NavigationViewPart extends ViewPart {

    public static final String ID =
        "vn.enclave.peyton.fusion.view.navigationViewPart";
    private static final String VERSION_PATTERN = "[0-9]++\\.[0-9]++\\.[0-9]++";
    private static final String VERSION_FORMAT_ERROR =
        "The format is not true.";
    private static final String VERSION_DULICATE_ERROR =
        "The version has been already existed.";
    private static final int TOP_COMPOSITE = 65;
    private static final int BOTTOM_COMPOSITE = 100 - TOP_COMPOSITE;

    private TreeViewer viewer;
    private Color gray, white;
    private ToolItem saveItem;
    private Text versionText;
    private Text projectText;
    private Text deployTimeText;
    private Text deploySourceText;
    private Text saveTimeText;
    private Text targetVersionText;
    private ControlDecoration versionDecoration;
    private PlanService planService = new PlanService();
    private VersionService versionService = new VersionService();

    public TreeViewer getViewer() {
        return viewer;
    }

    @Override
    public void createPartControl(Composite parent) {
        // Set value for colors.
        gray = parent.getDisplay().getSystemColor(SWT.COLOR_GRAY);
        white = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);

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
        Section versionSection = createVersionSection(bottomComposite);

        // Create a ScrooledForm inside section.
        createForm(versionSection);

        // Make the section is invisible.
        versionSection.setVisible(false);

        Section projectSection = createProjectSection(bottomComposite);
        projectSection.setVisible(false);

        // Create a SelectionListener for Form.
        createSelectionService4BottomComposite(versionSection, projectSection);

        // Make the selection available to other Views.
        getSite().setSelectionProvider(viewer);
        sashForm.setWeights(new int[] { TOP_COMPOSITE, BOTTOM_COMPOSITE });
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
     * Create Composite to store Section.
     */
    private Composite createBottomComposite(Composite parent) {
        // Create and layout a composite.
        int style = SWT.NONE | SWT.BORDER;
        Composite composite = new Composite(parent, style);
        composite.setLayout(new FillLayout());
        return composite;
    }

    /*
     * Create Section to store Form.
     */
    private Section createVersionSection(Composite parent) {
        Display display = parent.getDisplay();
        // Create a FormToolKit.
        FormToolkit toolkit = new FormToolkit(display);

        // Create and layout a Section.
        Section section = toolkit.createSection(parent, Section.TITLE_BAR);
        section.setLayout(new FillLayout());

        // Set text and fore color for title.
        section.setText("Version Properties");
        section.setForeground(display.getSystemColor(SWT.COLOR_BLACK));

        // CreateToolbar for the Section.
        createVersionToolbar(section);

        return section;
    }

    private Section createProjectSection(Composite parent) {
        Display display = parent.getDisplay();
        // Create a FormToolKit.
        FormToolkit toolkit = new FormToolkit(display);

        // Create and layout a Section.
        Section section = toolkit.createSection(parent, Section.TITLE_BAR);
        section.setLayout(new FillLayout());

        // Set text and fore color for title.
        section.setText("Project Properties");
        section.setForeground(display.getSystemColor(SWT.COLOR_BLACK));

        // CreateToolbar for the Section.
        createProjectToolbar(section);

        return section;
    }

    /*
     * Create Toolbar on Section.
     */
    private void createVersionToolbar(Section section) {
        // Create and layout a Toolbar.
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolBar = new ToolBar(section, style);

        // Create a ToolItem.
        saveItem = new ToolItem(toolBar, SWT.PUSH);
        saveItem.setImage(Constant.IMAGE_SAVE);
        saveItem.setEnabled(false);

        // Add a SelectionListener to the ToolItem.
        saveItem.addSelectionListener(getSelectionAdapter4SaveVersion());

        // Set TextClient to section.
        section.setTextClient(toolBar);
    }

    private void createProjectToolbar(Section section) {
        // Create and layout a Toolbar.
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolBar = new ToolBar(section, style);

        // Create a ToolItem.
        saveItem = new ToolItem(toolBar, SWT.PUSH);
        saveItem.setImage(Constant.IMAGE_SAVE);
        saveItem.setEnabled(false);

        // Add a SelectionListener to the ToolItem.
//        saveItem.addSelectionListener(getSelectionAdapter4SaveProject());

        // Set TextClient to section.
        section.setTextClient(toolBar);
    }

    /*
     * Create Form in Section.
     */
    private void createForm(Section section) {
        FormToolkit toolkit = new FormToolkit(section.getDisplay());
        // Create a ScrolledForm.
        ScrolledForm form = toolkit.createScrolledForm(section);

        // Layout the body of the ScrolledForm.
        form.getBody().setLayout(new GridLayout(2, false));

        // Create a Label and a Textbox for Version.
        Composite body = form.getBody();
        toolkit.createLabel(body, "Version");
        versionText = createText(toolkit, body, white, "");

        // Add ControlDecoration to versionText.
        versionDecoration = createControlDecoration();

        // Add ModifyListener to versionText.
        versionText.addModifyListener(getModifyListener4VersionText());

        // Make versionDecoration is hidden.
        versionDecoration.hide();

        // Create a Label and a Textbox for Project.
        toolkit.createLabel(body, "Project");
        projectText = createText(toolkit, body, gray, "");

        // Create a Label and a Textbox for Deploy Time.
        toolkit.createLabel(body, "Deploy Time");
        deployTimeText = createText(toolkit, body, gray, "");

        // Create a Label and a Textbox for Deploy Source.
        toolkit.createLabel(body, "Deploy Source");
        deploySourceText = createText(toolkit, body, gray, "");

        // Create a Label and a Textbox for Save Time.
        toolkit.createLabel(body, "Save Time");
        saveTimeText = createText(toolkit, body, gray, "");

        // Create a Label and a Textbox for Target Version.
        toolkit.createLabel(body, "Target Version");
        targetVersionText = createText(toolkit, body, gray, "");

        // Set Client for the Section.
        section.setClient(form);
    }

    /*
     * Initial a textbox.
     */
    private Text createText(
        FormToolkit toolkit, Composite parent, Color color, String value) {
        // Create and layout a Text.
        GridData layoutDataText =
            new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        Text text = toolkit.createText(parent, value, SWT.READ_ONLY);
        text.setLayoutData(layoutDataText);
        text.setBackground(color);
        return text;
    }

    /*
     * Create ControlDecoration for versionText.
     */
    private ControlDecoration createControlDecoration() {
        ControlDecoration decoration =
            new ControlDecoration(versionText, SWT.TOP | SWT.LEFT);
        decoration.setImage(PlatformUI
            .getWorkbench().getSharedImages()
            .getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
        return decoration;
    }

    /*
     * Create SelectionListener for Form. When a Version node is clicked, this
     * method will fill in all Textbox in Version Properties View and the
     * formComposite is set visible. If another node is clicked, the
     * formComposite will be set invisible.
     */
    private void createSelectionService4BottomComposite(Section versionSection, Section projectSection) {
        // Create a ISelectionService.
        IWorkbenchWindow window =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        ISelectionService service = window.getSelectionService();

        // Add a SelectionListener.
        service.addSelectionListener(ID, getSelectionService(versionSection, projectSection));
    }

    /*
     * Get ISelectionListener.
     */
    private ISelectionListener getSelectionService(final Section versionSection, final Section projectSection) {
        return new ISelectionListener() {

            @Override
            public void selectionChanged(
                IWorkbenchPart part, ISelection selection) {
                IStructuredSelection sselection =
                    (IStructuredSelection) selection;
                Object firstElement = sselection.getFirstElement();
                if (firstElement != null && firstElement instanceof Version) {
                    setDataFormVersion((Version) firstElement);
                    versionSection.setVisible(true);
                    projectSection.setVisible(false);
                    versionSection.getParent().layout(true);
                } else if(firstElement instanceof Project) {
                    versionSection.setVisible(false);
                    projectSection.setVisible(true);
                    projectSection.getParent().layout(true);
                } else {
                    versionSection.setVisible(false);
                    projectSection.setVisible(false);
                }
            }
        };
    }

    /*
     * Fill in all Textbox in Version Properties View. If version is allowed to
     * edit, versionText is set editable.
     */
    private void setDataFormVersion(Version version) {
        // Set data to all Textboxes in the Form.
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        versionText.setText(version.getName());
        projectText.setText(version.getProject().getName());
        if (version.getDeployTime() != null) {
            deployTimeText.setText(format.format(version.getDeployTime()));
        } else {
            deployTimeText.setText("");
        }
        deploySourceText.setText(version.getDeploySource());
        saveTimeText.setText(format.format(version.getSaveTime()));
        targetVersionText.setText(version.getTargetVersion());

        // Set editable for version Textbox.
        boolean editable = version.isEditable();
        versionText.setEditable(editable);
    }

    /*
     * Create ModifyListener for versionText to enable save ToolItem. When the
     * TreeViewer is not focused and the version is allowed to edit,
     * saveItem.setEnabled(true). Otherwise, saveItem.setEnabled(false).
     */
    private ModifyListener getModifyListener4VersionText() {
        return new ModifyListener() {

            private static final long serialVersionUID = -4530687986524902521L;

            @Override
            public void modifyText(ModifyEvent event) {
                IStructuredSelection selection =
                    (IStructuredSelection) viewer.getSelection();
                Object firstElement = selection.getFirstElement();
                if (firstElement instanceof Version) {
                    // Set enable to save ToolItem.
                    boolean focus = viewer.getTree().isFocusControl();
                    boolean editable = ((Version) firstElement).isEditable();
                    saveItem.setEnabled(!focus && editable);

                    // Validate versionText.
                    validateVersionText(firstElement);
                }
            }
        };
    }

    /*
     * Validate the versionText. If the format of version is wrong, error
     * message is "The format is not true.". If the version name is dulicate,
     * error message is "The version has been already existed.".
     */
    private void validateVersionText(Object firstElement) {
        String name = versionText.getText().intern();
        boolean format = name.matches(VERSION_PATTERN);
        if (!format) {
            versionDecoration.setDescriptionText(VERSION_FORMAT_ERROR);
            versionDecoration.show();
            saveItem.setEnabled(false);
            return;
        }

        int id = ((Version) firstElement).getId();
        List<Version> versions =
            ((Version) firstElement).getProject().getVersions();
        for (Version version : versions) {
            if (version.getName().intern() == name && version.getId() != id) {
                versionDecoration.setDescriptionText(VERSION_DULICATE_ERROR);
                versionDecoration.show();
                saveItem.setEnabled(false);
                return;
            } else {
                versionDecoration.hide();
            }
        }
    }

    /*
     * Create SelectionAdapter for Save Toolbar Item in Toolbar.
     */
    private SelectionAdapter getSelectionAdapter4SaveVersion() {
        return new SelectionAdapter() {

            private static final long serialVersionUID = -5483634617709186172L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection =
                    (IStructuredSelection) viewer.getSelection();
                Object firstElement = selection.getFirstElement();
                if (firstElement instanceof Version) {
                    String name = versionText.getText();
                    ((Version) firstElement).setName(name);

                    // Update name to database.
                    versionService.update((Version) firstElement);

                    // Make save ToolItem is enable.
                    saveItem.setEnabled(false);

                    // Refresh the Viewer.
                    viewer.refresh(firstElement);
                }
            }
        };
    }
}