package vn.enclave.peyton.fusion.filter;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class TemplatePatternFilter extends PatternFilter {

    private static final long serialVersionUID = 1231583759555166983L;

    @Override
    protected boolean isLeafMatch(Viewer viewer, Object element) {
        TreeViewer treeViewer = (TreeViewer) viewer;
        int numberOfColumns = treeViewer.getTree().getColumnCount();
        ITableLabelProvider labelProvider =
            (ITableLabelProvider) treeViewer.getLabelProvider();
        boolean isMatch = false;
        for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
            String labelText =
                labelProvider.getColumnText(element, columnIndex);
            isMatch |= wordMatches(labelText);
        }
        return isMatch;
    }
}
