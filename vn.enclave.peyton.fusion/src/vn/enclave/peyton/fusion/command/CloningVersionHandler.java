package vn.enclave.peyton.fusion.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import vn.enclave.peyton.fusion.dialog.SaveAsDialog;
import vn.enclave.peyton.fusion.entity.Device;
import vn.enclave.peyton.fusion.entity.Project;
import vn.enclave.peyton.fusion.entity.Property;
import vn.enclave.peyton.fusion.entity.Version;
import vn.enclave.peyton.fusion.service.impl.VersionService;
import vn.enclave.peyton.fusion.view.NavigationViewPart;

public class CloningVersionHandler extends AbstractHandler implements IHandler {
    private VersionService versionService = new VersionService();
    private ExecutionEvent executionEvent;

    private void setExecutionEvent(ExecutionEvent executionEvent) {
        this.executionEvent = executionEvent;
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        setExecutionEvent(event);

        Object selectedNode = getSelectedNodeOnPlanTree();

        if (selectedNode instanceof Version) {
            Version selectedVersion = (Version) selectedNode;
            if (selectedVersion.isNewVersion()) {
                return null;
            }
            Shell shell = HandlerUtil.getActiveShell(event);

            SaveAsDialog dialog = new SaveAsDialog(shell, selectedVersion);

            if (dialog.open() == Window.OK) {
                String newName = dialog.getName();

                Project parentProject = selectedVersion.getProject();
                Version newVersion = createNewVersionHasParentIs(parentProject);
                newVersion.setName(newName);

                // Get the list devices of selected version.
                List<Device> devicesOfSelectedVersion = selectedVersion.getDevices();

                // Copy all data devices from selected version to new version.
                List<Device> duplicateDevices = new ArrayList<>();
                for (Device sourceDevice : devicesOfSelectedVersion) {
                    Device destinationDevice = new Device();
                    copyFromSourceDeviceToDestinationDevice(sourceDevice, destinationDevice);
                    destinationDevice.setVersion(newVersion);
                    duplicateDevices.add(destinationDevice);

                    List<Property> propertiesOfSourceDevice = sourceDevice.getProperties();
                    List<Property> duplicateProperties = new ArrayList<Property>();
                    for (Property sourceProperty : propertiesOfSourceDevice) {
                        Property destinationProperty = new Property();
                        copyFromSourcePropertyToDestinationProperty(sourceProperty, destinationProperty);
                        destinationProperty.setDevice(destinationDevice);
                        duplicateProperties.add(destinationProperty);
                    }
                    destinationDevice.setProperties(duplicateProperties);
                }
                newVersion.setDevices(duplicateDevices);

                /*
                 * After add() method run, the return variable newVersion has a
                 * new Project pointer.
                 */
                newVersion = versionService.add(newVersion);

                // Set current Project pointer for the new Version Node.
                newVersion.setProject(parentProject);

                // Add new version to project pointer.
                parentProject.getVersions().add(0, newVersion);

                NavigationViewPart navigationViewPart = (NavigationViewPart) HandlerUtil.getActivePart(executionEvent);
                navigationViewPart.refreshPlanTreeViewer();
                navigationViewPart.setSelectionToPlanTreeViewerBy(newVersion);
            }
        }
        return null;
    }

    private Object getSelectedNodeOnPlanTree() {
        IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(executionEvent);
        Object selectedNode = selection.getFirstElement();
        return selectedNode;
    }

    private Version createNewVersionHasParentIs(Project parentProject) {
        Version newVersion = new Version();
        newVersion.setEditable(true);
        newVersion.setProject(parentProject);
        newVersion.setSaveTime(new Date());
        newVersion.setTargetVersion("2.x");
        return newVersion;
    }

    private void copyFromSourceDeviceToDestinationDevice(Device sourceDevice, Device destinationDevice) {
        destinationDevice.setIcon(sourceDevice.getIcon());
        destinationDevice.setName(sourceDevice.getName());
        destinationDevice.setAppModule(sourceDevice.getAppModule());
        destinationDevice.setDeviceType(sourceDevice.getDeviceType());
        destinationDevice.setPhysicalLocation(sourceDevice.getPhysicalLocation());
        destinationDevice.setManufacturer(sourceDevice.getManufacturer());
        destinationDevice.setModelNumber(sourceDevice.getModelNumber());
        destinationDevice.setNotes(sourceDevice.getNotes());
        destinationDevice.setLastModified(sourceDevice.getLastModified());
        destinationDevice.setVersionDevice(sourceDevice.getVersionDevice());
    }

    private void copyFromSourcePropertyToDestinationProperty(Property sourceProperty, Property destinationProperty) {
        destinationProperty.setName(sourceProperty.getName());
        destinationProperty.setValue(sourceProperty.getValue());
        destinationProperty.setMandatory(sourceProperty.isMandatory());
        destinationProperty.setDescription(sourceProperty.getDescription());
    }

}
