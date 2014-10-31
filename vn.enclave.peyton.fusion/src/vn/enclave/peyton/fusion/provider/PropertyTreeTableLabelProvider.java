package vn.enclave.peyton.fusion.provider;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.entity.Property;

public class PropertyTreeTableLabelProvider implements ITableLabelProvider {

    private static final long serialVersionUID = 5217708117312627071L;

    private static final int NAME_COLUMN = 0;

    private static final int VALUE_COLUMN = 1;

    private static final int MANDATORY_COLUMN = 2;

    private static final int DESCRIPTION_COLUMN = 3;

    @Override
    public String getColumnText(Object element, int columnIndex) {
        switch (columnIndex) {
            case NAME_COLUMN :
                if (element instanceof List<?>) {
                    return "General";
                }
                if (element instanceof Property) {
                    return ((Property) element).getName();
                }

            case VALUE_COLUMN :
                if (element instanceof Property) {
                    return ((Property) element).getValue();
                }

            case MANDATORY_COLUMN :
                if (element instanceof Property) {
                    return ((Property) element).isMandatory() ? "yes" : "no";
                }

            case DESCRIPTION_COLUMN :
                if (element instanceof Property) {
                    return ((Property) element).getDescription();
                }
        }
        return null;
    }

    @Override
    public Image getColumnImage(Object element, int columnIndex) {
        return null;
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
