package vn.enclave.peyton.fusion.control;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public class VersionPropertySection {
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
    private Section versionPropertySection;
    private ScrolledForm versionPropertyScrolledForm;
    private FormToolkit toolkit;

    public Section getVersionPropertySection() {
        return versionPropertySection;
    }

    public VersionPropertySection(Composite parent) {
        createFormToolkitFrom(parent);
        createVersionPropertySectionInside(parent);
    }

    private void createFormToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createVersionPropertySectionInside(Composite parent) {
        versionPropertySection = toolkit.createSection(parent, Section.TITLE_BAR);
        versionPropertySection.setText("Version Properties");
    }

    public void setVisible(boolean isVisible) {
        versionPropertySection.setVisible(isVisible);
    }
}
