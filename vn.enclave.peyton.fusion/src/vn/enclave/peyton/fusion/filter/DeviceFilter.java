package vn.enclave.peyton.fusion.filter;

import org.eclipse.jface.viewers.*;

import vn.enclave.peyton.fusion.entity.Device;


public class DeviceFilter extends ViewerFilter {

    private static final long serialVersionUID = 5232662280687240217L;

    private String filterString;

    public void setFilterString(String filterString) {
        this.filterString = filterString.toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (filterString == null || filterString.length() == 0) {
            return true;
        }

        Device device = (Device) element;
        String name = device.getName().toLowerCase();
        String appModule = device.getAppModule().toLowerCase();
        String deviceType = device.getDeviceType().toLowerCase();
        String physicalLocation = device.getPhysicalLocation().toLowerCase();
        String manufacture = device.getManufacture().toLowerCase();

        if (name != null && name.contains(filterString)) {
            return true;
        }

        if (appModule != null && appModule.contains(filterString)) {
            return true;
        }

        if (deviceType != null && deviceType.contains(filterString)) {
            return true;
        }

        if (physicalLocation != null &&
            physicalLocation.contains(filterString)) {
            return true;
        }

        if (manufacture != null && manufacture.contains(filterString)) {
            return true;
        }
        return false;
    }
}