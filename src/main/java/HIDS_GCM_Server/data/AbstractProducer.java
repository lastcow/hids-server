package HIDS_GCM_Server.data;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 4/1/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractProducer {

    @Inject
    protected EntityManager em;
    protected Query query;

}
