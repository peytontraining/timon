package vn.enclave.peyton.fusion.provider;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.DeviceType;
import vn.enclave.peyton.fusion.entity.Module;

public class TemplateTreeLabelProvider implements ITableLabelProvider {

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
            if (element instanceof Module) {
                if (((Module) element).getName().intern() == Constant.CCTV) {
                    return Constant.IMAGE_CCTV;
                }
            }
            if (element instanceof DeviceType) {
                if (((DeviceType) element).getModule().getName().intern() == Constant.CCTV) {
                    return Constant.IMAGE_CCTV;
                }
            }
            if (element instanceof DeviceTemplate) {
                if (((DeviceTemplate) element)
                    .getDeviceType().getModule().getName().intern() == Constant.CCTV) {
                    return Constant.IMAGE_CCTV;
                }
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
                    return ((DeviceTemplate) element).getManufacture();
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
