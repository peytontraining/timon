package vn.enclave.peyton.fusion.view;

import static vn.enclave.peyton.fusion.common.Constant.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.*;
import org.eclipse.ui.forms.widgets.*;

import vn.enclave.peyton.fusion.common.*;
import vn.enclave.peyton.fusion.model.*;
import vn.enclave.peyton.fusion.service.*;

public class NavigationViewPart extends AbstractTreeViewPart {

	public static final String ID = "vn.enclave.peyton.fusion.view.navigationViewPart";

	private Text versionText;
	private Text projectText;
	private Text deployTimeText;
	private Text deploySource;
	private Text saveTimeText;
	private Text targetVersionText;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.VERTICAL));

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());

		/* Create Tree View */
		Composite treeComposite = new Composite(parent, SWT.BORDER);
		treeComposite.setLayout(new GridLayout(1, false));

		PatternFilter filter = new PatternFilter();
		FilteredTree filteredTree = new FilteredTree(treeComposite, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL, filter, true);
		filteredTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		treeViewer = filteredTree.getViewer();
		treeViewer.setContentProvider(new ViewContentProvider());
		treeViewer.setLabelProvider(new ViewLabelProvider());
		treeViewer.setInput(createTreeData());

		getSite().setSelectionProvider(treeViewer);

		/* Create Version Properties View */
		Composite formComposite = new Composite(parent, SWT.NONE | SWT.BORDER);
		formComposite.setLayout(new FillLayout());
		formComposite.setVisible(false);

		Section section = toolkit.createSection(formComposite,
				Section.TITLE_BAR);
		section.setLayout(new FillLayout());
		section.setText("Version Properties");
		section.setForeground(parent.getDisplay().getSystemColor(
				SWT.COLOR_BLACK));

		ScrolledForm form = toolkit.createScrolledForm(section);
		form.getBody().setLayout(new GridLayout(2, false));
		GridData textGridData = new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1);

		/* Create labels and texts for form */
		Color gray = parent.getDisplay().getSystemColor(SWT.COLOR_GRAY);
		toolkit.createLabel(form.getBody(), "Version");
		versionText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		versionText.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Project");
		projectText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		projectText.setLayoutData(textGridData);
		projectText.setBackground(gray);
		toolkit.createLabel(form.getBody(), "Deploy Time");
		deployTimeText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		deployTimeText.setLayoutData(textGridData);
		deployTimeText.setBackground(gray);
		toolkit.createLabel(form.getBody(), "Deploy Source");
		deploySource = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		deploySource.setLayoutData(textGridData);
		deploySource.setBackground(gray);
		toolkit.createLabel(form.getBody(), "Save Time");
		saveTimeText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		saveTimeText.setLayoutData(textGridData);
		saveTimeText.setBackground(gray);
		toolkit.createLabel(form.getBody(), "Target Version");
		targetVersionText = toolkit.createText(form.getBody(), "",
				SWT.READ_ONLY);
		targetVersionText.setLayoutData(textGridData);
		targetVersionText.setBackground(gray);

		section.setClient(form);

		createSelectionListener(formComposite);
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
							int size = versions.size();
							for (int i = size - 1; i >= 0; i--) {
								Version version = versions.get(i);
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

	private void createSelectionListener(final Composite formComposite) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		ISelectionService selectionService = window.getSelectionService();
		selectionService.addSelectionListener(NavigationViewPart.ID,
				new ISelectionListener() {

					@Override
					public void selectionChanged(IWorkbenchPart part,
							ISelection selection) {
						IStructuredSelection sselection = (IStructuredSelection) selection;
						Object firstElement = sselection.getFirstElement();
						if (firstElement != null) {
							if (Constant.VERSION_NODE_LEVEL == ((TreeObject) firstElement)
									.getLevel()) {
								VersionService versionService = new VersionService();
								Version version = versionService
										.findByVersionID((String) ((TreeObject) firstElement)
												.getData());
								fillInForm(version);
								formComposite.setVisible(true);
								if (PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage()
										.findView(SceneTableViewPart.ID) == null) {
									System.out.println("Null");
									try {
										PlatformUI.getWorkbench()
												.getWorkbenchWindows()[1]
												.getActivePage().showView(
														SceneTableViewPart.ID);
									} catch (PartInitException e) {
										e.printStackTrace();
									}
								} else {
									System.out.println("Not Null");
								}

							} else {
								formComposite.setVisible(false);
							}
						}
					}
				});
	}

	private void fillInForm(Version version) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		versionText.setText(version.getId());
		projectText.setText(version.getProject().getName());
		deployTimeText.setText(format.format(version.getDeployTime()));
		deploySource.setText(version.getDeploySource());
		saveTimeText.setText(format.format(version.getSaveTime()));
		targetVersionText.setText(version.getTargetVersion());
	}
}
