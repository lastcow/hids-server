package HIDS_GCM_Server.data;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 4/1/13
 * Time: 7:18 PM
 * To change this template use File | Settings | File Templates.
 */

@Model
public class DeviceApplicationProducer extends AbstractProducer implements Serializable {

    private Device device;
    private List<DeviceApplication> installApplicationList;
    private List<DeviceApplication> runningApplicationList;

    @PostConstruct
    public void postInit(){
        installApplicationList = new ArrayList<DeviceApplication>();
        runningApplicationList = new ArrayList<DeviceApplication>();
    }

    /**
     * Event listener
     * @param deviceApplication
     */
    public void onDeviceApplicationChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final DeviceApplication deviceApplication){

    }

    /**
     * Listener on device changed.
     * @param device
     */
    public void onDeviceChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Device device){
        if(device != null){
            this.device = em.find(Device.class, device.getDeviceSerial());
            installApplicationList = this.device.getInstalledApplications();
            runningApplicationList = this.device.getRunningApplications();
        }
    }


    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<DeviceApplication> getInstallApplicationList() {
        return installApplicationList;
    }

    public void setInstallApplicationList(List<DeviceApplication> installApplicationList) {
        this.installApplicationList = installApplicationList;
    }

    public List<DeviceApplication> getRunningApplicationList() {
        return runningApplicationList;
    }

    public void setRunningApplicationList(List<DeviceApplication> runningApplicationList) {
        this.runningApplicationList = runningApplicationList;
    }
}
