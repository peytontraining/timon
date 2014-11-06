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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
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
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class VersionPropertySection {
    private static final String VERSION_PATTERN = "[0-9]++\\.[0-9]++\\.[0-9]++";
    private static final String VERSION_FORMAT_ERROR = "The format is not true.";
    private static final String VERSION_DULICATE_ERROR = "The version has been already existed.";
    private Label versionLbl;
    private Label projectLbl;
    private Label deployTimeLbl;
    private Label deploySourceLbl;
    private Label saveTimeLbl;
    private Label targetVersionLbl;
    private Text versionTxt;
    private Text projectTxt;
    private Text deployTimeTxt;
    private Text deploySourceTxt;
    private Text saveTimeTxt;
    private Text targetVersionTxt;
    private ControlDecoration versionDecoration;
    private Section versionPropertySection;
    private ScrolledForm versionPropertyScrolledForm;
    private FormToolkit toolkit;
    private ToolItem saveToolItem;
    private IWorkbenchPartSite workbenchPartSite;
    private IWorkbenchPage workbenchPage;
    private VersionService versionService = new VersionService();

    public Section getVersionPropertySection() {
        return versionPropertySection;
    }

    private void setWorkbenchPartSite(IWorkbenchPartSite workbenchPartSite) {
        this.workbenchPartSite = workbenchPartSite;
    }

    public VersionPropertySection(Composite parent, IWorkbenchPartSite workbenchPartSite) {
        setWorkbenchPartSite(workbenchPartSite);
        createWorkbenchPage();
        createFormToolkitFrom(parent);
        createVersionPropertySectionInside(parent);
    }

    private void createWorkbenchPage() {
        workbenchPage = workbenchPartSite.getPage();
    }

    private void createFormToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createVersionPropertySectionInside(Composite parent) {
        versionPropertySection = toolkit.createSection(parent, Section.TITLE_BAR);
        versionPropertySection.setText("Version Properties");

        createToolBarToVersionPropertySection();

        createVersionPropertyScrolledFromInside(versionPropertySection);
    }

    private void createToolBarToVersionPropertySection() {
        int style = SWT.FLAT | SWT.HORIZONTAL;
        ToolBar toolbarPropertySection = new ToolBar(versionPropertySection, style);
        versionPropertySection.setTextClient(toolbarPropertySection);

        createSaveToolItemTo(toolbarPropertySection);
    }

    private void createVersionPropertyScrolledFromInside(Section versionPropertySection) {
        GridLayout layout = new GridLayout(2, false);
        versionPropertyScrolledForm = toolkit.createScrolledForm(versionPropertySection);

        Composite versionPropertyFormBody = versionPropertyScrolledForm.getBody();
        versionPropertyFormBody.setLayout(layout);

        createControlsTo(versionPropertyFormBody);

        versionPropertySection.setClient(versionPropertyScrolledForm);
    }

    private void createSaveToolItemTo(ToolBar toolbarPropertySection) {
        saveToolItem = new ToolItem(toolbarPropertySection, SWT.PUSH);
        saveToolItem.setImage(Constant.IMAGE_SAVE_AS);
        saveToolItem.setEnabled(false);
    }

    private void createControlsTo(Composite versionPropertyFormBody) {
        versionLbl = createLabelTo(versionPropertyFormBody);
        versionLbl.setText("Version:");

        versionTxt = createTextTo(versionPropertyFormBody);

        versionDecoration = createControlDecorationTo(versionTxt);

        projectLbl = createLabelTo(versionPropertyFormBody);
        projectLbl.setText("Project:");

        projectTxt = createTextTo(versionPropertyFormBody);

        deployTimeLbl = createLabelTo(versionPropertyFormBody);
        deployTimeLbl.setText("Deploy Time:");

        deployTimeTxt = createTextTo(versionPropertyFormBody);

        deploySourceLbl = createLabelTo(versionPropertyFormBody);
        deploySourceLbl.setText("Deploy Source:");

        deploySourceTxt = createTextTo(versionPropertyFormBody);

        saveTimeLbl = createLabelTo(versionPropertyFormBody);
        saveTimeLbl.setText("Save Time:");

        saveTimeTxt = createTextTo(versionPropertyFormBody);

        targetVersionLbl = createLabelTo(versionPropertyFormBody);
        targetVersionLbl.setText("Target Version:");

        targetVersionTxt = createTextTo(versionPropertyFormBody);

        setEnableAllControl(false);
    }

    private Label createLabelTo(Composite versionPropertyFormBody) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        Label label = toolkit.createLabel(versionPropertyFormBody, "");
        label.setLayoutData(layoutData);
        return label;
    }

    private Text createTextTo(Composite versionPropertyFormBody) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Text text = toolkit.createText(versionPropertyFormBody, "");
        text.setLayoutData(layoutData);
        return text;
    }

    private ControlDecoration createControlDecorationTo(Text versionTxt) {
        int position = SWT.TOP | SWT.LEFT;
        ControlDecoration decoration = new ControlDecoration(versionTxt, position);
        decoration.setImage(createFieldErrorImageToVersionDecoration());
        return decoration;
    }

    private void setEnableAllControl(boolean isEnable) {
        versionTxt.setEnabled(isEnable);
        projectTxt.setEnabled(isEnable);
        deployTimeTxt.setEnabled(isEnable);
        deploySourceTxt.setEnabled(isEnable);
        saveTimeTxt.setEnabled(isEnable);
        targetVersionTxt.setEnabled(isEnable);
    }

    private Image createFieldErrorImageToVersionDecoration() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        ISharedImages sharedImages = workbench.getSharedImages();
        return sharedImages.getImage(ISharedImages.IMG_DEC_FIELD_ERROR);
    }

    public void addModifyListenerToPropertySection(ModifyListener modifyListener) {
        versionTxt.addModifyListener(modifyListener);
        versionTxt.addModifyListener(createModifyListenerToVersionTxt());
    }

    /*
     * Trigger version name validation as version Text is modified.
     */
    private ModifyListener createModifyListenerToVersionTxt() {
        return new ModifyListener() {
            private static final long serialVersionUID = 1782445795664083609L;

            @Override
            public void modifyText(ModifyEvent event) {
                validateVersionText();
            }
        };
    }

    private void validateVersionText() {
        Version selectedVersion = getSelectedVersion();
        String modifiedName = versionTxt.getText();
        if (!modifiedName.matches(VERSION_PATTERN)) {
            versionDecoration.setDescriptionText(VERSION_FORMAT_ERROR);
            versionDecoration.show();
            saveToolItem.setEnabled(false);
            return;
        }

        int selectedId = selectedVersion.getId();
        List<Version> versions = selectedVersion.getProject().getVersions();
        for (Version version : versions) {
            String currentName = version.getName();
            int currentId = version.getId();
            if (isDuplicateProjectName(modifiedName, currentName, selectedId, currentId)) {
                versionDecoration.setDescriptionText(VERSION_DULICATE_ERROR);
                versionDecoration.show();
                saveToolItem.setEnabled(false);
                return;
            }
        }
        versionDecoration.hide();
    }

    private Version getSelectedVersion() {
        ISelection selection = workbenchPage.getSelection(NavigationViewPart.ID);
        IStructuredSelection sselection = (IStructuredSelection) selection;
        Object selectedNode = sselection.getFirstElement();
        Version selectedVersion = (Version) selectedNode;
        return selectedVersion;
    }

    private boolean isDuplicateProjectName(String modifiedName, String currentName, int selectedId, int currentId) {
        return modifiedName.equals(currentName) && selectedId != currentId;
    }

    public void setVisible(boolean isVisible) {
        versionPropertySection.setVisible(isVisible);
    }

    public void setEnableSaveToolItem(boolean isEnable) {
        saveToolItem.setEnabled(isEnable);
    }

    public void populatePropertyScrolledFormFrom(Version selectedVersion) {
        if (selectedVersion.isNewVersion()) {
            int endIndex = selectedVersion.getName().lastIndexOf(" *");
            versionTxt.setText(selectedVersion.getName().substring(0, endIndex));
        } else {
            versionTxt.setText(selectedVersion.getName());
        }
        projectTxt.setText(selectedVersion.getProject().getName());
        if (selectedVersion.getDeployTime() != null) {
            deployTimeTxt.setText(Utils.convertDate2String(selectedVersion.getDeployTime()));
        } else {
            deployTimeTxt.setText("");
        }
        if (selectedVersion.getDeploySource() != null) {
            deploySourceTxt.setText(selectedVersion.getDeploySource());
        } else {
            deployTimeTxt.setText("");
        }

        if (selectedVersion.getSaveTime() != null) {
            saveTimeTxt.setText(Utils.convertDate2String(selectedVersion.getSaveTime()));
        } else {
            saveTimeTxt.setText("");
        }
        targetVersionTxt.setText(selectedVersion.getTargetVersion());

        setEnableAllControl(selectedVersion.isEditable());
    }

    public void addSelectionAdapterToPropertySection(SelectionAdapter createSelectionAdapterToPropertySection) {
        saveToolItem.addSelectionListener(createSelectionAdapterToPropertySection);
    }

    public Version saveModifiedVersion() {
        Version selectedVersion = getSelectedVersion();

        if (selectedVersion.isNewVersion()) {
            Project currentProject = selectedVersion.getProject();
            prepareNewVersion(selectedVersion);

            /*
             * After add() method run, the return variable new Version has a new
             * Project pointer.
             */
            selectedVersion = versionService.add(selectedVersion);

            selectedVersion.setProject(currentProject);

            // Remove the temp object in treeview and add the new
            // object that got from add query.
            currentProject.getVersions().set(0, selectedVersion);
        } else {
            String name = versionTxt.getText();
            selectedVersion.setName(name);
            versionService.update(selectedVersion);
        }

        return selectedVersion;
    }

    private void prepareNewVersion(Version selectedVersion) {
        selectedVersion.setName(versionTxt.getText());
        selectedVersion.setDeploySource(deploySourceTxt.getText());
        selectedVersion.setSaveTime(new Date());
        selectedVersion.setTargetVersion(targetVersionTxt.getText());

    }
}
