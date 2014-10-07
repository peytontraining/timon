package vn.enclave.peyton.fusion.view;

import java.util.*;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

import vn.enclave.peyton.fusion.common.*;
import vn.enclave.peyton.fusion.common.AbstractTreeViewPart.TreeObject;
import vn.enclave.peyton.fusion.service.DeviceService;
import vn.enclave.peyton.fusion.view.filter.DeviceFilter;

public class DeviceTableViewPart extends AbstractTableViewPart implements
        IDoubleClickListener {

    public static final String ID = "vn.enclave.peyton.fusion.view.deviceTableViewPart";

    private static final String[] COLUMN_PROPERTIES = { "Name", "App Module",
            "Device Type", "Physical Location", "Manufacture" };
    private static final int DOWN_OFFSET = 1;
    private static final int UP_OFFSET = -1;

    private Table table;
    private Text filterText, findText;
    private Button up, down;

    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(8, false));

        /* Create controls for filter and finder */
        Label filterLabel = new Label(parent, SWT.NONE);
        filterLabel.setText("Filter: ");
        filterText = new Text(parent, SWT.BORDER | SWT.SEARCH);
        filterText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
                2, 1));

        Label findLabel = new Label(parent, SWT.NONE);
        findLabel.setText("Find: ");
        findText = new Text(parent, SWT.BORDER | SWT.SEARCH);
        findText.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2,
                1));
        up = new Button(parent, SWT.NONE);
        up.setImage(Constant.ARROW_UP_IMAGE);
        up.setToolTipText("Up");
        down = new Button(parent, SWT.NONE);
        down.setImage(Constant.ARROW_DOWN_IMAGE);
        down.setToolTipText("Down");

        /* Create TableViewer */
        tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION
                | SWT.H_SCROLL | SWT.V_SCROLL);
        table = tableViewer.getTable();
        tableViewer.setContentProvider(new ViewContentProvider());
        tableViewer.setLabelProvider(new VLabelProvider());
        tableViewer.setColumnProperties(initColumnProperties(table));

        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setForeground(getSite().getShell().getDisplay()
                .getSystemColor(SWT.COLOR_BLUE));
        getSite().setSelectionProvider(tableViewer);

        /* Create filter */
        final DeviceFilter filter = new DeviceFilter();
        filterText.addModifyListener(new ModifyListener() {

            private static final long serialVersionUID = 3629305489301067031L;

            @Override
            public void modifyText(ModifyEvent event) {
                filter.setFilterString(filterText.getText());
                tableViewer.refresh();
                find();
            }
        });
        tableViewer.addFilter(filter);

        /* Create finder */
        findText.addModifyListener(new ModifyListener() {

            private static final long serialVersionUID = 6754389583850064696L;

            @Override
            public void modifyText(ModifyEvent event) {
                find();
            }
        });

        findText.addKeyListener(new KeyAdapter() {
            private static final long serialVersionUID = -5488000045054772158L;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    find();
                }
            }
        });

        up.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = 5405524889838122005L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                find(UP_OFFSET);
            }
        });

        down.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = -8610275856504507986L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                find(DOWN_OFFSET);
            }
        });

        tableViewer.addDoubleClickListener(this);
        createSelectionListener();
    }

    /* This method is called by listeners of Up button and Down button */
    private void find(int offset) {
        TableItem[] rows = table.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = table.getColumnCount();
        ArrayList<Integer> focusIndexes = new ArrayList<Integer>();

        if (numberOfRow != 0) {

            /* Get indexes of match rows */
            for (int i = 0; i < numberOfRow; i++) {
                for (int j = 0; j < nubmerOfColumn; j++) {
                    if (rows[i].getText(j).toLowerCase()
                            .contains(findText.getText().trim().toLowerCase())) {
                        focusIndexes.add(i);
                        break;
                    }
                }
            }

            int selectionIndex = table.getSelectionIndex();
            int index = focusIndexes.indexOf(selectionIndex) + offset;

            if (index < 0) {
                index = focusIndexes.size() - 1;
            } else if (index > focusIndexes.size() - 1) {
                index = 0;
            }
            table.select(focusIndexes.get(index));
        }
    }

    /* This method is called by find textbox's and filter textbox's listeners */
    private void find() {
        TableItem[] rows = table.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = table.getColumnCount();
        int index = -1;

        if (numberOfRow != 0) {
            /* Get indexes of match rows */
            for (int i = 0; i < numberOfRow; i++) {
                for (int j = 0; j < nubmerOfColumn; j++) {
                    if (rows[i].getText(j).toLowerCase()
                            .contains(findText.getText().trim().toLowerCase())) {
                        index = i;
                        table.select(index);
                        break;
                    }
                }

                if (index != -1) {
                    break;
                }
            }

            /* Deselect row when find textbox is empty */
            if (findText.getText().length() == 0 || findText.getText() == null
                    || index == -1) {
                table.deselectAll();
            }
        }
    }

    @Override
    protected String[] initColumnProperties(Table table) {
        for (String property : COLUMN_PROPERTIES) {
            TableColumn column = new TableColumn(table, SWT.FILL);
            column.setText(property);
            column.pack();
        }
        return COLUMN_PROPERTIES;
    }

    private void createSelectionListener() {
        IWorkbenchWindow window = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        ISelectionService selectionService = window.getSelectionService();

        /* This method is called when there is any version node is clicked */
        selectionService.addSelectionListener(NavigationViewPart.ID,
                new ISelectionListener() {

                    @Override
                    public void selectionChanged(IWorkbenchPart part,
                            ISelection selection) {
                        IStructuredSelection sselection = (IStructuredSelection) selection;
                        Object firstElement = sselection.getFirstElement();
                        if (firstElement != null) {
                            if (Constant.VERSION_NODE_LEVEL == ((TreeObject) firstElement)
                                    .getLevel()) {
                                DeviceService deviceService = new DeviceService();
                                List<Object[]> devices = deviceService
                                        .findAllByVersionID((String) ((TreeObject) firstElement)
                                                .getData());
                                tableViewer.setInput(devices);
                                tableViewer.getTable().setEnabled(true);
                                TableColumn[] columns = tableViewer.getTable()
                                        .getColumns();
                                for (TableColumn c : columns) {
                                    c.pack();
                                }

                                if (findText.getText().length() != 0
                                        || findText.getText() != null) {
                                    find();
                                }
                            } else {
                                table.removeAll();
                            }
                        }

                    }
                });
    }

    private class VLabelProvider extends ViewLabelProvider {

        private static final long serialVersionUID = 1756428861179912259L;

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            Image image = null;
            Object[] row = (Object[]) element;
            if (0 == columnIndex) {
                switch (row[2].toString()) {
                case Constant.IP_CAMERAS:
                    image = Constant.CAMERA_IMAGE;
                    break;

                case Constant.DVR_NVR:
                    image = Constant.DVR_NVR_IMAGE;
                    break;

                case Constant.FIREPLACE_CONTROLLER:
                    image = Constant.FIREPLACE_CONTROLLER_IMAGE;
                    break;
                }
            }

            return image;
        }

    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
        IStructuredSelection sselection = (IStructuredSelection) event
                .getSelection();
        Object firstElement = sselection.getFirstElement();
        if (firstElement != null) {
            Object[] row = (Object[]) firstElement;
            IWorkbenchWindow window = PlatformUI.getWorkbench()
                    .getActiveWorkbenchWindow();
            StringBuilder message = new StringBuilder(
                    "You have double clicked on ").append(row[0].toString());
            MessageDialog.openInformation(window.getShell(), "Device Table",
                    message.toString());
        }
    }
}
