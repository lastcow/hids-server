package HIDS_GCM_Server.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: lastcow
 * Date: 4/10/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class EmailService {

    public void doSendSimpleEmail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("tucoscps@gmail.com", "TUcosc451"));
        email.setSSL(true);
        email.setFrom("tucoscps@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("zhijiang@chen.me");
        email.send();
    }

    EmailService(){
        try {
            this.doSendSimpleEmail();
        } catch (EmailException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args){
        new EmailService();
    }
}
