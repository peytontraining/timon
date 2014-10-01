package vn.enclave.peyton.fusion.view;

import java.text.SimpleDateFormat;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.ViewPart;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.common.AbstractTreeViewPart.TreeObject;
import vn.enclave.peyton.fusion.model.Version;
import vn.enclave.peyton.fusion.service.VersionService;

public class VersionViewPart extends ViewPart {

	public static final String ID = "vn.enclave.peyton.fusion.view.versionViewPart";

	private Text versionText;
	private Text projectText;
	private Text deployTimeText;
	private Text deploySource;
	private Text saveTimeText;
	private Text targetVersionText;
	private ScrolledForm form;
	private Version version;

	@Override
	public void createPartControl(Composite parent) {
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setExpandHorizontal(true);
		toolkit.decorateFormHeading(form.getForm());
		form.setText("Version Properties");
		form.getBody().setLayout(new GridLayout(2, false));
		
		/* Create labels and text fields */
		GridData textGridData = new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1);
		toolkit.createLabel(form.getBody(), "Version");
		versionText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		versionText.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Project");
		projectText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		projectText.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Deploy Time");
		deployTimeText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		deployTimeText.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Deploy Source");
		deploySource = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		deploySource.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Save Time");
		saveTimeText = toolkit.createText(form.getBody(), "", SWT.READ_ONLY);
		saveTimeText.setLayoutData(textGridData);
		toolkit.createLabel(form.getBody(), "Target Version");
		targetVersionText = toolkit.createText(form.getBody(), "",
				SWT.READ_ONLY);
		targetVersionText.setLayoutData(textGridData);

		createSelectionListener();
	}

	@Override
	public void setFocus() {
		form.setFocus();
	}

	private void createSelectionListener() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		ISelectionService selectionService = window.getSelectionService();
		selectionService.addSelectionListener(ProjectTreeViewPart.ID,
				new ISelectionListener() {

					@Override
					public void selectionChanged(IWorkbenchPart part,
							ISelection selection) {
						IStructuredSelection sselection = (IStructuredSelection) selection;
						Object firstElement = sselection.getFirstElement();
						if (firstElement != null
								&& Constant.VERSION_NODE_LEVEL == ((TreeObject) firstElement)
										.getLevel()) {
							VersionService versionService = new VersionService();
							version = versionService
									.findByVersionID((String) ((TreeObject) firstElement)
											.getData());
							fillInForm();
						}
					}
				});
	}

	private void fillInForm() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		versionText.setText(version.getId());
		projectText.setText(version.getProject().getName());
		deployTimeText.setText(format.format(version.getDeployTime()));
		deploySource.setText(version.getDeploySource());
		saveTimeText.setText(format.format(version.getSaveTime()));
		targetVersionText.setText(version.getTargetVersion());
	}
}
