package vn.enclave.peyton.fusion.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.comparator.DeviceTableViewerComparator;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.filter.DeviceFilter;
import vn.enclave.peyton.fusion.provider.DeviceTableLabelProvider;

public class DeviceTableViewPart extends ViewPart implements IDoubleClickListener {
    public static final String ID = "vn.enclave.peyton.fusion.view.deviceTableViewPart";
    private static final int DEFAULT_WIDTH = 120;
    private static final int DOWN_OFFSET = 1;
    private static final int UP_OFFSET = -1;
    private static final String[] TITLES = {"Name", "App Module", "Device Type", "Physical Location", "Manufacturer"};
    private Table deviceTable;
    private TableViewer deviceTableViewer;
    private DeviceTableViewerComparator deviceTableComparator = new DeviceTableViewerComparator();
    private Label filterLbl;
    private Label finderLbl;
    private Text filterTxt;
    private Text finderTxt;
    private Button clearBtn;
    private Button upBtn;
    private Button downBtn;
    private DeviceFilter deviceFilter = new DeviceFilter();
    private IWorkbenchPage activePage;

    @Override
    public void createPartControl(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginRight = -5;
        layout.marginLeft = -5;
        layout.horizontalSpacing = 0;
        parent.setLayout(layout);

        createActivePage();

        createToolbarCompositeInside(parent);
        createFilterAndFinderCompositeInside(parent);
        createDeviceTableCompositeInside(parent);
    }

    private void createActivePage() {
        IWorkbenchWindow window = getSite().getWorkbenchWindow();
        activePage = window.getActivePage();
    }

    private void createToolbarCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite toolbarComposite = new Composite(parent, SWT.NONE);
        toolbarComposite.setLayout(layout);
        toolbarComposite.setLayoutData(layoutData);

