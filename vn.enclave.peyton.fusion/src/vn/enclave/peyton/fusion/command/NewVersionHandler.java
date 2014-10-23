package vn.enclave.peyton.fusion.command;
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

public class NewVersionHandler extends AbstractHandler implements IHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the current window.
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

        // Get selected version node.
        IStructuredSelection selection =
            (IStructuredSelection) window.getActivePage().getSelection(
                NavigationViewPart.ID);
        Object firstElement = selection.getFirstElement();

        // If selected node is instance of Project.
        if (firstElement instanceof Project) {
            createNewVersionFromProject(event, (Project) firstElement);
            return null;
        }

        // If selected node is instance of Version.
        if (firstElement instanceof Version) {
            createNewVersionFromVersion(event, (Version) firstElement);
            return null;
        }
        return null;
    }

    private void createNewVersionFromVersion(
        ExecutionEvent event, Version selectedVersion) {
        // Create new name to new Version.
        String newName = getNewVersionName(selectedVersion);

        // Create new version.
        Version newVersion =
            createNewVersion(selectedVersion.getProject(), newName);

        // Add new version to project pointer.
        selectedVersion.getProject().getVersions().add(0, newVersion);

        // Refresh the tree after adding new version.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer().refresh();

        // Focus on the new added verion.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer()
            .setSelection(new StructuredSelection(newVersion), true);

        // Add a star on ViewPart title.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .setPartName("* Project");
    }

    private void createNewVersionFromProject(
        ExecutionEvent event, Project selectedProject) {
        // Create new name to new Version.
        Version version = selectedProject.getVersions().get(0);
        String newName = getNewVersionName(version);

        // Create new version.
        Version newVersion = createNewVersion(selectedProject, newName);

        // Add new version to project pointer.
        selectedProject.getVersions().add(0, newVersion);

        // Refresh the tree after adding new version.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer().refresh();

        // Focus on the new added verion.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .getViewer()
            .setSelection(new StructuredSelection(newVersion), true);

        // Add a star on ViewPart title.
        ((NavigationViewPart) HandlerUtil.getActivePart(event))
            .setPartName("* Project");
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
    private Version createNewVersion(Project project, String newName) {
        Version newVersion = new Version();
        newVersion.setId(Constant.DEFAULT_VERSION_ID);
        newVersion.setDeploySource("");
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setProject(project);
        newVersion.setTargetVersion("");
        return newVersion;
    }
}
