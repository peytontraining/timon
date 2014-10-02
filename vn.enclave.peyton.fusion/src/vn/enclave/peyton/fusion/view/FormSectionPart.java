package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.*;

public class FormSectionPart extends SectionPart {

	public FormSectionPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createClient(getSection(), toolkit);
	}

	private void createClient(Section section, FormToolkit toolkit) {
		section.setText("New SectionPart");
	}

}
