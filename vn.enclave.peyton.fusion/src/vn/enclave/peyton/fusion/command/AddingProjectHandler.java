package vn.enclave.peyton.fusion.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class AddingProjectHandler extends AbstractHandler implements IHandler {
    private ExecutionEvent executionEvent;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);

        Object selectedNode = getSelectedNodeOnPlanTree();

        if (selectedNode instanceof Plan) {
            Plan selectedPlan = (Plan) selectedNode;
            addNewProjectFrom(selectedPlan);
            return null;
        }
        if (selectedNode instanceof Project) {
            Project selectedProject = (Project) selectedNode;
            if (selectedProject.isNewProject()) {
                return null;
            }
            Plan parentPlan = selectedProject.getPlan();
            addNewProjectFrom(parentPlan);
            return null;
        }
        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;
            if (selectedVersion.isNewVersion()) {
                return null;
            }
            Project parentProject = selectedVersion.getProject();
            Plan parentPlan = parentProject.getPlan();
            addNewProjectFrom(parentPlan);
            return null;
        }

        return null;
    }

    private Object getSelectedNodeOnPlanTree() {
        IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(executionEvent);
        Object selectedNode = selection.getFirstElement();
        return selectedNode;
    }

    private void addNewProjectFrom(Plan parentPlan) {
        Project newProject = createNewProjectHasParentIs(parentPlan);

        parentPlan.getProjects().add(0, newProject);

        NavigationViewPart navigationViewPart = (NavigationViewPart) HandlerUtil.getActivePart(executionEvent);
        navigationViewPart.refreshPlanTreeViewer();
        navigationViewPart.setSelectionToPlanTreeViewerBy(newProject);
        navigationViewPart.setDirty(true);
    }

    private Project createNewProjectHasParentIs(Plan plan) {
        Project newProject = new Project();
        newProject.setName("UNKNOWN *");
        newProject.setPlan(plan);
        newProject.setDeploymentLocked(false);
        newProject.setEditable(true);
        newProject.setGateway(true);
        newProject.setHost("");
        newProject.setId(Project.DEFAULT_PROJECT_ID);
        newProject.setLicense("");
        newProject.setNote("");
        newProject.setPort(0);
        newProject.setUuid("");

        Version newVersion = createNewVersionHas("1.0.0 *");
        newVersion.setProject(newProject);
        List<Version> versions = new ArrayList<>();
        versions.add(newVersion);
        newProject.setVersions(versions);

        return newProject;
    }

    private Version createNewVersionHas(String newName) {
        Version newVersion = new Version();
        newVersion.setId(Version.DEFAULT_VERSION_ID);
        newVersion.setDeploySource("");
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setTargetVersion("2.x");
        return newVersion;
    }

}
