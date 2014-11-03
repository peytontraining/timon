package vn.enclave.peyton.fusion.provider;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;

public class PlanTreeContentProvider implements ITreeContentProvider {

    private static final long serialVersionUID = -2337284671702127401L;

    @Override
    public void dispose() {
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    @Override
    public Object[] getElements(Object inputElement) {
        return ((List<?>) inputElement).toArray();
    }

    @Override
    public Object[] getChildren(Object parentElement) {
        if (parentElement != null && parentElement instanceof Plan) {
            return ((Plan) parentElement).getProjects().toArray();
        } else if (parentElement != null && parentElement instanceof Project) {
            return ((Project) parentElement).getVersions().toArray();
        }
        
        return null;
    }

    @Override
    public Object getParent(Object element) {
        if (element != null && element instanceof Project) {
            return ((Project) element).getPlan();
        } else if (element != null && element instanceof Version) {
            return ((Version) element).getProject();
        }
        return null;
    }

    @Override
    public boolean hasChildren(Object element) {
        if (element != null && element instanceof Plan) {
            return ((Plan) element).getProjects().size() > 0;
        } else if (element != null && element instanceof Project) {
            return ((Project) element).getVersions().size() > 0;
        }
        return false;
    }

}