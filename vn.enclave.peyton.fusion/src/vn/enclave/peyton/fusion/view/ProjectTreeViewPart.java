package vn.enclave.peyton.fusion.view;

import static vn.enclave.peyton.fusion.common.Constant.*;

import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.*;

import vn.enclave.peyton.fusion.common.AbstractTreeViewPart;
import vn.enclave.peyton.fusion.model.*;
import vn.enclave.peyton.fusion.service.PlanService;

public class ProjectTreeViewPart extends AbstractTreeViewPart {

	public static final String ID = "vn.enclave.peyton.fusion.view.projectTreeViewPart";

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		/* Create TreeViewer and its filter */
		PatternFilter filter = new PatternFilter();
		FilteredTree filteredTree = new FilteredTree(parent, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		filteredTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		treeViewer = filteredTree.getViewer();
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setLabelProvider(new ViewLabelProvider());
		treeViewer.setInput(createTreeData());
		getSite().setSelectionProvider(treeViewer);
	}

	private static final class ViewLabelProvider extends LabelProvider {

		private static final long serialVersionUID = -7691934858007357490L;

		@Override
		public Image getImage(Object element) {
			Image image = null;

			switch (((TreeObject) element).getLevel()) {
			case PLAN_NODE_LEVEL:
				image = PLAN_IMAGE;
				break;

			case PROJECT_NODE_LEVEL:
				image = PROJECT_IMAGE;
				break;

			case VERSION_NODE_LEVEL:
				image = VERSION_IMAGE;
				break;
			}

			return image;
		}
	}

	private TreeObject createTreeData() {
		TreeParent root = new TreeParent("");

		PlanService planService = new PlanService();
		List<Plan> plans = planService.findAll();

		/* Create plan nodes */
		if (0 != plans.size()) {
			for (Plan plan : plans) {
				TreeParent planNode = new TreeParent(plan.getName());
				planNode.setLevel(PLAN_NODE_LEVEL);
				root.addChild(planNode);

				/* Create project nodes */
				List<Project> projects = plan.getProjects();
				if (0 != projects.size()) {
					for (Project project : projects) {
						TreeParent projectNode = new TreeParent(
								project.getName());
						projectNode.setLevel(PROJECT_NODE_LEVEL);
						planNode.addChild(projectNode);

						/* Create version nodes */
						List<Version> versions = project.getVersions();
						if (0 != versions.size()) {
							for (Version version : versions) {
								TreeObject versionNode = new TreeObject(
										version.getId());
								versionNode.setLevel(VERSION_NODE_LEVEL);
								versionNode.setData(version.getId());
								projectNode.addChild(versionNode);
							}
						}
					}
				}
			}
		}

		return root;
	}
}
