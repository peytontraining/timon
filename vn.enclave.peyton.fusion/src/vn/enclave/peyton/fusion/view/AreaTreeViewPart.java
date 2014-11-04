package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import vn.enclave.peyton.fusion.common.AbstractTreeViewPart;

public class AreaTreeViewPart extends AbstractTreeViewPart {

    public static final String ID = "vn.enclave.peyton.fusion.view.areaTreeViewPart";

    @Override
    public void createPartControl(Composite parent) {
        viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
        viewer.setContentProvider(new ViewContentProvider());
        viewer.setLabelProvider(new LabelProvider());

    }

}