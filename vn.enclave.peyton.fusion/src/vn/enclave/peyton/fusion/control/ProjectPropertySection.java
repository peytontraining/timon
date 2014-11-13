package vn.enclave.peyton.fusion.control;

import java.util.Date;
import java.util.List;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.ProjectService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class ProjectPropertySection {
    private static final String PROJECT_DULICATE_ERROR = "The project has been already existed.";
    private static final String PROJECT_EMPTY_ERROR = "Name of project cannot be empty";
    private Label nameLbl;
    private Label gatewayLbl;
    private Label hostLbl;
    private Label portLbl;
    private Label uuidLbl;
    private Label licenseLbl;
    private Text nameTxt;
    private Text hostTxt;
    private Text uuidTxt;
    private Text notesTxt;
    private Button uuidRadioBtn;
    private Button hostPortRadioBtn;
    private Button deploymentLockedChkBox;
    private Button editNotesBtn;
    private Spinner portSpinner;
    private Combo licenseCombo;
    private ControlDecoration nameDecoration;
    private Section projectPropertySection;
    private ScrolledForm projectPropertyScrolledForm;
    private FormToolkit toolkit;
    private ToolItem saveToolItem;
    private IWorkbenchPartSite workbenchPartSite;
    private IWorkbenchPage workbenchPage;
    private ProjectService projectService = new ProjectService();

    public Section getProjectPropertySection() {
        return projectPropertySection;
    }

    private void setWorkbenchPartSite(IWorkbenchPartSite workbenchPartSite) {
        this.workbenchPartSite = workbenchPartSite;
    }

    public ProjectPropertySection(Composite parent, IWorkbenchPartSite workbenchPartSite) {
        setWorkbenchPartSite(workbenchPartSite);
        createWorkbenchPage();
        createFormToolkitFrom(parent);
        createProjectPropertySectionInside(parent);
    }

    private void createWorkbenchPage() {
        workbenchPage = workbenchPartSite.getPage();
    }

    private void createFormToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createProjectPropertySectionInside(Composite parent) {
        projectPropertySection = toolkit.createSection(parent, Section.TITLE_BAR);
        projectPropertySection.setText("Project Properties");

        createToolBarToProjectPropertySection();

        createProjectPropertyScrolledFromInside(projectPropertySection);
    }

    private void createToolBarToProjectPropertySection() {
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolbarPropertySection = new ToolBar(projectPropertySection, style);
        projectPropertySection.setTextClient(toolbarPropertySection);

        createSaveToolItemTo(toolbarPropertySection);
    }

    private void createProjectPropertyScrolledFromInside(Section projectPropertySection) {
        GridLayout layout = new GridLayout(2, false);
        projectPropertyScrolledForm = toolkit.createScrolledForm(projectPropertySection);

        Composite projectPropertyFormBody = projectPropertyScrolledForm.getBody();
        projectPropertyFormBody.setLayout(layout);

        createControlsTo(projectPropertyFormBody);

        projectPropertySection.setClient(projectPropertyScrolledForm);
    }

    private void createSaveToolItemTo(ToolBar toolbarPropertySection) {
        saveToolItem = new ToolItem(toolbarPropertySection, SWT.PUSH);
        saveToolItem.setImage(Constant.IMAGE_SAVE_AS);
        saveToolItem.setEnabled(false);
    }

    private void createControlsTo(Composite projectPropertyFormBody) {
        nameLbl = createLabelTo(projectPropertyFormBody);
        nameLbl.setText("Name:");

        nameTxt = createTextTo(projectPropertyFormBody);

        nameDecoration = createControlDecorationTo(nameTxt);
        nameDecoration.hide();

        gatewayLbl = createLabelTo(projectPropertyFormBody);
        gatewayLbl.setText("Gateway uses:");

        createRadioButtonCompositeInside(projectPropertyFormBody);

        hostLbl = createLabelTo(projectPropertyFormBody);
        hostLbl.setText("Gateway Host:");

        hostTxt = createTextTo(projectPropertyFormBody);

        portLbl = createLabelTo(projectPropertyFormBody);
        portLbl.setText("Gateway Port:");

        portSpinner = createSpinnerTo(projectPropertyFormBody);

        uuidLbl = createLabelTo(projectPropertyFormBody);
        uuidLbl.setText("Gateway UUID:");

        uuidTxt = createTextTo(projectPropertyFormBody);

        licenseLbl = createLabelTo(projectPropertyFormBody);
        licenseLbl.setText("License:");

        licenseCombo = createComboTo(projectPropertyFormBody);

        deploymentLockedChkBox = createCheckBoxTo(projectPropertyFormBody);
        deploymentLockedChkBox.setText("Deployment locked");

        notesTxt = createTextAreaTo(projectPropertyFormBody);

        editNotesBtn = createButtonTo(projectPropertyFormBody);
        editNotesBtn.setText("Edit Notes...");

        setEnableAllControl(false);
    }

    private Label createLabelTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        Label label = toolkit.createLabel(projectPropertyFormBody, "");
        label.setLayoutData(layoutData);
        return label;
    }

    private Text createTextTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Text text = toolkit.createText(projectPropertyFormBody, "");
        text.setLayoutData(layoutData);
        return text;
    }

    private ControlDecoration createControlDecorationTo(Text nameTxt) {
        int position = SWT.TOP | SWT.LEFT;
        ControlDecoration decoration = new ControlDecoration(nameTxt, position);
        decoration.setImage(createFieldErrorImageToNameDecoration());
        return decoration;
    }

    private void createRadioButtonCompositeInside(Composite projectPropertyFormBody) {
        GridLayout layout = new GridLayout(2, false);
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite radioButtonComposite = new Composite(projectPropertyFormBody, SWT.BORDER);
        radioButtonComposite.setLayout(layout);
        radioButtonComposite.setLayoutData(layoutData);

        uuidRadioBtn = createRadioButtonTo(radioButtonComposite);
        uuidRadioBtn.setText("UUID");
        uuidRadioBtn.setEnabled(false);

        hostPortRadioBtn = createRadioButtonTo(radioButtonComposite);
        hostPortRadioBtn.setText("Host/Port");
        hostPortRadioBtn.setEnabled(false);
    }

    private Spinner createSpinnerTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Spinner spinner = new Spinner(projectPropertyFormBody, SWT.BORDER);
        spinner.setLayoutData(layoutData);
        spinner.setMaximum(99999);
        spinner.setTextLimit(5);
        return spinner;
    }

    private Combo createComboTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Combo combo = new Combo(projectPropertyFormBody, SWT.NONE);
        combo.setLayoutData(layoutData);
        return combo;
    }

    private Button createCheckBoxTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        layoutData.horizontalSpan = 2;
        Button checkBox = toolkit.createButton(projectPropertyFormBody, "", SWT.CHECK);
        checkBox.setLayoutData(layoutData);
        return checkBox;
    }

    private Text createTextAreaTo(Composite projectPropertyFormBody) {
        Text textArea = toolkit.createText(projectPropertyFormBody, "", SWT.MULTI);
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        layoutData.heightHint = textArea.getLineHeight() * 5;
        layoutData.horizontalSpan = 2;
        textArea.setLayoutData(layoutData);
        return textArea;
    }

    private Button createButtonTo(Composite projectPropertyFormBody) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        layoutData.horizontalSpan = 2;
        Button button = toolkit.createButton(projectPropertyFormBody, "", SWT.PUSH);
        button.setLayoutData(layoutData);
        return button;
    }

    private void setEnableAllControl(boolean isEnable) {
        nameTxt.setEnabled(isEnable);
        hostTxt.setEnabled(isEnable);
        uuidTxt.setEnabled(isEnable);
        notesTxt.setEnabled(isEnable);
        uuidRadioBtn.setEnabled(isEnable);
        hostPortRadioBtn.setEnabled(isEnable);
        deploymentLockedChkBox.setEnabled(isEnable);
        editNotesBtn.setEnabled(isEnable);
        portSpinner.setEnabled(isEnable);
        licenseCombo.setEnabled(isEnable);
    }

    private Image createFieldErrorImageToNameDecoration() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        ISharedImages sharedImages = workbench.getSharedImages();
        return sharedImages.getImage(ISharedImages.IMG_DEC_FIELD_ERROR);
    }

    private Button createRadioButtonTo(Composite radioButtonComposite) {
        GridData layoutData = new GridData(SWT.LEFT, SWT.NONE, false, false);
        Button radioBtn = toolkit.createButton(radioButtonComposite, "", SWT.RADIO);
        radioBtn.setLayoutData(layoutData);
        return radioBtn;
    }

    public void addModifyListenerToPropertySection(ModifyListener modifyListener) {
        nameTxt.addModifyListener(modifyListener);
        nameTxt.addModifyListener(createModifyListenerToNameTxt());
        hostTxt.addModifyListener(modifyListener);
        uuidTxt.addModifyListener(modifyListener);
        notesTxt.addModifyListener(modifyListener);
        portSpinner.addModifyListener(modifyListener);
    }

    /*
     * Trigger project name validation as name Text is modified.
     */
    private ModifyListener createModifyListenerToNameTxt() {
        return new ModifyListener() {
            private static final long serialVersionUID = 1782445795664083609L;

            @Override
            public void modifyText(ModifyEvent event) {
                validateNameText();
            }
        };
    }

    private void validateNameText() {
        Project selectedProject = getSelectedProject();
        String modifiedName = nameTxt.getText();
        if (modifiedName.isEmpty()) {
            nameDecoration.setDescriptionText(PROJECT_EMPTY_ERROR);
            nameDecoration.show();
            saveToolItem.setEnabled(false);
            return;
        }
        int selectedId = selectedProject.getId();
        List<Project> projects = selectedProject.getPlan().getProjects();
        for (Project project : projects) {
            String currentName = project.getName();
            int currentId = project.getId();
            if (isDuplicateProjectName(modifiedName, currentName, selectedId, currentId)) {
                nameDecoration.setDescriptionText(PROJECT_DULICATE_ERROR);
                nameDecoration.show();
                saveToolItem.setEnabled(false);
                return;
            }
        }
        nameDecoration.hide();
    }

    private Project getSelectedProject() {
        ISelection selection = workbenchPage.getSelection(NavigationViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object selectedNode = sselection.getFirstElement();
        Project selectedProject = (Project) selectedNode;
        return selectedProject;
    }

    private boolean isDuplicateProjectName(String modifiedName, String currentName, int selectedId, int currentId) {
        return modifiedName.equals(currentName) && selectedId != currentId;
    }

    public void setVisible(boolean isVisible) {
        projectPropertySection.setVisible(isVisible);
    }

    public void setEnableSaveToolItem(boolean isEnable) {
        saveToolItem.setEnabled(isEnable);
    }

    public void populatePropertyScrolledFormFrom(Project selectedProject) {
        if (selectedProject.isNewProject()) {
            int endIndex = selectedProject.getName().lastIndexOf(" *");
            nameTxt.setText(selectedProject.getName().substring(0, endIndex));
        } else {
            nameTxt.setText(selectedProject.getName());
        }
        if (selectedProject.getGateway()) {
            uuidRadioBtn.setSelection(true);
            hostPortRadioBtn.setSelection(false);
        } else {
            uuidRadioBtn.setSelection(false);
            hostPortRadioBtn.setSelection(true);
        }

        hostTxt.setText(selectedProject.getHost());
        portSpinner.setSelection(selectedProject.getPort());
        uuidTxt.setText(selectedProject.getUuid());
        licenseCombo.setText(selectedProject.getLicense());
        deploymentLockedChkBox.setSelection(selectedProject.isDeploymentLocked());
        notesTxt.setText(selectedProject.getNote());

        setEnableAllControl(selectedProject.isEditable());
    }

    public void addSelectionAdapterToPropertySection(SelectionAdapter createSelectionAdapterToPropertySection) {
        saveToolItem.addSelectionListener(createSelectionAdapterToPropertySection);
    }

    public Project saveModifiedProject() {
        Project selectedProject = getSelectedProject();

        if (selectedProject.isNewProject()) {
            Plan currentPlan = selectedProject.getPlan();
            prepareNewProject(selectedProject);

            /*
             * After add() method run, the return variable new Project has a new
             * Plan pointer.
             */
            selectedProject = projectService.add(selectedProject);

            selectedProject.setPlan(currentPlan);

            // Remove the temp object in treeview and add the new
            // object that got from add query.
            currentPlan.getProjects().set(0, selectedProject);
        } else {
            prepareModifiedProject(selectedProject);
            projectService.update(selectedProject);
        }

        return selectedProject;
    }

    private void prepareNewProject(Project selectedProject) {
        selectedProject.setName(nameTxt.getText());
        selectedProject.setGateway(uuidRadioBtn.getSelection());
        selectedProject.setHost(hostTxt.getText());
        selectedProject.setLicense(licenseCombo.getText());
        selectedProject.setPort(portSpinner.getSelection());
        selectedProject.setUuid(uuidTxt.getText());
        Version newVersion = selectedProject.getVersions().get(0);
        newVersion.setName("1.0.0");
        newVersion.setSaveTime(new Date());
    }

    private void prepareModifiedProject(Project selectedProject) {
        selectedProject.setName(nameTxt.getText());
        selectedProject.setHost(hostTxt.getText());
        selectedProject.setUuid(uuidTxt.getText());
        selectedProject.setNote(notesTxt.getText());
        selectedProject.setPort(Integer.valueOf(portSpinner.getText()));
    }
}
