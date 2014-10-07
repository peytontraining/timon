package vn.enclave.peyton.fusion.view.filter;

import org.eclipse.jface.viewers.*;

public class DeviceFilter extends ViewerFilter {

    private static final long serialVersionUID = 5232662280687240217L;

    private String filterString;

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {

        if (filterString == null || filterString.length() == 0) {
            return true;
        }

        Object[] device = (Object[]) element;
        for (int i = 0; i < device.length; i++) {
            if (device[i] != null
                    && device[i].toString().toLowerCase()
                            .contains(filterString.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}