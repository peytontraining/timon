package vn.enclave.peyton.fusion.filter;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

public class PlanFilter extends FilteredTree {

    private static final long serialVersionUID = -1278794600510832349L;

    public PlanFilter(
        Composite parent, int treeStyle, PatternFilter filter,
        boolean useNewLook) {
        super(parent, treeStyle, filter, useNewLook);
        showFilterControls = false;
    }

    @Override
    protected long getRefreshJobDelay() {
        return 200;
    }

    @Override
    protected void updateToolbar(boolean visible) {
        super.updateToolbar(visible);
    }
}
