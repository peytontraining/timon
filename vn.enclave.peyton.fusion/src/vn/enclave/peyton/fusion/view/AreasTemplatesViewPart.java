package vn.enclave.peyton.fusion.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

public class AreasTemplatesViewPart extends ViewPart {
    public static final String ID = "vn.enclave.peyton.fusion.view.areasTemplatesViewPart";

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(2, false);
        parent.setLayout(layout);

        FormToolkit toolkit = new FormToolkit(parent.getDisplay());

        toolkit.createLabel(parent, "Template:");
        Combo combo = new Combo(parent, SWT.DROP_DOWN);
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        combo.setLayoutData(layoutData);

        toolkit.createLabel(parent, "Name:");
        toolkit.createText(parent, "").setLayoutData(layoutData);

        toolkit.createLabel(parent, "Notes:");
        toolkit.createText(parent, "", SWT.MULTI).setLayoutData(layoutData);

        layoutData = new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1);
        toolkit.createText(parent, "").setLayoutData(layoutData);
    }

    @Override
    public void setFocus() {

    }

}
