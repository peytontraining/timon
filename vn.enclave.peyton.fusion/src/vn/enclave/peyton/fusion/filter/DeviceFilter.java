package vn.enclave.peyton.fusion.filter;

import org.eclipse.jface.viewers.*;

import vn.enclave.peyton.fusion.entity.Device;


public class DeviceFilter extends ViewerFilter {

    private static final long serialVersionUID = 5232662280687240217L;

    private String filterText;

    public void setFilterString(String filterString) {
        this.filterText = filterString.toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (filterText == null || filterText.isEmpty()) {
            return true;
        }

        Device device = (Device) element;
        String name = device.getName().toLowerCase();
        String appModule = device.getAppModule().toLowerCase();
        String deviceType = device.getDeviceType().toLowerCase();
        String physicalLocation = device.getPhysicalLocation().toLowerCase();
        String manufacture = device.getManufacturer().toLowerCase();

        if (name != null && name.contains(filterText)) {
            return true;
        }

        if (appModule != null && appModule.contains(filterText)) {
            return true;
        }

        if (deviceType != null && deviceType.contains(filterText)) {
            return true;
        }

        if (physicalLocation != null &&
            physicalLocation.contains(filterText)) {
            return true;
        }

        if (manufacture != null && manufacture.contains(filterText)) {
            return true;
        }
        return false;
    }
}