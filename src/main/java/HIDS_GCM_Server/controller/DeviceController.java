package HIDS_GCM_Server.controller;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.service.DeviceDbHelper;
import HIDS_GCM_Server.service.GCMService;
import HIDS_GCM_Server.util.CommonUtil;
import org.jboss.solder.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 3/21/13
 * Time: 12:56 AM
 * To change this template use File | Settings | File Templates.
 */
@Model
public class DeviceController implements Serializable {

    @Inject
    Logger logger;

    @Inject
    DeviceDbHelper deviceDbHelper;

    @Inject
    GCMService gcmService;

    private String deviceGcmRegistration;

    private List<Device> deviceList;

    private Device actionDevice;

    private String selectedDeviceSerial;
    private String selectedAppid;

    // Events
    @Inject private Event<Device> deviceEvent;

    @PostConstruct
    public void postInit(){

        // Get list of device.
        deviceList = deviceDbHelper.getDeviceList();

    }

    /**
     * Get device detailed information
     * @param device
     */
    public void doGetDeviceInfo(Device device){

        logger.info("Getting device information for: " + device.getDeviceSerial());
        if(device != null){
            actionDevice = device;

            // Get device information.
            gcmService.doGetDeviceInfo(actionDevice);

            // Get device installed application.
            gcmService.doGetDeviceInstalledApplications(actionDevice);

            // Get device running application.
            gcmService.doGetDeviceRunningApplications(actionDevice);
        }
    }


    /**
     * Scan application
     */
    public void doScanApplication(){

        logger.info("Scan requested for process: " + selectedAppid + " on device: " + selectedDeviceSerial);
        Device device = deviceDbHelper.getDeviceById(selectedDeviceSerial);
        gcmService.doScanApplication(device, deviceDbHelper.getDeviceApplicationById(selectedAppid));

        // Update application scan status.
        deviceDbHelper.updateApplicationScanning(selectedAppid, true);
        // Update the application status.
        deviceDbHelper.updateApplicationVirusStatus(selectedAppid, CommonUtil.virus_scanning);
        // Update the device status.
        deviceDbHelper.updateDeviceVirusStatus(deviceDbHelper.getDeviceById(selectedDeviceSerial), CommonUtil.virus_scanning);

        // Reget device here
        deviceEvent.fire(deviceDbHelper.getDeviceById(selectedDeviceSerial));

        // Reload device list.
        this.postInit();
    }

    /**
     * Never called from webpage.
     */
    public void doSacnPrepare(){

    }

    /**
     * Prepare device's details.
     * @param device
     */
    public void doPrepareDetailedInformation(Device device){

        logger.info("Preparing device information for: " + device.getDeviceSerial());

        deviceEvent.fire(device);
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public Device getActionDevice() {
        if(actionDevice != null){
            logger.info("Action: " + actionDevice.getTotalCapacity());
        }
        return actionDevice;
    }

    public String getSelectedAppid() {
        return selectedAppid;
    }

    public void setSelectedAppid(String selectedAppid) {
        this.selectedAppid = selectedAppid;
    }

    public String getSelectedDeviceSerial() {
        return selectedDeviceSerial;
    }

    public void setSelectedDeviceSerial(String selectedDeviceSerial) {
        this.selectedDeviceSerial = selectedDeviceSerial;
    }
}
