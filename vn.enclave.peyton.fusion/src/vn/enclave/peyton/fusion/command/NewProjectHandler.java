package vn.enclave.peyton.fusion.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.common.Constant;
import vn.enclave.peyton.fusion.entity.Plan;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

//TODO: Reorganize NewProjectHandler
public class NewProjectHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the current window.
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        // Get selected version node.
        IStructuredSelection selection =
            (IStructuredSelection) window.getActivePage().getSelection(
                NavigationViewPart.ID);
        Object firstElement = selection.getFirstElement();

        // If selected node is an instance of Plan.
        if (firstElement instanceof Plan) {
            addNewProjectFromPlan(event, firstElement);
            return null;
        }

        // If selected node is an instance of Project.
        if (firstElement instanceof Project) {
            addNewProjectFromProject(event, firstElement);
            return null;
        }

        // If selected node is an instance of Version.
        if (firstElement instanceof Version) {
            addNewProjectFromVersion(event, firstElement);
            return null;
        }

        return null;
    }

    private void addNewProjectFromVersion(
        ExecutionEvent event, Object firstElement) {
        Version selectedVersion = (Version) firstElement;
        Project newProject = createNewProject(selectedVersion);
        Version newVersion = createNewVersion(newProject, "1.0.0 *");
        List<Version> versions = new ArrayList<>();
        versions.add(newVersion);
        newProject.setVersions(versions);

        // Add new version to project pointer.
        selectedVersion.getProject().getPlan().getProjects().add(0, newProject);

        // Refresh the tree after adding new project.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer().refresh();

        // Focus on the new added project.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer()
            .setSelection(new StructuredSelection(newProject), true);

        // Add a star on ViewPart title.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .setPartName("* Project");
    }

    private void addNewProjectFromProject(
        ExecutionEvent event, Object firstElement) {
        Project selectedProject = (Project) firstElement;
        Project newProject = createNewProject(selectedProject);
        Version newVersion = createNewVersion(newProject, "1.0.0 *");
        List<Version> versions = new ArrayList<>();
        versions.add(newVersion);
        newProject.setVersions(versions);

        // Add new version to project pointer.
        selectedProject.getPlan().getProjects().add(0, newProject);

        // Refresh the tree after adding new project.
//        ((NavigationViewPart) HandlerUtil.getActivePart(event))
//            .getViewer().refresh();
        ((NavigationViewPart) HandlerUtil.getActivePart(event)).refreshPlanTreeViewer();
        
        // Focus on the new added project.
//        ((NavigationViewPart) HandlerUtil.getActivePart(event))
//            .getViewer()
//            .setSelection(new StructuredSelection(newProject), true);

        ((NavigationViewPart) HandlerUtil.getActivePart(event)).setSelectionToPlanTreeViewerBy(newProject);
        // Add a star on ViewPart title.
//        ((NavigationViewPart) HandlerUtil.getActivePart(event))
//            .setPartName("* Project");
    }

    private
        void addNewProjectFromPlan(ExecutionEvent event, Object firstElement) {
        Plan selectedPlan = (Plan) firstElement;
        Project newProject = createNewProject(selectedPlan);
        Version newVersion = createNewVersion(newProject, "1.0.0 *");
        List<Version> versions = new ArrayList<>();
        versions.add(newVersion);
        newProject.setVersions(versions);

        // Add new version to project pointer.
        selectedPlan.getProjects().add(0, newProject);

        // Refresh the tree after adding new project.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer().refresh();

        // Focus on the new added project.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer()
            .setSelection(new StructuredSelection(newProject), true);

        // Add a star on ViewPart title.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .setPartName("* Project");
    }

    private Project createNewProject(Version version) {
        Project newProject = new Project();
        newProject.setName("UNKNOWN *");
        newProject.setPlan(version.getProject().getPlan());
        newProject.setDeploymentLocked(false);
        newProject.setEditable(true);
        newProject.setGateway(true);
        newProject.setHost("");
        newProject.setId(Constant.DEFAULT_PROJECT_ID);
        newProject.setLicense("");
        newProject.setNote("");
        newProject.setPort(0);
        newProject.setUuid("");
        return newProject;
    }

    private Project createNewProject(Project project) {
        Project newProject = new Project();
        newProject.setName("UNKNOWN *");
        newProject.setPlan(project.getPlan());
        newProject.setDeploymentLocked(false);
        newProject.setEditable(true);
        newProject.setGateway(true);
        newProject.setHost("");
        newProject.setId(Constant.DEFAULT_PROJECT_ID);
        newProject.setLicense("");
        newProject.setNote("");
        newProject.setPort(0);
        newProject.setUuid("");
        return newProject;
    }

    private Project createNewProject(Plan plan) {
        Project newProject = new Project();
        newProject.setName("UNKNOWN *");
        newProject.setPlan(plan);
        newProject.setDeploymentLocked(false);
        newProject.setEditable(true);
        newProject.setGateway(true);
        newProject.setHost("");
        newProject.setId(Constant.DEFAULT_PROJECT_ID);
        newProject.setLicense("");
        newProject.setNote("");
        newProject.setPort(0);
        newProject.setUuid("");
        return newProject;
    }

    /*
     * Initial new version. Set project data for new Version.
     */
    private Version createNewVersion(Project project, String newName) {
        Version newVersion = new Version();
        newVersion.setDeploySource("");
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setProject(project);
        newVersion.setTargetVersion("2.x");
        return newVersion;
    }

}
