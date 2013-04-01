package HIDS_GCM_Server.service;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;
import HIDS_GCM_Server.util.CommonUtil;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.jboss.solder.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 3/20/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class GCMService implements Serializable {

    @Inject
    Logger logger;
    Map<String, String> params;

    @Inject
    DeviceDbHelper deviceDbHelper;

    /**
     * Send action to retrieve device's information
     * @param device
     */
    public void doGetDeviceInfo(Device device){

        this.sendMessage(device.getGcmRegistrationId(), null, CommonUtil.deviceActionGetInfo, null);
    }

    /**
     * Send action to retrieve device's installed applications
     * @param device
     */
    public void doGetDeviceInstalledApplications(Device device){
        this.sendMessage(device.getGcmRegistrationId(), null, CommonUtil.deviceActionGetInstalledApps, null);
    }

    /**
     * Send action to retrieve device's running applications.
     * @param device
     */
    public void doGetDeviceRunningApplications(Device device){
        this.sendMessage(device.getGcmRegistrationId(), null, CommonUtil.deviceActionGetRunningApps, null);
    }

    /**
     * Scan application
     * @param device
     * @param application
     */
    public void doScanApplication(Device device, DeviceApplication application){

        params = new HashMap<String, String>();
        params.put("processName", application.getProcessName());
        logger.info("GCM ID: " + device.getGcmRegistrationId());
        this.sendMessage(device.getGcmRegistrationId(), application.getId(), CommonUtil.deviceActionMonitoringApp, params);
    }

    /**
     * Send message to device
     * @param deviceId
     * @param action
     * @param params
     */
    private void sendMessage(String deviceId, String processId,  String action, Map<String, String> params){
        // Start sending message
        Sender sender = new Sender(CommonUtil.sendId);
        Message.Builder builder = new Message.Builder();
        builder.addData("action", action);
//        Message message = new Message.Builder().addData("action", action).build();
        if(params != null){
            for(String key : params.keySet()){
                builder.addData(key, params.get(key));
            }
        }
        // Build
        Message message = builder.build();
        boolean isSend = true;
        try {
            Result result = sender.send(message, deviceId, 5);
            String error = result.getErrorCodeName();
            if(result.getCanonicalRegistrationId() != null){
                // Replace device gcmRegId with this.
            }

            if(error != null && error.equals(Constants.ERROR_NOT_REGISTERED)){
                // Application removed from device. Delete from DB as well.
                isSend = false;
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            isSend = false;
        }

        // Update db for scanning flag.
        if( isSend && action.equals(CommonUtil.deviceActionMonitoringApp)){
            deviceDbHelper.updateApplicationScanning(processId, true);
        }
    }
}
