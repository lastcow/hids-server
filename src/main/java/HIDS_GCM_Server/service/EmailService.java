package HIDS_GCM_Server.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 4/10/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class EmailService {

    /**
     * With gmail account
     * @throws EmailException
     */
    public void doSendSimpleEmail(String subject, String msg, List<String> toList) throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("tucoscps@gmail.com", "TUcosc451"));
        email.setSSL(true);
        email.setFrom("tucoscps@gmail.com", "Security Monitoring System - TU");
        email.setSubject(subject);
        email.setMsg(msg);
        if(toList != null){
            for(String recept : toList){
                email.addTo(recept);
            }
        }else{
            // Send email to default
            email.addTo("zhijiang@chen.me");
        }
        email.send();
    }

    public EmailService(){
        // Do nothing.
    }

    public static void main(String[] args){
        new EmailService();
    }
}
