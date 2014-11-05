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
import vn.enclave.peyton.fusion.entity.DeviceTemplate;
import vn.enclave.peyton.fusion.entity.PropertyTemplate;
import vn.enclave.peyton.fusion.provider.PropertyTemplateTreeContentProvider;
import vn.enclave.peyton.fusion.provider.PropertyTemplateTreeTableLabelProvider;

public class NewDevicePropertySection {
    private static final String[] TITLES = {"Name", "Value", "Mandatory", "Description"};
    private static final int DEFAULT_WIDTH = 120;
    private FormToolkit toolkit;
    private Section propertySection;
    private TreeViewer propertyTreeViewer;

    public Section getPropertySection() {
        return propertySection;
    }

    @SuppressWarnings("unchecked")
    public List<PropertyTemplate> getNewDeviceProperties() {
        return (List<PropertyTemplate>) propertyTreeViewer.getInput();
    }

    public NewDevicePropertySection(Composite parent) {
        createToolkitFrom(parent);
        createPropertySectionInside(parent);
    }

    private void createToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createPropertySectionInside(Composite parent) {
        propertySection = toolkit.createSection(parent, Section.TITLE_BAR);
        propertySection.setText("Configuration Properties");

        createTextClientControlInsidePropertySection();

        createPropertyTreeviewerInsidePropertySection();
    }

    private void createTextClientControlInsidePropertySection() {
        Composite textClientComposite = createTextClientCompositeInsidePropertySection();
        propertySection.setTextClient(textClientComposite);
    }

    private void createPropertyTreeviewerInsidePropertySection() {
        Tree propertyTemplateTree = createPropertyTreeInsidePropertySection();

        propertyTreeViewer = new TreeViewer(propertyTemplateTree);
        propertyTreeViewer.setContentProvider(new PropertyTemplateTreeContentProvider());
        propertyTreeViewer.setLabelProvider(new PropertyTemplateTreeTableLabelProvider());

        propertySection.setClient(propertyTemplateTree);
    }

    private Composite createTextClientCompositeInsidePropertySection() {
        Composite textClientComposite = toolkit.createComposite(propertySection);

        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        textClientComposite.setLayout(layout);
        textClientComposite.setBackgroundMode(SWT.INHERIT_NONE);
        textClientComposite.setBackground(null);

        createComboInside(textClientComposite);
        createToolbarInside(textClientComposite);

        return textClientComposite;
    }

    private Tree createPropertyTreeInsidePropertySection() {
        int style = SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;

        Tree propertyTree = new Tree(propertySection, style);
        propertyTree.setHeaderVisible(true);
        propertyTree.setLinesVisible(true);

        createAllTreeColumnsTo(propertyTree);

        return propertyTree;
    }

    private void createComboInside(Composite textClientComposite) {
        Combo combo = new Combo(textClientComposite, SWT.NONE);

        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        combo.setLayoutData(layoutData);
    }

    private void createToolbarInside(Composite textClientComposite) {
        ToolBar toolBar = new ToolBar(textClientComposite, SWT.FLAT);
        toolBar.setBackgroundMode(SWT.INHERIT_DEFAULT);

        createToolItemInside(toolBar);
    }

    private void createAllTreeColumnsTo(Tree propertyTree) {
        for (String title : TITLES) {
            TreeColumn treeColumn = createTreeColumnTo(propertyTree);
            treeColumn.setText(title);
        }
    }

    private void createToolItemInside(ToolBar toolBar) {
        ToolItem addPropertyToolItem = new ToolItem(toolBar, SWT.NONE);
        addPropertyToolItem.setImage(Constant.IMAGE_NODE_ADD);
        ToolItem deletePropertyToolItem = new ToolItem(toolBar, SWT.NONE);
        deletePropertyToolItem.setImage(Constant.IMAGE_NODE_DELETE);
    }

    private TreeColumn createTreeColumnTo(Tree propertyTemplateTree) {
        TreeColumn treeColumn = new TreeColumn(propertyTemplateTree, SWT.NONE);
        treeColumn.setWidth(DEFAULT_WIDTH);

        return treeColumn;
    }

    public void populatePropertyTreeViewerFrom(DeviceTemplate deviceTemplate) {
        List<PropertyTemplate> propertyTemplates = deviceTemplate.getPropertyTemplates();
        propertyTreeViewer.setInput(propertyTemplates);
    }

}
