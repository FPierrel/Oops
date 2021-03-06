package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
@LocalBean
public class MailManagerBean {

    @Inject
    UtilisateurDAL ud;

    /**
     * Send an invite email to the targeted email address
     * @param user login of the Utilisateur inviting
     * @param targetMail email address to send the invite
     * @param userMessage text message embedded in the email
     * @return true if the email is sent to an existing address, else false
     */
    public boolean sendInviteMail(String user, String targetMail, String userMessage) {
        if (ud.mailExist(targetMail)) {
            return false;
        }
        
        String from = "oops@pi-r-l.ovh";
        final String username = "oops@pi-r-l.ovh";
        final String password = "oops_password";
        String host = "smtp.pi-r-l.ovh";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(targetMail));
            message.setSubject("Rejoignez OOPS");
            String msg = "Bonjour,\nVous avez reçu une invitation de " + user + "\n"
                    + "Rejoignez l'annuaire de prestataires ultime : Opinion On Provider of Services !\n"
                    + "http://pi-r-l.ovh:8080/Oops-web/ \n"
                    + "Message de " + user + " à votre intention : \n"
                    + userMessage
                    + "\n\nCordialement\n"
                    + "L'équipe OOPS";

            message.setText(msg);
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Send an email asking to post an opinion on a Prestataire page
     * @param user login of the Utilisateur inviting
     * @param targetMail email address to send the invite
     * @param url url address of the Prestataire page to post on
     * @return false if the address does not exist, else true; 
     */
    public boolean sendAskOpinion(String user, String targetMail, String url) {
        if (ud.mailExist(targetMail)) {
            String from = "oops@pi-r-l.ovh";
            final String username = "oops@pi-r-l.ovh";
            final String password = "oops_password";
            String host = "smtp.pi-r-l.ovh";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(targetMail));
                message.setSubject("Laissez votre avis sur un prestataire OOPS");
                String msg = "Bonjour,\nL'utilisateur " + user + " vous invite à laisser un message sur ce prestataire !\n"
                        + url + "\n\nCordialement\n" + "L'équipe OOPS";
                message.setText(msg);
                Transport.send(message);
                return true;

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    
    /**
     * Send an email containing a new password
     * @param targetMail email address to send the password
     * @param userPassword the new password
     */
    public void sendNewPassword(String targetMail, String userPassword) {

        String from = "oops@pi-r-l.ovh";
        final String username = "oops@pi-r-l.ovh";
        final String password = "oops_password";
        String host = "smtp.pi-r-l.ovh";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(targetMail));
            message.setSubject("Réinitialisation du mot de passe");
            String msg = "Bonjour,\n"
                    + "Vous avez demandé la réinitialisation de votre mot de passe OOPS.\n"
                    + "Votre nouveau mot de passe est : " + userPassword+"\n"
                    + "Connectez-vous à l'adresse suivante : http://pi-r-l.ovh:8080/Oops-web/login.xhtml et changez immédiatement votre mot de passe."
                    + "\n\nCordialement\n"
                    + "L'équipe OOPS";
            message.setText(msg);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
