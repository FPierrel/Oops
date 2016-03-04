/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univ_lorraine.oops.ejb;

import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author romain
 */
@Stateless
@LocalBean
public class MailManagerBean {

    @PersistenceContext(unitName = "fr.univ_lorraine_Oops-library_jar_1.0-SNAPSHOTPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return this.em;
    }

    public boolean sendInviteMail(String user, String targetMail, String userMessage) {
        String queryString = "SELECT u.mail "
                + "FROM Utilisateur u "
                + "WHERE u.mail = '" + targetMail + "'";

        TypedQuery<String> query = this.getEntityManager().createQuery(queryString, String.class);
        List<String> l = query.getResultList();
        if (l.size() > 0) {
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

        System.out.println("target mail : " + targetMail);
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
            String msg = "Vous avez reçu une invitation de " + user + "\n"
                    + "Rejoignez l'annuaire de prestataires ultime : Opinion On Provider of Services !\n"
                    + "http://pi-r-l.ovh:8080/Oops-web/ \n"
                    + "Message de " + user + " à votre intention : \n"
                    + userMessage;

            message.setText(msg);
            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean sendAskOpinion(String user, String targetMail, String url) {
        String queryString = "SELECT u.mail "
                + "FROM Utilisateur u "
                + "WHERE u.mail = '" + targetMail + "'";
        TypedQuery<String> query = this.getEntityManager().createQuery(queryString, String.class);
        List<String> l = query.getResultList();
        if (l.size() > 0) {
            System.out.println("SessionBean : mail ok ");
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
                String msg = "L'utilisateur " + user + " vous invite à laisser un message sur ce prestataire !\n"
                        + url + "\n\nCordialement\n" + "L'équipe OOPS";
                message.setText(msg);
                Transport.send(message);
                return true;

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("SessionBean : mail pas ok ");
        return false ;
    }
}
