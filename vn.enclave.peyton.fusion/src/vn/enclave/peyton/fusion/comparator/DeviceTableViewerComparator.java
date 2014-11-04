package vn.enclave.peyton.fusion.comparator;

import static vn.enclave.peyton.fusion.common.Constant.COLUMN_NAME;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_APP_MODULE;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_DEVICE_TYPE;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_PHYSICAL_LOCATION;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_MANUFACTURER;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import vn.enclave.peyton.fusion.entity.Device;

public class DeviceTableViewerComparator extends ViewerComparator {

    private static final long serialVersionUID = 6467573382782190246L;
    private static final int DESCENDING = 1;

    private int columnIndex;
    private int direction = DESCENDING;

    public DeviceTableViewerComparator() {
        this.columnIndex = 0;
        direction = DESCENDING;
    }

    public int getDirection() {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }

    public void setColumnIndex(int columnIndex) {
        if (this.columnIndex == columnIndex) {
            direction = 1 - direction;
        } else {
            this.columnIndex = columnIndex;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Device d1 = (Device) e1;
        Device d2 = (Device) e2;
        int rc = 0;
        switch (columnIndex) {
            case COLUMN_NAME :
                rc = d1.getName().compareTo(d2.getName());
                break;

            case COLUMN_APP_MODULE :
                rc = d1.getAppModule().compareTo(d2.getAppModule());
                break;

            case COLUMN_DEVICE_TYPE :
                rc = d1.getDeviceType().compareTo(d2.getDeviceType());
                break;

            case COLUMN_PHYSICAL_LOCATION :
                rc = d1.getPhysicalLocation().compareTo(d2.getPhysicalLocation());
                break;

            case COLUMN_MANUFACTURER :
                rc = d1.getManufacturer().compareTo(d2.getManufacturer());
                break;
        }

        // If descending order, flip the direction.
        if (direction == DESCENDING) {
            rc = -rc;
        }
        return rc;
    }

}