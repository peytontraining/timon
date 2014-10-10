package vn.enclave.peyton.fusion.common;

import java.util.ArrayList;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public abstract class AbstractTreeViewPart extends ViewPart {

    protected TreeViewer viewer;

    public class TreeObject {
        private String name;
        private Object data;
        private int level;
        private TreeParent parent;

        public TreeObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setParent(TreeParent parent) {
            this.parent = parent;
        }

        public TreeParent getParent() {
            return parent;
        }

        @Override
        public String toString() {
            return getName();
        }
    }

    public class TreeParent extends TreeObject {

        private ArrayList<TreeObject> children;

        public TreeParent(String name) {
            super(name);
            children = new ArrayList<TreeObject>();
        }

        public void addChild(TreeObject child) {
            children.add(child);
            child.setParent(this);
        }

        public void remoreChild(TreeObject child) {
            children.remove(child);
            child.setParent(null);
        }

        public TreeObject[] getChildren() {
            return children.toArray(new TreeObject[children.size()]);
        }

        public boolean hasChildren() {
            return children.size() > 0;
        }
    }

    protected class ViewContentProvider implements IStructuredContentProvider,
        ITreeContentProvider {

        private static final long serialVersionUID = -1078242598650066595L;

        public ViewContentProvider() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public void 
            inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof TreeParent) {
                return ((TreeParent) parentElement).getChildren();
            }
            return new Object[0];
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof TreeObject) {
                return ((TreeObject) element).getParent();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof TreeParent) {
                return ((TreeParent) element).hasChildren();
            }
            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

    }

    /**
     * Sample code:<br>
     * treeViewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL |
     * SWT.BORDER);<br>
     * treeViewer.setContentProvider(new ViewContentProvider());<br>
     * treeViewer.setLabelProvider(new LabelProvider());<br>
     * treeViewer.setInput(this);<br>
     */
    @Override
    public abstract void createPartControl(Composite parent);

    @Override
    public void setFocus() {
        viewer.getTree().setFocus();
    }
}