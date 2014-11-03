package vn.enclave.peyton.fusion.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import vn.enclave.peyton.fusion.common.Utils;
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.DeviceType;
import vn.enclave.peyton.fusion.entity.Module;

public class TemplateTreeLabelProvider extends LabelProvider {

    private static final long serialVersionUID = -889220201270235236L;

    @Override
    public Image getImage(Object element) {
        String pluginId = null;
        String imageFilePath = null;
        if (element instanceof Module) {
            pluginId = ((Module) element).getIcon().getPluginId();
            imageFilePath = ((Module) element).getIcon().getImageFilePath();
        }
        if (element instanceof DeviceType) {
            pluginId = ((DeviceType) element).getIcon().getPluginId();
            imageFilePath = ((DeviceType) element).getIcon().getImageFilePath();
        }
        if (element instanceof DeviceTemplate) {
            pluginId = ((DeviceTemplate) element).getIcon().getPluginId();
            imageFilePath =
                ((DeviceTemplate) element).getIcon().getImageFilePath();
        }
        if (pluginId != null && imageFilePath != null) {
            return Utils.createImageFromPluginIdAndImageFilePath(pluginId, imageFilePath);
        }
        return null;
    }

    @Override
    public String getText(Object element) {
        if (element instanceof Module) {
            return ((Module) element).getName();
        }
        if (element instanceof DeviceType) {
            return ((DeviceType) element).getName();
        }
        if (element instanceof DeviceTemplate) {
            return ((DeviceTemplate) element).getName();
        }
        return null;
    }

}