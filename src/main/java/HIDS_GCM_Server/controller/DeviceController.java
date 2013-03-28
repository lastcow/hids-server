package HIDS_GCM_Server.controller;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;
import HIDS_GCM_Server.service.DeviceDbHelper;
import HIDS_GCM_Server.service.GCMService;
import org.jboss.solder.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
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

    private List<DeviceApplication> deviceInstalledApplicationList;
    private List<DeviceApplication> deviceRunningApplicationList;

    private Device actionDevice;

    private String selectedDeviceSerial;
    private String selectedAppid;

    @PostConstruct
    public void postInit(){
        logger.info("CALLED");
        // Get list of device.
        deviceList = deviceDbHelper.getDeviceList();

        deviceInstalledApplicationList = new ArrayList<DeviceApplication>();
        deviceRunningApplicationList = new ArrayList<DeviceApplication>();

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
//        gcmService.doScanApplication(deviceDbHelper.getDeviceById(deviceId), appProcessName);
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
//        // Get actionDevice
        actionDevice = device;

        // Get installed applications
        this.doGetDeviceInstalledApplication();

        // Get running applications.
        this.doGetDeviceRunningApplication();
    }

    /**
     * Get device installed applications
     */
    private void doGetDeviceInstalledApplication(){
        deviceInstalledApplicationList = actionDevice.getInstalledApplications();
    }

    /**
     * Get device running applications
     */
    private void doGetDeviceRunningApplication(){
        deviceRunningApplicationList = actionDevice.getRunningApplications();
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public List<DeviceApplication> getDeviceInstalledApplicationList() {
        return deviceInstalledApplicationList;
    }

    public List<DeviceApplication> getDeviceRunningApplicationList() {
        return deviceRunningApplicationList;
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
