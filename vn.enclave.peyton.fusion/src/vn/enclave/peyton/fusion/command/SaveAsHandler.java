package vn.enclave.peyton.fusion.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.SaveAsDialog;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class SaveAsHandler extends AbstractHandler implements IHandler {

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
            // Get shell.
            Shell shell = window.getShell();

            // Show the dialog.
            SaveAsDialog dialog =
                new SaveAsDialog(shell, (Version) firstElement);
            int open = dialog.open();

            if (open == Window.OK) {
                String newName = dialog.getName();

                // Create new version.
                Version newVersion =
                    createNewVersion(
                        ((Version) firstElement).getProject(), newName);

                // Get the list devices of selected version.
                List<Device> devices = ((Version) firstElement).getDevices();

                // Copy all data devices from selected version to new version.
                List<Device> copyDevices = new ArrayList<>();
                for (Device device : devices) {
                    Device d = new Device();
                    d.setAppModule(device.getAppModule());
                    d.setDeviceType(device.getDeviceType());
                    d.setManufacturer(device.getManufacturer());
                    d.setName(device.getName());
                    d.setPhysicalLocation(device.getPhysicalLocation());
                    d.setVersion(newVersion);
                    copyDevices.add(d);
                }
                newVersion.setDevices(copyDevices);

                /*
                 * After add() method run, the return variable newVersion
                 * has a new Project pointer.
                 */
                newVersion = versionService.add(newVersion);

                // Set current Project pointer for the new Version Node.
                newVersion.setProject(((Version) firstElement).getProject());

                // Add new version to project pointer.
                ((Version) firstElement)
                    .getProject().getVersions().add(0, newVersion);

                // Refresh the tree after adding new version.
                ((NavigationViewPart) HandlerUtil.getActivePart(event))
                    .getViewer().refresh();
                
                // Focus on the new added verion.
                ((NavigationViewPart) HandlerUtil.getActivePart(event))
                    .getViewer().setSelection(
                        new StructuredSelection(newVersion), true);
            }
        }
        return null;
    }

    /*
     * Initial new version. Set project data for new Version.
     */
    private Version createNewVersion(Project project, String newName) {
        Version newVersion = new Version();
        newVersion.setDeploySource("");
        newVersion.setDeployTime(new Date()); // Current datetime.
        newVersion.setDevices(null);
        newVersion.setEditable(true);
        newVersion.setName(newName);
        newVersion.setProject(project);
        newVersion.setSaveTime(new Date()); // Current datetime.
        newVersion.setTargetVersion("2.x");
        return newVersion;
    }
}
