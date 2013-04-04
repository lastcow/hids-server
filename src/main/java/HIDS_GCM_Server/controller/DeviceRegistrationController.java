package HIDS_GCM_Server.controller;

import HIDS_GCM_Server.service.DeviceDbHelper;
import org.jboss.solder.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 2/20/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Model
public class DeviceRegistrationController {

    @Inject
    private Logger logger;

    @Inject
    private DeviceDbHelper deviceRegistration;

    public void register(){
        logger.info("==== Try registration");
    }
}
