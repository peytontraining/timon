package vn.enclave.peyton.fusion.view;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import vn.enclave.peyton.fusion.common.AbstractTableViewPart;

public class ServiceTableViewPart extends AbstractTableViewPart {

    public static final String 
        ID = "vn.enclave.peyton.fusion.view.serviceTableViewPart";

    @Override
    public void createPartControl(Composite parent) {
        tableViewer = new TableViewer(parent, SWT.NONE);
        tableViewer.setContentProvider(new ViewContentProvider());
        tableViewer.setLabelProvider(new ViewLabelProvider());
        final Table table = tableViewer.getTable();
        tableViewer.setColumnProperties(initColumnProperties(table));
    }

    @Override
    protected String[] initColumnProperties(Table table) {
        return null;
    }

}