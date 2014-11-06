package vn.enclave.peyton.fusion.command;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class AddingVersionHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);

        Object selectedNode = getSelectedNodeOnPlanTree();

        if (selectedNode instanceof Project) {
            Project selectedProject = (Project) selectedNode;
            createNewVersionFrom(selectedProject);
            return null;
        }
        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;
            Project parentProject = selectedVersion.getProject();
            createNewVersionFrom(parentProject);
            return null;
        }
        return null;
    }

    private Object getSelectedNodeOnPlanTree() {
        IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(executionEvent);
        Object selectedNode = selection.getFirstElement();
        return selectedNode;
    }

    private void createNewVersionFrom(Project parentProject) {
        Version newVersion = createNewVersionHasParentIs(parentProject);

        parentProject.getVersions().add(0, newVersion);

        NavigationViewPart navigationViewPart = (NavigationViewPart) HandlerUtil.getActivePart(executionEvent);
        navigationViewPart.refreshPlanTreeViewer();
        navigationViewPart.setSelectionToPlanTreeViewerBy(newVersion);
        navigationViewPart.setDirty(true);
    }

    /*
     * Create the new name for the new Version with format 1.0.x
     */
    private String getNewVersionName(Version version) {
        // Get the latest version.
        Version latestVersion = version.getProject().getVersions().get(0);

        // Get the last number in version name. For example: 1.0.13, then
        // tailName = 13
        String tailName = latestVersion.getName().split("\\.")[2];

        // Convert tailName to int.
        int number = Integer.valueOf(tailName);

        // Increase number by one, then convert to String.
        tailName = String.valueOf(++number);

        return "1.0.".concat(tailName).concat(" *");
    }

    /*
     * Initial new version. Set project data for new Version.
     */
    private Version createNewVersionHasParentIs(Project parentProject) {
        Version version = parentProject.getVersions().get(0);
        String newName = getNewVersionName(version);
        Version newVersion = new Version();
        newVersion.setId(Constant.DEFAULT_VERSION_ID);
        newVersion.setDeploySource("");
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setProject(parentProject);
        newVersion.setTargetVersion("");
        return newVersion;
    }
}
