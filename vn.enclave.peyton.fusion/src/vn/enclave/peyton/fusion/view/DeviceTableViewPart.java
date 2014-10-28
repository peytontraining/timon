package vn.enclave.peyton.fusion.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.comparator.DeviceTableViewerComparator;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Icon;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.DeviceFilter;

public class DeviceTableViewPart extends ViewPart
    implements IDoubleClickListener {

    public static final int NAME_COLUMN = 0;

    public static final int APP_MODULE_COLUMN = 1;

    public static final int DEVICE_TYPE_COLUMN = 2;

    public static final int PHYSICAL_LOCATION_COLUMN = 3;

    public static final int MANUFACTURE_COLUMN = 4;

    private static final int DEFAULT_WIDTH = 100;

    private static final int DOWN_OFFSET = 1;

    private static final int UP_OFFSET = -1;

    public static final String ID =
        "vn.enclave.peyton.fusion.view.deviceTableViewPart";

    private static final String[] TITLES =
        {
            "Name", "App Module", "Device Type", "Physical Location",
            "Manufacture"};

    private TableViewer viewer;

    private DeviceTableViewerComparator comparator;

    private Text filterText, finderText;

    private Button clear, up, down;

    private DeviceFilter filter;

    public TableViewer getViewer() {
        return viewer;
    }

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(9, false);
        parent.setLayout(layout);

        // Create filter.
        createFilter(parent);

        // Create finder.
        createFinder(parent);

        // Create TableViewer.
        createViewer(parent);

        // Set the filter for the table.
        filter = new DeviceFilter();
        viewer.addFilter(filter);

        // Set the sorter for the table.
        comparator = new DeviceTableViewerComparator();
        viewer.setComparator(comparator);

        // Set selection service
        createSelectionListener();
    }

    @Override
    public void setFocus() {
        viewer.getControl().setFocus();
    }

    private void createFilter(Composite parent) {
        // Create filter label.
        Label label = new Label(parent, SWT.NONE);
        label.setText("Filter:");

        // Create and layout filter text.
        GridData layoutData =
            new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        filterText = new Text(parent, SWT.BORDER | SWT.SEARCH);
        filterText.setLayoutData(layoutData);
        filterText.addModifyListener(new ModifyListener() {

            private static final long serialVersionUID = 4906617449904102933L;

            @Override
            public void modifyText(ModifyEvent event) {
                filter.setFilterString(filterText.getText());
                viewer.refresh();
                boolean enabled =
                    filterText.getText() != null
                        & filterText.getText().length() != 0;
                clear.setEnabled(enabled);
            }
        });

        // Create clear button
        clear = new Button(parent, SWT.NONE);
        clear.setImage(Constant.IMAGE_EDIT);
        clear.setEnabled(false);
        clear.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = 8727982726761507228L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                filterText.setText("");
                clear.setEnabled(false);
            }
        });
    }

    private void createFinder(Composite parent) {
        // Create and layout finder label.
        GridData layoutData =
            new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
        Label label = new Label(parent, SWT.NONE);
        label.setText("Find:");
        label.setLayoutData(layoutData);

        // Create and layout finder text.
        layoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        finderText = new Text(parent, SWT.BORDER | SWT.SEARCH);
        finderText.setLayoutData(layoutData);
        finderText.addModifyListener(new ModifyListener() {

            private static final long serialVersionUID = -1718429326062300859L;

            @Override
            public void modifyText(ModifyEvent event) {
                find(finderText.getText());
            }
        });
        finderText.addKeyListener(new KeyAdapter() {

            private static final long serialVersionUID = -5866801124911784172L;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    find(finderText.getText());
                }
            }
        });

        // Create up button
        up = new Button(parent, SWT.NONE);
        up.setImage(Constant.IMAGE_ARROW_UP);
        up.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = -5886805186732591167L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                find(finderText.getText(), UP_OFFSET);
            }
        });

        // Create down button
        down = new Button(parent, SWT.NONE);
        down.setImage(Constant.IMAGE_ARROW_DOWN);
        down.addSelectionListener(new SelectionAdapter() {

            private static final long serialVersionUID = -3866766296386752019L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                find(finderText.getText(), DOWN_OFFSET);
            }
        });
    }

    private void find(String findText) {
        Table table = viewer.getTable();
        TableItem[] rows = table.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = table.getColumnCount();
        int index = -1;
        if (numberOfRow != 0) {
            // Get indexes of match rows.
            for (int i = 0; i < numberOfRow; i++) {
                for (int j = 0; j < nubmerOfColumn; j++) {
                    if (rows[i]
                        .getText(j).toLowerCase()
                        .contains(findText.trim().toLowerCase())) {
                        index = i;
                        table.select(index);
                        break;
                    }
                }

                if (index != -1) {
                    break;
                }
            }

            // Deselect row when find textbox is empty.
            if (findText.length() == 0 || findText == null || index == -1) {
                table.deselectAll();
            }
        }
    }

    protected void find(String findText, int offset) {
        Table table = viewer.getTable();
        TableItem[] rows = table.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = table.getColumnCount();
        ArrayList<Integer> focusIndexes = new ArrayList<Integer>();
        if (numberOfRow != 0) {
            // Get indexes of match rows.
            for (int i = 0; i < numberOfRow; i++) {
                for (int j = 0; j < nubmerOfColumn; j++) {
                    if (rows[i]
                        .getText(j).toLowerCase()
                        .contains(findText.trim().toLowerCase())) {
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

    private void createViewer(Composite parent) {
        // Define the TableViewer.
        viewer =
            new TableViewer(parent, SWT.H_SCROLL
                | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

        // Layout the viewer
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 9, 1);
        viewer.getControl().setLayoutData(gridData);

        // Set the ContentProvider
        viewer.setContentProvider(ArrayContentProvider.getInstance());

        // Make lines and make header visible.
        Table table = viewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        // Create the columns
        createColumns(parent);

        // Add DoubleClickListener for the viewer.
        viewer.addDoubleClickListener(this);

        // Make the selection available to other Views.
        getSite().setSelectionProvider(viewer);
    }

    private void createColumns(Composite parent) {
        // First column is for the Name.
        TableViewerColumn column =
            createTableViewerColumn(
                TITLES[NAME_COLUMN], DEFAULT_WIDTH, NAME_COLUMN);
        column.setLabelProvider(new CellLabelProvider() {

            private static final long serialVersionUID = -8940575743109974080L;

            @Override
            public void update(ViewerCell cell) {
                Device device = (Device) cell.getElement();

                // Set text for cell
                cell.setText(device.getName());

                // Set image for cell
                cell.setImage(createImage(device.getIcon()));
            }
        });

        // Second column is for the App module
        column =
            createTableViewerColumn(
                TITLES[APP_MODULE_COLUMN], DEFAULT_WIDTH, APP_MODULE_COLUMN);
        column.setLabelProvider(new CellLabelProvider() {

            private static final long serialVersionUID = 406059287858697720L;

            @Override
            public void update(ViewerCell cell) {
                Device device = (Device) cell.getElement();
                cell.setText(device.getAppModule());
            }
        });

        // Third column is for the Device type
        column =
            createTableViewerColumn(
                TITLES[DEVICE_TYPE_COLUMN], DEFAULT_WIDTH, DEVICE_TYPE_COLUMN);
        column.setLabelProvider(new CellLabelProvider() {

            private static final long serialVersionUID = -9169642429241436682L;

            @Override
            public void update(ViewerCell cell) {
                Device device = (Device) cell.getElement();
                cell.setText(device.getDeviceType());
            }
        });

        // Fourth column is for the Physical location
        column =
            createTableViewerColumn(
                TITLES[PHYSICAL_LOCATION_COLUMN], DEFAULT_WIDTH,
                PHYSICAL_LOCATION_COLUMN);
        column.setLabelProvider(new CellLabelProvider() {

            private static final long serialVersionUID = -8236537062078809905L;

            @Override
            public void update(ViewerCell cell) {
                Device device = (Device) cell.getElement();
                cell.setText(device.getPhysicalLocation());
            }
        });

        // Fifth column is for the Manufacture
        column =
            createTableViewerColumn(
                TITLES[MANUFACTURE_COLUMN], DEFAULT_WIDTH, MANUFACTURE_COLUMN);
        column.setLabelProvider(new CellLabelProvider() {

            private static final long serialVersionUID = 7303645075256781731L;

            @Override
            public void update(ViewerCell cell) {
                Device device = (Device) cell.getElement();
                cell.setText(device.getManufacturer());
            }
        });
    }

    private TableViewerColumn createTableViewerColumn(
        String title, int width, int columnNumber) {
        TableViewerColumn viewerColumn =
            new TableViewerColumn(viewer, SWT.NONE);
        TableColumn column = viewerColumn.getColumn();
        column.setText(title);
        column.setWidth(width);
        column.setResizable(true);
        column.setMoveable(true);
        column.addSelectionListener(getSelectionAdapter(column, columnNumber));
        return viewerColumn;
    }

    private SelectionListener getSelectionAdapter(
        final TableColumn column, final int columnNumber) {
        SelectionAdapter selectionAdapter = new SelectionAdapter() {

            private static final long serialVersionUID = 4234122934768663966L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                comparator.setColumn(columnNumber);
                int direction = comparator.getDirection();
                viewer.getTable().setSortDirection(direction);
                viewer.getTable().setSortColumn(column);
                viewer.refresh();
            }
        };
        return selectionAdapter;
    }

    private Image createImage(Icon icon) {
        Image image = null;
        image = Utils.createImage(icon.getPluginId(), icon.getImageFilePath());
        return image;
    }

    private void createSelectionListener() {
        // Get current window.
        IWorkbenchWindow window =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        // Get selection service.
        ISelectionService selectionService = window.getSelectionService();

        // Add selection listener.
        selectionService.addSelectionListener(
            NavigationViewPart.ID, new ISelectionListener() {

                @Override
                public void selectionChanged(
                    IWorkbenchPart part, ISelection selection) {
                    IStructuredSelection sselection =
                        (IStructuredSelection) selection;
                    Object firstElement = sselection.getFirstElement();
                    fillDataToTable(firstElement);
                }
            });
    }

    private void fillDataToTable(Object firstElement) {
        if (firstElement != null && firstElement instanceof Version) {
            List<Device> devices = ((Version) firstElement).getDevices();

            // Set the content for the Viewer,
            // setInput will call getElements in the ContentProvider.
            viewer.setInput(devices);

        } else {
            viewer.getTable().removeAll();
        }
    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
        IWorkbenchWindow window =
            PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        String secondaryId = String.valueOf((new Date()).getTime());
        try {
            ISelection selection = viewer.getSelection();
            IStructuredSelection sselection = (IStructuredSelection) selection;
            Object firstObject = sselection.getFirstElement();
            if (firstObject instanceof Device) {
                window.getActivePage().showView(
                    EditDeviceViewPart.ID, secondaryId,
                    IWorkbenchPage.VIEW_ACTIVATE);
                window
                    .getActivePage()
                    .findViewReference(EditDeviceViewPart.ID, secondaryId)
                    .getView(true);

                EditDeviceViewPart part =
                    (EditDeviceViewPart) window
                        .getActivePage()
                        .findViewReference(EditDeviceViewPart.ID, secondaryId)
                        .getView(true);
                part.setData((Device) firstObject);
            }
        } catch (PartInitException e) {
            e.printStackTrace();
        }

    }
}