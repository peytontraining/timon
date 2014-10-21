package vn.enclave.peyton.fusion.provider;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.DeviceType;
import vn.enclave.peyton.fusion.entity.Module;

public class TemplateTreeContentProvider implements ITreeContentProvider {

    private static final long serialVersionUID = -833288932787462667L;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof List) {
            return ((List<?>) parentElement).toArray();
        }
        if (parentElement instanceof Module) {
            return ((Module) parentElement).getDeviceTypes().toArray();
        }
        if (parentElement instanceof DeviceType) {
            return ((DeviceType) parentElement).getDeviceTemplates().toArray();
        }
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof DeviceType) {
            return ((DeviceType) element).getModule();
        }
        if (element instanceof DeviceTemplate) {
            return ((DeviceTemplate) element).getDeviceType();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof List) {
            return ((List<?>) element).size() > 0;
        }
        if (element instanceof Module) {
            return ((Module) element).getDeviceTypes().size() > 0;
        }
        if (element instanceof DeviceType) {
            return ((DeviceType) element).getDeviceTemplates().size() > 0;
        }
        return false;
    }

}