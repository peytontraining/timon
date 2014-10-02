package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import vn.enclave.peyton.fusion.common.AbstractTreeViewPart;

public class DriverTreeViewPart extends AbstractTreeViewPart {

	public static final String ID = "vn.enclave.peyton.fusion.view.driverTreeViewPart";

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setLabelProvider(new LabelProvider());

	}

}
