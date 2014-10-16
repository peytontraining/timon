package vn.enclave.peyton.fusion.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.ProjectService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class NewProjectHandler extends AbstractHandler implements IHandler {

    private ProjectService projectService = new ProjectService();

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

            Project newProject = new Project();
            newProject.setName("UNKNOWN");
            newProject.setPlan(((Version) firstElement).getProject().getPlan());

            Version newVersion = createNewVersion(newProject, "1.0.0");
            List<Version> versions = new ArrayList<>();
            versions.add(newVersion);
            newProject.setVersions(versions);

            /*
             * After add() method run, the return variable newProject
             * has a new Plan pointer.
             */
            newProject = projectService.add(newProject);

            // Set current Plan pointer for the new Project Node.
            newProject.setPlan(((Version) firstElement).getProject().getPlan());

            // Add new version to project pointer.
            ((Version) firstElement)
                .getProject().getPlan().getProjects().add(0, newProject);

            // Refresh the tree after adding new project.
            ((NavigationViewPart) HandlerUtil.getActivePart(event))
                .getViewer().refresh();
        }
        return null;
    }

    /*
     * Initial new version. Set project data for new Version.
     */
    private Version createNewVersion(Project project, String newName) {
        Version newVersion = new Version();
        newVersion.setDeploySource("");
        newVersion.setDeployTime(new Date()); //Current datetime.
        newVersion.setDevices(null);
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setProject(project);
        newVersion.setSaveTime(new Date()); //Current datetime.
        newVersion.setTargetVersion("2.x");
        return newVersion;
    }
    
    
}
