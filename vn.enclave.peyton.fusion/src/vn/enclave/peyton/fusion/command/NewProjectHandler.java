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
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

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

        if (firstElement instanceof Version) {
            Project newProject = createNewProject((Version) firstElement);
            Version newVersion = createNewVersion(newProject, "1.0.0 *");
            List<Version> versions = new ArrayList<>();
            versions.add(newVersion);
            newProject.setVersions(versions);

            // Add new version to project pointer.
            ((Version) firstElement)
                .getProject().getPlan().getProjects().add(0, newProject);

            // Refresh the tree after adding new project.
            ((NavigationViewPart) HandlerUtil.getActivePart(event))
                .getViewer().refresh();

            // Focus on the new added project.
            ((NavigationViewPart) HandlerUtil.getActivePart(event))
                .getViewer().setSelection(
                    new StructuredSelection(newProject), true);
        }
        return null;
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
