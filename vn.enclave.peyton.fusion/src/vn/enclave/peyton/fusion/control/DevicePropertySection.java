package vn.enclave.peyton.fusion.control;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Property;
import vn.enclave.peyton.fusion.provider.PropertyTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PropertyTreeTableLabelProvider;

public class DevicePropertySection {
    private static final String[] TITLES = {"Name", "Value", "Mandatory", "Description"};
    private static final int DEFAULT_WIDTH = 120;
    private Section section;

    private TreeViewer treeViewer;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public TreeViewer getTreeViewer() {
        return treeViewer;
    }

    public void setTreeViewer(TreeViewer treeViewer) {
        this.treeViewer = treeViewer;
    }

    public DevicePropertySection(Composite parent) {
        createSectionInside(parent);
    }

    private void createSectionInside(Composite parent) {
        FormToolkit toolkit = new FormToolkit(parent.getDisplay());
        section = toolkit.createSection(parent, Section.TITLE_BAR);
        section.setText("Configuration Properties");

        createTextClientControl(toolkit);

        treeViewer = createTreeViewer(section);

        section.setClient(treeViewer.getTree());
    }

    private void createTextClientControl(FormToolkit toolkit) {
        Composite textClientControl = createSectionToolbarComposite(toolkit);
        section.setTextClient(textClientControl);
    }

    private Composite createSectionToolbarComposite(FormToolkit toolkit) {
        Composite composite = toolkit.createComposite(section);
        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        composite.setLayout(layout);
        composite.setBackgroundMode(SWT.INHERIT_NONE);
        composite.setBackground(null);

        createComboInside(composite);
        createToolbarInside(composite);

        return composite;
    }

    private void createComboInside(Composite composite) {
        Combo combo = new Combo(composite, SWT.NONE);
        
        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        combo.setLayoutData(layoutData);
    }

    private void createToolbarInside(Composite parent) {
        ToolBar bar = new ToolBar(parent, SWT.FLAT);
        bar.setBackgroundMode(SWT.INHERIT_DEFAULT);

        createToolItem(bar);
    }

    private void createToolItem(ToolBar bar) {
        ToolItem addProperty = new ToolItem(bar, SWT.NONE);
        addProperty.setImage(Constant.IMAGE_NODE_ADD);
        ToolItem deleteProperty = new ToolItem(bar, SWT.NONE);
        deleteProperty.setImage(Constant.IMAGE_NODE_DELETE);
    }

    private TreeViewer createTreeViewer(Composite parent) {
        Tree tree = createTree(parent);

        TreeViewer treeViewer = new TreeViewer(tree);
        treeViewer.setContentProvider(new PropertyTreeContentProvider());
        treeViewer.setLabelProvider(new PropertyTreeTableLabelProvider());
        return treeViewer;
    }

    private Tree createTree(Composite parent) {
        int style = SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
        Tree tree = new Tree(parent, style);
        tree.setHeaderVisible(true);
        tree.setLinesVisible(true);

        createTreeColumns(tree);

        return tree;
    }

    private void createTreeColumns(Tree parent) {
        for (String title : TITLES) {
            createTreeColumn(parent, title);
        }
    }

    private void createTreeColumn(Tree parent, String title) {
        TreeColumn column = new TreeColumn(parent, SWT.NONE);
        column.setText(title);
        column.setWidth(DEFAULT_WIDTH);
    }

    public void fillInTree(List<Property> properties) {
        treeViewer.setInput(properties);
    }

}
