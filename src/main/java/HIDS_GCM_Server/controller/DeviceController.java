package HIDS_GCM_Server.controller;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;
import HIDS_GCM_Server.service.DeviceDbHelper;
import HIDS_GCM_Server.service.GCMService;
import org.jboss.solder.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
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
public class DeviceController {

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

    @PostConstruct
    public void postInit(){
        // Get list of device.
        deviceList = deviceDbHelper.getDeviceList();
        deviceInstalledApplicationList = new ArrayList<DeviceApplication>();
        deviceRunningApplicationList = new ArrayList<DeviceApplication>();
    }

    /**
     * Get device detailed information
     * @param deviceId
     */
    public void doGetDeviceInfo(String deviceId){

        logger.info("Getting device information for: " + deviceId);
        if(deviceId != null){
            actionDevice = deviceDbHelper.getDeviceById(deviceId);

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
     * @param deviceId
     * @param appProcessName
     */
    public void doScanApplication(String deviceId, String appProcessName){

        logger.info("Scan requested for process: " + appProcessName);
        gcmService.doScanApplication(deviceDbHelper.getDeviceById(deviceId), appProcessName);
    }

    /**
     * Prepare device's details.
     * @param deviceId
     */
    public void doPrepareDetailedInformation(String deviceId){

        logger.info("Preparing device information for: " + deviceId);
        // Get actionDevice
        actionDevice = deviceDbHelper.getDeviceById(deviceId);

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
        return actionDevice;
    }
}
