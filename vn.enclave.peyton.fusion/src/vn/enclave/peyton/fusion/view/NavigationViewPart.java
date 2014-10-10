package vn.enclave.peyton.fusion.view;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
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
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.PlanFilter;
import vn.enclave.peyton.fusion.provider.PlanTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PlanTreeLableProvider;
import vn.enclave.peyton.fusion.service.impl.PlanService;

public class NavigationViewPart extends ViewPart {

    public static final String
        ID = "vn.enclave.peyton.fusion.view.navigationViewPart";

    private Composite treeComposite;
    private Composite formComposite;
    private TreeViewer viewer;
    private Color gray, white;
    private ToolItem saveItem;
    private Text versionText;
    private Text projectText;
    private Text deployTimeText;
    private Text deploySourceText;
    private Text saveTimeText;
    private Text targetVersionText;

    @Override
    public void createPartControl(Composite parent) {
        FillLayout layout = new FillLayout(SWT.VERTICAL);
        parent.setLayout(layout);

        // Create TreeViewer.
        createViewer(parent);

        // Set value for colors.
        gray = parent.getDisplay().getSystemColor(SWT.COLOR_GRAY);
        white = parent.getDisplay().getSystemColor(SWT.COLOR_WHITE);

        // Create Form
        createForm(parent);
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    private void createViewer(Composite parent) {
        // Create and layout the composite.
        GridLayout layout = new GridLayout(1, false);
        treeComposite = new Composite(parent, SWT.BORDER);
        treeComposite.setLayout(layout);

        // Create the PatternFilter.
        PatternFilter filter = new PatternFilter();

        // Create and layout the FilteredTree.
        GridData layoutData = 
            new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        int style = SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
        FilteredTree filteredTree = 
            new PlanFilter(treeComposite, style, filter, true);
        filteredTree.setLayoutData(layoutData);

        // Create a TreeViewer from the FilteredTree.
        viewer = filteredTree.getViewer();

        // Set the ContentProvider.
        viewer.setContentProvider(new PlanTreeContentProvider());

        // Set the LableProvider.
        viewer.setLabelProvider(new PlanTreeLableProvider());

        // Set the content for the Viewer,
        // setInput will call getElements in the ContentProvider.
        PlanService service = new PlanService();
        List<Plan> plans = service.getAll();
        viewer.setInput(plans);

        // Make the selection available to other Views.
        getSite().setSelectionProvider(viewer);
    }


    private void createForm(Composite parent) {
        // Create and layout a composite.
        int style = SWT.NONE | SWT.BORDER;
        formComposite = new Composite(parent, style);
        formComposite.setLayout(new FillLayout());

        // Make the composite is invisible.
        formComposite.setVisible(false);

        // Create a Section for the Composite.
        createSection(formComposite);

        // Create a SelectionListener for form.
        createSelectionForm(formComposite);
    }

    private void createSection(Composite parent) {
        Display display = parent.getDisplay();
        // Create a FormToolKit.
        FormToolkit toolkit = new FormToolkit(display);

        // Create and layout a Section.
        Section section = toolkit
            .createSection(parent, Section.TITLE_BAR);
        section.setLayout(new FillLayout());

        // Set text and fore color for title.
        section.setText("Version Properties");
        section.setForeground(display.getSystemColor(SWT.COLOR_BLACK));

        // Create a Toolbar for the Section.
        createToolbar(section);

        // Create a ScrooledForm for the Section.
        createForm(toolkit, section);
    }

    private void createToolbar(Section section) {
        // Create a Toolbar.
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolBar = new ToolBar(section, style);

        // Create a ToolItem.
        saveItem = new ToolItem(toolBar, SWT.PUSH);
        saveItem.setImage(Constant.SAVE_IMAGE);
        saveItem.setEnabled(false);

        // Add a SelectionListener to the ToolItem.
        saveItem.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = -5483634617709186172L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("Save ToolItem");
            }
        });

        // Set TextClient to section.
        section.setTextClient(toolBar);
    }

    private void createForm(FormToolkit toolkit, Section section) {
        // Create a ScrolledForm.
        ScrolledForm form = toolkit.createScrolledForm(section);

        // Layout the body of the ScrolledForm.
        form.getBody().setLayout(new GridLayout(2, false));

        // Create a Label and a Textbox for Version.
        Composite body = form.getBody();
        toolkit.createLabel(body, "Version");
        versionText = createText(toolkit, body, white, "");

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

    private Text createText(FormToolkit toolkit, Composite parent,
        Color color, String value) {
        // Create and layout a Text.
        GridData layoutDataText = 
                new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
        Text text = toolkit.createText(parent, value, SWT.READ_ONLY);
        text.setLayoutData(layoutDataText);
        text.setBackground(color);
        return text;
    }

    private void createSelectionForm(Composite formComposite2) {
        // Create a ISelectionService.
        IWorkbenchWindow window = PlatformUI
            .getWorkbench().getActiveWorkbenchWindow();
        ISelectionService service = window.getSelectionService();

        // Add a SelectionListener.
        service.addSelectionListener(ID, createSelectionService() );
    }

    private ISelectionListener createSelectionService() {
        return new ISelectionListener() {

            @Override
            public void 
                selectionChanged(IWorkbenchPart part, ISelection selection) {
                IStructuredSelection sselection = 
                    (IStructuredSelection) selection;
                Object firstElement = sselection.getFirstElement();
                if (firstElement != null && firstElement instanceof Version) {
                    setDataForm((Version) firstElement);
                    formComposite.setVisible(true);
                } else {
                    formComposite.setVisible(false);
                }
            }
        };
    }

    private void setDataForm(Version version) {
        // Set data to all Textboxes in the Form.
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        versionText.setText(version.getName());
        projectText.setText(version.getProject().getName());
        deployTimeText.setText(format.format(version.getDeployTime()));
        deploySourceText.setText(version.getDeploySource());
        saveTimeText.setText(format.format(version.getSaveTime()));
        targetVersionText.setText(version.getTargetVersion());

        // Set editable for version Textbox.
        boolean editable = version.isEditable();
        versionText.setEditable(editable);
        targetVersionText.setEditable(editable);

        // Change background color of Target Version Textbox.
        targetVersionText.setBackground(editable ? white : gray);

        // Set enable for the ToolItem.
        saveItem.setEnabled(editable);
    }
}