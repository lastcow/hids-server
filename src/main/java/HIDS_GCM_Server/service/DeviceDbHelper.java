package HIDS_GCM_Server.service;

import HIDS_GCM_Server.model.ApplicationAck;
import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;
import HIDS_GCM_Server.model.VirusStatus;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 2/20/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class DeviceDbHelper implements Serializable {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Device> deviceEventSrc;

    public void register(Device device) throws Exception {
        // Get existing device information if existed.
        Device savedDevice = em.find(Device.class, device.getDeviceSerial());

        if(savedDevice == null){
            em.persist(device);
        }else{
            savedDevice.setGcmRegistrationId(device.getGcmRegistrationId());
            savedDevice.setRegisterDate(new Date());
            em.merge(savedDevice);
        }

        deviceEventSrc.fire(device);
    }

    /**
     * Update device information to database.
     * @param device
     */
    public void updateDeviceInfo(Device device) {
        Device savedDevice = em.find(Device.class, device.getDeviceSerial());

        if(savedDevice != null){
            // Update
            device.setGcmRegistrationId(savedDevice.getGcmRegistrationId());
            device.setRegisterDate(savedDevice.getRegisterDate());
            device.setVirusStatusBean(savedDevice.getVirusStatusBean());
            em.merge(device);
        }else{
            em.persist(device);
        }
    }

    /**
     * Update device installed applications list.
     * @param apps
     * @param deviceSerial
     */
    public void updateInstalledApps(List<DeviceApplication> apps, String deviceSerial){

        // Delete list of installed apps
        Query query = em.createQuery("DELETE FROM DeviceApplication app WHERE app.device2.deviceSerial = :serial");
        query.setParameter("serial", deviceSerial);
        query.executeUpdate();

        Device device = em.find(Device.class, deviceSerial);
        device.setInstalledApplications(apps);

        em.merge(device);
    }

    /**
     * Update device running applications list.
     * @param apps
     * @param deviceSerial
     */
    public void updateRunningApps(List<DeviceApplication> apps, String deviceSerial){

        // Delete list of installed apps
        Query query = em.createQuery("DELETE FROM DeviceApplication app WHERE app.device1.deviceSerial = :serial");
        query.setParameter("serial", deviceSerial);
        query.executeUpdate();

        Device device = em.find(Device.class, deviceSerial);
        device.setRunningApplications(apps);

        em.merge(device);

    }

    /**
     * Get device list.
     * @return
     */
    public List<Device> getDeviceList(){
        Query query = em.createQuery("SELECT device FROM Device device");
        return query.getResultList();
    }

    /**
     * Get Device by ID.
     * @param deviceId
     * @return
     */
    public Device getDeviceById(String deviceId){
        return em.find(Device.class, deviceId);
    }

    /**
     * Get VirusStatus by status.
     * @param status
     * @return
     */
    public VirusStatus getVirusStatusByStatus(String status){
        Query query = em.createNamedQuery("getVirusStatusByStatus").setParameter("status", status);

        return (VirusStatus) query.getSingleResult();
    }

    /**
     * Get ApplicationAck by signature
     * @param signature
     * @return
     */
    public ApplicationAck getApplicationAckBySignature(String signature){
        ApplicationAck appAck = null;
        Query query = em.createNamedQuery("getAppAckBySignature").setParameter("signature", signature);
        try{
            appAck = (ApplicationAck) query.getSingleResult();
        }catch(NoResultException nre){
            // no result found.
        }
        return appAck;
    }

    /**
     * Get device application by id.
     * @param processId
     * @return
     */
    public DeviceApplication getDeviceApplicationById(String processId){
        return em.find(DeviceApplication.class, processId);
    }

    /**
     * Update Application scanning flag.
     * @param processId
     * @param scanning
     */
    public void updateApplicationScanning(String processId, boolean scanning){
        DeviceApplication deviceApplication = this.getDeviceApplicationById(processId);
        if(deviceApplication != null){
            deviceApplication.setScanning(scanning);
            // Update db.
            em.merge(deviceApplication);
        }
    }

    /**
     * Update application's virus status.
     * @param processId
     * @param status
     */
    public void updateApplicationVirusStatus(String processId, String status){
        System.out.println("=======");
        DeviceApplication deviceApplication = this.getDeviceApplicationById(processId);
        System.out.println("Status :" + deviceApplication.getName());
        if(deviceApplication != null){
            deviceApplication.setVirusStatusBean(this.getVirusStatusByStatus(status));
            em.merge(deviceApplication);
        }
    }

    /**
     * Update device status.
     * @param device
     * @param status
     */
    public void updateDeviceVirusStatus(Device device, String status){
        if(device != null){
            device.setVirusStatusBean(this.getVirusStatusByStatus(status));
            em.merge(device);
        }
    }
}