        createToolbarTo(toolbarComposite);
    }

    private void createFilterAndFinderCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(2, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite filterAndFinderComposite = new Composite(parent, SWT.NONE);
        filterAndFinderComposite.setLayout(layout);
        filterAndFinderComposite.setLayoutData(layoutData);

        createFilterCompositeInside(filterAndFinderComposite);
        createFinderCompositeInside(filterAndFinderComposite);
    }

    private void createDeviceTableCompositeInside(Composite parent) {
        GridLayout layout = new GridLayout(1, false);
        layout.marginTop = -5;
        layout.marginRight = -5;
        layout.marginBottom = -5;
        layout.marginLeft = -5;
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite deviceTableComposite = new Composite(parent, SWT.NONE);
        deviceTableComposite.setLayout(layout);
        deviceTableComposite.setLayoutData(layoutData);

        createDeviceTableViewerInside(deviceTableComposite);
    }

    private void createToolbarTo(Composite toolbarComposite) {
        GridData layoutData = new GridData(SWT.RIGHT, SWT.NONE, true, false);
        ToolBar toolBar = new ToolBar(toolbarComposite, SWT.FLAT);
        toolBar.setLayoutData(layoutData);

        createAllToolItemsTo(toolBar);
    }

    private void createFilterCompositeInside(Composite filterAndFinderComposite) {
        GridLayout layout = new GridLayout(3, false);
        layout.marginRight = 20;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite filterComposite = new Composite(filterAndFinderComposite, SWT.NONE);
        filterComposite.setLayout(layout);
        filterComposite.setLayoutData(layoutData);

        createFilterControlsInside(filterComposite);
    }

    private void createFinderCompositeInside(Composite filterAndFinderComposite) {
        GridLayout layout = new GridLayout(4, false);
        layout.marginLeft = 20;
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Composite finderComposite = new Composite(filterAndFinderComposite, SWT.NONE);
        finderComposite.setLayout(layout);
        finderComposite.setLayoutData(layoutData);

        createFinderControlsInside(finderComposite);
    }

    private void createDeviceTableViewerInside(Composite deviceTableComposite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER;
        deviceTable = new Table(deviceTableComposite, style);
        deviceTable.setHeaderVisible(true);
        deviceTable.setLinesVisible(true);
        deviceTable.setLayoutData(layoutData);

        deviceTableViewer = new TableViewer(deviceTable);
        deviceTableViewer.setContentProvider(ArrayContentProvider.getInstance());
        deviceTableViewer.setLabelProvider(new DeviceTableLabelProvider());
        deviceTableViewer.setComparator(deviceTableComparator);
        deviceTableViewer.addFilter(deviceFilter);
        deviceTableViewer.addDoubleClickListener(this);

        getSite().setSelectionProvider(deviceTableViewer);

        createAllColumnsToDeviceTableViewer();
    }

    private void createAllToolItemsTo(ToolBar toolBar) {
        ToolItem refreshToolItem = createToolItemTo(toolBar);
        refreshToolItem.setImage(Constant.IMAGE_REFRESH);

        createSeparatorTo(toolBar);

        ToolItem addToolItem = createToolItemTo(toolBar);
        addToolItem.setImage(Constant.IMAGE_ADD);

        ToolItem copyToolItem = createToolItemTo(toolBar);
        copyToolItem.setImage(Constant.IMAGE_COPY);

        ToolItem deleteToolItem = createToolItemTo(toolBar);
        deleteToolItem.setImage(Constant.IMAGE_DELETE);

        createSeparatorTo(toolBar);

        ToolItem importDeviceToolItem = createToolItemTo(toolBar);
        importDeviceToolItem.setImage(Constant.IMAGE_IMPORT_DEVICES);

        ToolItem importZWaveToolItem = createToolItemTo(toolBar);
        importZWaveToolItem.setImage(Constant.IMAGE_IMPORT_ZWAVE);

        ToolItem templateChildToolItem = createToolItemTo(toolBar);
        templateChildToolItem.setImage(Constant.IMAGE_TEMPLATE_CHILD);

        ToolItem exportToolItem = createToolItemTo(toolBar);
        exportToolItem.setImage(Constant.IMAGE_EXPORT);

        createSeparatorTo(toolBar);

        ToolItem systemToolItem = createToolItemTo(toolBar);
        systemToolItem.setImage(Constant.IMAGE_SYSTEM);

        ToolItem expandToolItem = createToolItemTo(toolBar);
        expandToolItem.setImage(Constant.IMAGE_EXPAND);

        ToolItem collapseToolItem = createToolItemTo(toolBar);
        collapseToolItem.setImage(Constant.IMAGE_COLLAPSE);

        createSeparatorTo(toolBar);

        ToolItem deviceChangedToolItem = createToolItemTo(toolBar);
        deviceChangedToolItem.setImage(Constant.IMAGE_DEVICE_CHANGED);

        ToolItem requestOnlineStatusToolItem = createToolItemTo(toolBar);
        requestOnlineStatusToolItem.setImage(Constant.IMAGE_REQUEST_ONLINE_STATUS);
    }

    private void createFilterControlsInside(Composite filterComposite) {
        filterLbl = new Label(filterComposite, SWT.NONE);
        filterLbl.setText("Filter:");

        filterTxt = createText(filterComposite);
        filterTxt.addModifyListener(createModifyListenerToFilterText());

        clearBtn = createButton(filterComposite);
        clearBtn.setImage(Constant.IMAGE_EDIT);
        clearBtn.setEnabled(false);
        clearBtn.addSelectionListener(createSelectionAdapterToClearButton());
    }

    private void createFinderControlsInside(Composite finderComposite) {
        finderLbl = new Label(finderComposite, SWT.NONE);
        finderLbl.setText("Find:");

        finderTxt = createText(finderComposite);
        finderTxt.addModifyListener(createModifyListenerToFinderText());
        finderTxt.addKeyListener(createKeyAdapterToFinderText());

        upBtn = createButton(finderComposite);
        upBtn.setImage(Constant.IMAGE_ARROW_UP);
        upBtn.addSelectionListener(createSelectionAdapterToUpButton());

        downBtn = createButton(finderComposite);
        downBtn.setImage(Constant.IMAGE_ARROW_DOWN);
        downBtn.addSelectionListener(createSelectionAdapterToDownButton());
    }

    private ToolItem createToolItemTo(ToolBar toolBar) {
        ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);
        return toolItem;
    }

    private void createSeparatorTo(ToolBar toolBar) {
        new ToolItem(toolBar, SWT.SEPARATOR);
    }

    private Text createText(Composite composite) {
        GridData layoutData = new GridData(SWT.FILL, SWT.NONE, true, false);
        Text text = new Text(composite, SWT.BORDER);
        text.setLayoutData(layoutData);
        return text;
    }

    private ModifyListener createModifyListenerToFilterText() {
        return new ModifyListener() {
            private static final long serialVersionUID = 4906617449904102933L;

            @Override
            public void modifyText(ModifyEvent event) {
                deviceFilter.setFilterString(filterTxt.getText());
                deviceTableViewer.refresh();
                clearBtn.setEnabled(isEnableClearButton());
            }
        };
    }

    private boolean isEnableClearButton() {
        return filterTxt.getText() != null & filterTxt.getText().length() != 0;
    }

    private Button createButton(Composite composite) {
        Button button = new Button(composite, SWT.PUSH);
        return button;
    }

    private SelectionAdapter createSelectionAdapterToClearButton() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 8727982726761507228L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                filterTxt.setText("");
                clearBtn.setEnabled(false);
            }
        };
    }

    private ModifyListener createModifyListenerToFinderText() {
        return new ModifyListener() {
            private static final long serialVersionUID = -1718429326062300859L;

            @Override
            public void modifyText(ModifyEvent event) {
                findDeviceBy(finderTxt.getText());
            }
        };
    }

    private KeyAdapter createKeyAdapterToFinderText() {
        return new KeyAdapter() {
            private static final long serialVersionUID = -5866801124911784172L;

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    findDeviceBy(finderTxt.getText());
                }
            }
        };
    }

    private SelectionAdapter createSelectionAdapterToUpButton() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = -5886805186732591167L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                findDeviceBy(finderTxt.getText(), UP_OFFSET);
            }
        };
    }

    private SelectionAdapter createSelectionAdapterToDownButton() {
        return new SelectionAdapter() {
            private static final long serialVersionUID = -3866766296386752019L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                findDeviceBy(finderTxt.getText(), DOWN_OFFSET);
            }
        };
    }

    private void findDeviceBy(String findText) {
        int matchedRowIndex = findTheFirstMatchedRow(findText);

        if (findText.length() == 0 || findText == null || matchedRowIndex == -1) {
            deviceTable.deselectAll();
        }
    }

    private int findTheFirstMatchedRow(String findText) {
        TableItem[] rows = deviceTable.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = deviceTable.getColumnCount();
        for (int rowIndex = 0; rowIndex < numberOfRow; rowIndex++) {
            for (int columnIndex = 0; columnIndex < nubmerOfColumn; columnIndex++) {
                String cellValue = rows[rowIndex].getText(columnIndex);
                if (isCellValueContainsFindText(cellValue, findText)) {
                    deviceTable.select(rowIndex);
                    return rowIndex;
                }
            }
        }
        return -1;
    }

    private boolean isCellValueContainsFindText(String cellValue, String findText) {
        return cellValue.toLowerCase().contains(findText.trim().toLowerCase());
    }

    //TODO: reorganize
    private void findDeviceBy(String findText, int offset) {
        TableItem[] rows = deviceTable.getItems();
        int numberOfRow = rows.length;
        int nubmerOfColumn = deviceTable.getColumnCount();
        ArrayList<Integer> focusIndexes = new ArrayList<Integer>();
        if (numberOfRow != 0) {
            // Get indexes of match rows.
            for (int rowIndex = 0; rowIndex < numberOfRow; rowIndex++) {
                for (int columnIndex = 0; columnIndex < nubmerOfColumn; columnIndex++) {
                    String cellValue = rows[rowIndex].getText(columnIndex);
                    if (isCellValueContainsFindText(cellValue, findText)) {
                        focusIndexes.add(rowIndex);
                        break;
                    }
                }
            }
            int selectionIndex = deviceTable.getSelectionIndex();
            int index = focusIndexes.indexOf(selectionIndex) + offset;
            if (index < 0) {
                index = focusIndexes.size() - 1;
            } else if (index > focusIndexes.size() - 1) {
                index = 0;
            }
            deviceTable.select(focusIndexes.get(index));
        }
    }

    private void createAllColumnsToDeviceTableViewer() {
        for (int columnIndex = 0; columnIndex < TITLES.length; columnIndex++) {
            createColumnHas(columnIndex);
        }
    }

    private TableColumn createColumnHas(int columnIndex) {
        TableColumn tableColumn = new TableColumn(deviceTable, SWT.NONE);
        tableColumn.setText(TITLES[columnIndex]);
        tableColumn.setWidth(DEFAULT_WIDTH);
        tableColumn.setResizable(true);
        tableColumn.setMoveable(true);
        tableColumn.addSelectionListener(createSelectionAdapterToColumnHas(columnIndex));
        return tableColumn;
    }

    private SelectionListener createSelectionAdapterToColumnHas(final int columnIndex) {
        return new SelectionAdapter() {
            private static final long serialVersionUID = 4234122934768663966L;

            @Override
            public void widgetSelected(SelectionEvent e) {
                TableColumn tableColumn = deviceTable.getColumn(columnIndex);
                deviceTableComparator.setColumnIndex(columnIndex);
                int direction = deviceTableComparator.getDirection();
                deviceTable.setSortDirection(direction);
                deviceTable.setSortColumn(tableColumn);

                deviceTableViewer.refresh();
            }
        };
    }

    @Override
    public void doubleClick(DoubleClickEvent event) {
        try {
            ISelection selection = event.getSelection();
            IStructuredSelection sselection = (IStructuredSelection) selection;
            Object firstObject = sselection.getFirstElement();
            if (firstObject instanceof Device) {
                Device device = (Device) firstObject;
                showModifyingDeviceViewPartAndPopulateFrom(device);
            }
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }

    private void showModifyingDeviceViewPartAndPopulateFrom(Device device) throws PartInitException {
        String viewId = ModifyingDeviceViewPart.ID;
        String secondaryId = String.valueOf(device.getId());
        int mode = IWorkbenchPage.VIEW_ACTIVATE;
        IViewPart viewPart = null;
        if (isViewPartOpened(viewId, secondaryId)) {
            viewPart = activePage.showView(viewId, secondaryId, mode);
            ((ModifyingDeviceViewPart) viewPart).setData(device);
        } else {
            viewPart = activePage.showView(viewId, secondaryId, mode);
        }
    }

    private boolean isViewPartOpened(String viewId, String secondaryId) {
        return activePage.findViewReference(viewId, secondaryId) == null;
    }

    public void refreshDeviceTableViewer() {
        deviceTableViewer.refresh();
    }

    public void populateDeviceTableFrom(Version selectedVersion) {
        List<Device> devices = ((Version) selectedVersion).getDevices();
        deviceTableViewer.setInput(devices);
    }

    public void clearRowsOnDeviceTable() {
        deviceTableViewer.setInput(null);
    }

    @Override
    public void setFocus() {
    }
}