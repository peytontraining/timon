package vn.enclave.peyton.fusion.provider;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import vn.enclave.peyton.fusion.entity.Property;

public class PropertyTreeContentProvider implements ITreeContentProvider {

    private static final long serialVersionUID = -3279693486055653665L;

    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof List<?>) {
            return ((List<?>) parentElement).toArray();
        }
        return new Object[0];
    }

    @Override
    public Object getParent(Object element) {
        if (element instanceof Property) {
            return ((Property) element).getDevice();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element instanceof List<?>) {
            return ((List<?>) element).size() > 0;
        }
        return false;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
