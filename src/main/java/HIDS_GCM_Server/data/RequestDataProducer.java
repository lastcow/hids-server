package HIDS_GCM_Server.data;

import HIDS_GCM_Server.util.CommonUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 4/4/13
 * Time: 1:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Named
@RequestScoped
public class RequestDataProducer extends AbstractProducer{

    /**
     * bg class for different status.
     * @param status
     * @return
     */
    public String getBgClass(String status){
        if(status.equals(CommonUtil.virus_clear)){
            return "green-bg";
        }else if(status.equals(CommonUtil.virus_unknow)){
            return "orange-bg";
        }else if(status.equals(CommonUtil.virus_infect)){
            return "red-bg";
        }else if(status.equals(CommonUtil.virus_scanning)){
            return "blue-bg";
        }

        return null;
    }
}
