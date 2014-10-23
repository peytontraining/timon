package vn.enclave.peyton.fusion.filter;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.DeviceType;
import vn.enclave.peyton.fusion.entity.Module;

public class TemplateFilter extends ViewerFilter {

    private static final long serialVersionUID = 2060753949720907937L;

    private String filterString;

    public void setFilterString(String filterString) {
        this.filterString = filterString.toLowerCase();
    }

    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        if (filterString == null || filterString.length() == 0) {
            return true;
        }

        String name;
        if (element instanceof Module) {
            name = ((Module) element).getName().toLowerCase();
            if (name != null && name.contains(filterString)) {
                return true;
            } else {
                List<DeviceType> types = ((Module) element).getDeviceTypes();
                if (types != null) {
                    for (DeviceType type : types) {
                        if (select(viewer, element, type)) {
                            return true;
                        }
                    }
                }
            }
        } else if (element instanceof DeviceType) {
            name = ((DeviceType) element).getName().toLowerCase();
            if (name != null && name.contains(filterString)) {
                return true;
            } else {
                List<DeviceTemplate> templates =
                    ((DeviceType) element).getDeviceTemplates();
                if (templates != null) {
                    for (DeviceTemplate template : templates) {
                        if (select(viewer, element, template)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            name = ((DeviceTemplate) element).getName().toLowerCase();
            String lastModified =
                Utils.convertDate2String(
                    ((DeviceTemplate) element).getLastModified()).toLowerCase();
            String manufature =
                ((DeviceTemplate) element).getManufacturer().toLowerCase();
            String modelNumber =
                ((DeviceTemplate) element).getModelNumber().toLowerCase();
            String version =
                ((DeviceTemplate) element).getVersion().toLowerCase();
            if (name != null && name.contains(filterString)) {
                return true;
            }

            if (lastModified != null && lastModified.contains(filterString)) {
                return true;
            }

            if (manufature != null && manufature.contains(filterString)) {
                return true;
            }

            if (modelNumber != null && modelNumber.contains(filterString)) {
                return true;
            }

            if (version != null && version.contains(filterString)) {
                return true;
            }
        }
        return false;
    }

}
