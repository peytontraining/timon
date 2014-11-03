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

public class DevicePropertyTemplateSection {

    private static final String[] TITLES = {"Name", "Value", "Mandatory", "Description"};
    private static final int DEFAULT_WIDTH = 120;
    private FormToolkit toolkit;
    private Section propertyTemplateSection;
    private TreeViewer propertyTemplateTreeViewer;

    public Section getPropertyTemplateSection() {
        return propertyTemplateSection;
    }

    public TreeViewer getPropertyTemplateTreeViewer() {
        return propertyTemplateTreeViewer;
    }

    public DevicePropertyTemplateSection(Composite parent) {
        createToolkitFrom(parent);
        createPropertyTemplateSectionInside(parent);
    }

    private void createToolkitFrom(Composite parent) {
        toolkit = new FormToolkit(parent.getDisplay());
    }

    private void createPropertyTemplateSectionInside(Composite parent) {
        propertyTemplateSection = toolkit.createSection(parent, Section.TITLE_BAR);
        propertyTemplateSection.setText("Configuration Properties");

        createTextClientControlInsidePropertyTemplateSection();

        createPropertyTemplateTreeviewerInsidePropertyTemplateSection();
    }

    private void createTextClientControlInsidePropertyTemplateSection() {
        Composite textClientComposite = createTextClientCompositeInsidePropertyTemplateSection();
        propertyTemplateSection.setTextClient(textClientComposite);
    }

    private void createPropertyTemplateTreeviewerInsidePropertyTemplateSection() {
        Tree propertyTemplateTree = createPropertyTreeInsidePropertyTemplateSection();

        propertyTemplateTreeViewer = new TreeViewer(propertyTemplateTree);
        propertyTemplateTreeViewer.setContentProvider(new PropertyTemplateTreeContentProvider());
        propertyTemplateTreeViewer.setLabelProvider(new PropertyTemplateTreeTableLabelProvider());

        propertyTemplateSection.setClient(propertyTemplateTree);
    }

    private Composite createTextClientCompositeInsidePropertyTemplateSection() {
        Composite textClientComposite = toolkit.createComposite(propertyTemplateSection);

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

    private Tree createPropertyTreeInsidePropertyTemplateSection() {
        int style = SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;

        Tree propertyTemplateTree = new Tree(propertyTemplateSection, style);
        propertyTemplateTree.setHeaderVisible(true);
        propertyTemplateTree.setLinesVisible(true);

        createAllTreeColumnsTo(propertyTemplateTree);

        return propertyTemplateTree;
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

    private void createAllTreeColumnsTo(Tree propertyTemplateTree) {
        for (String title : TITLES) {
            TreeColumn treeColumn = createTreeColumnTo(propertyTemplateTree);
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
        propertyTemplateTreeViewer.setInput(propertyTemplates);
    }
}
