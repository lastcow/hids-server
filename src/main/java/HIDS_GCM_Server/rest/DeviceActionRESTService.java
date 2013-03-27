package HIDS_GCM_Server.rest;

import HIDS_GCM_Server.model.Device;
import HIDS_GCM_Server.model.DeviceApplication;
import HIDS_GCM_Server.service.DeviceDbHelper;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.solder.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 2/19/13
 * Time: 6:24 PM
 */
@Path("/deviceaction")
@RequestScoped
public class DeviceActionRESTService {
    @Inject
    Logger logger;

    @Inject
    EntityManager em;

    @Inject
    DeviceDbHelper deviceDbHelper;

    @POST
    @Path("/register")
    @Produces(MediaType.TEXT_HTML)
    public void doRegistrate(@FormParam("gcmregid") String gcmRegId, @FormParam("deviceserial") String deviceSerial){
        // Write do database.
//        logger.info("Record id: " + id + " /email: " + email);
        if(gcmRegId.isEmpty()){
            return;
        }

        logger.info("POSTING HERE: " + gcmRegId);

        // Store in DB
        Device device = new Device();
        device.setGcmRegistrationId(gcmRegId);
        device.setDeviceSerial(deviceSerial);
        try {
            deviceDbHelper.register(device);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @POST
    @Path("/install")
    @Produces(MediaType.TEXT_HTML)
    public void doPackageInstall(@FormParam("package") String packageName, @FormParam("fresh_install") boolean freshInstall){
        logger.info("Package: " + packageName);
        logger.info("Install or update? " + freshInstall);

    }

    @POST
    @Path("/logfileput")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response uploadLogFile(MultipartFormDataInput input){
        logger.info("Logfile added");

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("logfile");
        String fileName = null;
        for(InputPart inputPart : inputParts){
            try{
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                // Conver upload file to input stream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                fileName = "/Users/lastcow/Documents/TEMP/" + fileName;

                writeFile(bytes, fileName);

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        return Response.status(200).entity("sth here").build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.TEXT_HTML)
    public Response deviceStatus(@FormParam("manufacturer") String manufacturer, @FormParam("device") String industrialDesign,
                                 @FormParam("hardware") String hardware, @FormParam("serial") String serial,
                                 @FormParam("id") String deviceLabel, @FormParam("model") String deviceModel,
                                 @FormParam("codename") String codeName, @FormParam("freeinternalsize") String freeInternalSize,
                                 @FormParam("totalinternalsize") String totalInternalSize){

        // Log.
        logger.info("Device information received for : " + serial );

        // Update device.
        Device device = new Device();
        device.setDeviceSerial(serial);
        device.setManufacturer(manufacturer);
        device.setIndustrialDesign(industrialDesign);
        device.setHardware(hardware);
        device.setDeviceLabel(deviceLabel);
        device.setDeviceModel(deviceModel);
        device.setCodeName(codeName);
        device.setTotalCapacity(Integer.valueOf(totalInternalSize));
        device.setUsedCapacity((device.getTotalCapacity() - Integer.valueOf(freeInternalSize)));

        deviceDbHelper.updateDeviceInfo(device);

        return Response.status(Response.Status.OK).entity("Database updated").build();
    }

    @POST
    @Path("/infotest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deviceStatus(Device device){
        System.out.println("Device info received");
        System.out.println("Device info: " + device.toString());
        System.out.println("Device Serial: " + device.getDeviceSerial());

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testRest(@FormParam("name") String name, @FormParam("serial") String serial){
        System.out.println("Device info: " + serial);

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Format apps: [string, string, string, ...]
     * @param apps
     * @param deviceSerial
     * @return
     */
    @POST
    @Path("/installedapps")
    @Produces(MediaType.TEXT_HTML)
    public Response installedApps(@FormParam("apps") List<String> apps, @FormParam("deviceserial") String deviceSerial){

//        deviceDbHelper.updateInstalledApps(apps, deviceSerial);

        Device device = deviceDbHelper.getDeviceById(deviceSerial);
        String[] applications = apps.get(0).split(",");
        String[] appProperties = null;
        DeviceApplication deviceApplication = null;
        List<DeviceApplication> deviceApplicationList = new ArrayList<DeviceApplication>();
        for(String app : applications){
            app = app.replace("[", "");
            app = app.replace("]", "");
            appProperties = app.split("\\|");
            deviceApplication = new DeviceApplication();
            deviceApplication.setVersionNumber(appProperties[1].trim());
            deviceApplication.setAppPackage(appProperties[0].trim());
            deviceApplication.setProcessName(appProperties[2].trim());
            deviceApplication.setName(appProperties[3].trim());
            deviceApplication.setId(UUID.randomUUID().toString().toUpperCase());
            deviceApplication.setDevice2(device);
            deviceApplicationList.add(deviceApplication);

        }

        deviceDbHelper.updateInstalledApps(deviceApplicationList, deviceSerial);

        return Response.status(Response.Status.OK).entity("Database updated").build();
    }

    @POST
    @Path("/runningapps")
    @Produces(MediaType.TEXT_HTML)
    public Response runningApps(@FormParam("apps") List<String> apps, @FormParam("deviceserial") String deviceSerial){


        Device device = deviceDbHelper.getDeviceById(deviceSerial);
        String[] applications = apps.get(0).split(",");
        String[] appProperties = null;

        DeviceApplication deviceApplication = null;
        List<DeviceApplication> deviceApplicationList = new ArrayList<DeviceApplication>();
        for(String app : applications){
            app = app.replace("[", "");
            app = app.replace("]", "");
            appProperties = app.split("\\|");
            deviceApplication = new DeviceApplication();

            deviceApplication.setName(appProperties[1].trim());
            deviceApplication.setProcessName(appProperties[0].trim());
            deviceApplication.setId(UUID.randomUUID().toString().toUpperCase());
            deviceApplication.setDevice1(device);
            deviceApplicationList.add(deviceApplication);

        }

        deviceDbHelper.updateRunningApps(deviceApplicationList, deviceSerial);

        return Response.status(Response.Status.OK).entity("Database updated").build();
    }

    /**
     * get file name
     * @param header
     * @return
     */
    private String getFileName(MultivaluedMap<String, String> header){
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


    /**
     * write file
     * @param content
     * @param filename
     * @throws IOException
     */
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
}
