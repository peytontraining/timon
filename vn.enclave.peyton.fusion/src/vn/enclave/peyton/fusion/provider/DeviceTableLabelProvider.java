package vn.enclave.peyton.fusion.provider;

import static vn.enclave.peyton.fusion.common.Constant.COLUMN_NAME;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_APP_MODULE;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_DEVICE_TYPE;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_PHYSICAL_LOCATION;
import static vn.enclave.peyton.fusion.common.Constant.COLUMN_MANUFACTURER;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.Device;

public class DeviceTableLabelProvider implements ITableLabelProvider {
    private static final long serialVersionUID = 9049929314293959258L;

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        if (isNameColumn(columnIndex)) {
            Device device = (Device) element;
            return Utils.createImageFromIcon(device.getIcon());
        }
        return null;
    }

    private boolean isNameColumn(int columnIndex) {
        return columnIndex == COLUMN_NAME;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        Device device = (Device) element;
        if (isNameColumn(columnIndex)) {
            return device.getName();
        }
        if (isAppModuleColumn(columnIndex)) {
            return device.getAppModule();
        }
        if (isDeviceTypeColumn(columnIndex)) {
            return device.getDeviceType();
        }
        if (isPhysicalLocationColumn(columnIndex)) {
            return device.getPhysicalLocation();
        }
        if (isManufacturerColumn(columnIndex)) {
            return device.getManufacturer();
        }
        return null;
    }

    private boolean isAppModuleColumn(int columnIndex) {
        return columnIndex == COLUMN_APP_MODULE;
    }

    private boolean isDeviceTypeColumn(int columnIndex) {
        return columnIndex == COLUMN_DEVICE_TYPE;
    }

    private boolean isPhysicalLocationColumn(int columnIndex) {
        return columnIndex == COLUMN_PHYSICAL_LOCATION;
    }

    private boolean isManufacturerColumn(int columnIndex) {
        return columnIndex == COLUMN_MANUFACTURER;
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
    }
}
