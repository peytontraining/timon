package vn.enclave.peyton.fusion.provider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.DeviceType;
import vn.enclave.peyton.fusion.entity.Module;

public class TemplateTreeTableLabelProvider implements ITableLabelProvider {

    private static final int NAME_COLUMN = 0;

    private static final int LAST_MODIFIED_COLUMN = 1;

    private static final int MANUFACTURE_COLUMN = 2;

    private static final int MODEL_NUMBER_COLUMN = 3;

    private static final int VERSION_COLUMN = 4;

    private static final long serialVersionUID = -889220201270235236L;

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

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        if (columnIndex == NAME_COLUMN) {
            String pluginId = null;
            String imageFilePath = null;
            if (element instanceof Module) {
                pluginId = ((Module) element).getIcon().getPluginId();
                imageFilePath = ((Module) element).getIcon().getImageFilePath();
            }
            if (element instanceof DeviceType) {
                pluginId = ((DeviceType) element).getIcon().getPluginId();
                imageFilePath =
                    ((DeviceType) element).getIcon().getImageFilePath();
            }
            if (element instanceof DeviceTemplate) {
                pluginId = ((DeviceTemplate) element).getIcon().getPluginId();
                imageFilePath =
                    ((DeviceTemplate) element).getIcon().getImageFilePath();
            }
            if (pluginId != null && imageFilePath != null) {
                return Utils.createImage(pluginId, imageFilePath);
            }
        }
        return null;
    }

    @Override
    public String getColumnText(Object element, int columnIndex) {
        switch (columnIndex) {
            case NAME_COLUMN :
                if (element instanceof Module) {
                    return ((Module) element).getName();
                }
                if (element instanceof DeviceType) {
                    return ((DeviceType) element).getName();
                }
                if (element instanceof DeviceTemplate) {
                    return ((DeviceTemplate) element).getName();
                }

            case LAST_MODIFIED_COLUMN :
                if (element instanceof DeviceTemplate) {
                    return Utils.convertDate2String(((DeviceTemplate) element)
                        .getLastModified());
                }

            case MANUFACTURE_COLUMN :
                if (element instanceof DeviceTemplate) {
                    return ((DeviceTemplate) element).getManufacturer();
                }

            case MODEL_NUMBER_COLUMN :
                if (element instanceof DeviceTemplate) {
                    return ((DeviceTemplate) element).getModelNumber();
                }

            case VERSION_COLUMN :
                if (element instanceof DeviceTemplate) {
                    return ((DeviceTemplate) element).getVersion();
                }
        }
        return null;
    }

}
