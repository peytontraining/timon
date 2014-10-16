package vn.enclave.peyton.fusion.command;
import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;


public class NewVersionHandler extends AbstractHandler implements IHandler {

    private VersionService versionService = new VersionService();

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
            // Create new name to new Version.
            String newName = getNewVersionName((Version) firstElement);

            // Create new version.
            Version newVersion =
                createNewVersion(
                    ((Version) firstElement).getProject(), newName);

            /*
             * After add() method run, the return variable newVersion
             * has a new Project pointer.
             */
            newVersion = versionService.add(newVersion);

            // Set current Project pointer for the new Version Node.
            newVersion.setProject(((Version) firstElement).getProject());

            // Add new version to project pointer.
            ((Version) firstElement).getProject().getVersions().add(0, newVersion);
            
            // Refresh the tree after adding new version.
            ((NavigationViewPart) HandlerUtil.getActivePart(event))
                .getViewer().refresh();
        }
        return null;
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

        return "1.0.".concat(tailName);
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