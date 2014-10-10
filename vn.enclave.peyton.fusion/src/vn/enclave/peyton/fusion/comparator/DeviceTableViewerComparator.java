package vn.enclave.peyton.fusion.comparator;

import static vn.enclave.peyton.fusion.view.DeviceTableViewPart.*;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import vn.enclave.peyton.fusion.entity.Device;

public class DeviceTableViewerComparator extends ViewerComparator {

    private static final long serialVersionUID = 6467573382782190246L;
    private static final int DESCENDING = 1;

    private int propertyIndex;
    private int direction = DESCENDING;

    public DeviceTableViewerComparator() {
        this.propertyIndex = 0;
        direction = DESCENDING;
    }

    public int getDirection() {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }

    public void setColumn(int column) {
        if (this.propertyIndex == column) {
            direction = 1 - direction;
        } else {
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        Device d1 = (Device) e1;
        Device d2 = (Device) e2;
        int rc = 0;
        switch (propertyIndex) {
            case NAME_COLUMN:
                rc = d1.getName().compareTo(d2.getName());
                break;

            case APP_MODULE_COLUMN:
                rc = d1.getAppModule().compareTo(d2.getAppModule());
                break;

            case DEVICE_TYPE_COLUMN:
                rc = d1.getDeviceType().compareTo(d2.getDeviceType());
                break;

            case PHYSICAL_LOCATION_COLUMN:
                rc = d1.getPhysicalLocation()
                    .compareTo(d2.getPhysicalLocation());
                break;

            case MANUFACTURE_COLUMN:
                rc = d1.getManufacture().compareTo(d2.getManufacture());
                break;
        }

        // If descending order, flip the direction.
        if (direction == DESCENDING) {
            rc = -rc;
        }
        return rc;
    }

}