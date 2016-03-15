package fr.univ_lorraine.oops.ejb;

import fr.univ_lorraine.oops.dal.UtilisateurDAL;
import java.security.MessageDigest;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import fr.univ_lorraine.oops.library.model.Utilisateur;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
@LocalBean
public class PasswordManagerBean {

    @Inject
    MailManagerBean mMB;

    @Inject
    UtilisateurDAL uD;

    /**
     * Create a new password for the Utilisateur with the matching email address and send it to this address
     * @param mail the email address of the Utilisateur asking a new password
     * @return a boolean, true if the email address existsn, else false
     */
    public boolean sendNewPassword(String mail) {
        if (!uD.mailExist(mail)) {
            return false;
        }
        String password = RandomStringUtils.randomAlphanumeric(10);
        String hashPassword = sha256(password);
        mMB.sendNewPassword(mail, password);
        Utilisateur u = uD.getUserByEmail(mail);
        u.setMotDePasse(hashPassword);
        uD.update(u);

        return true;

    }

    /**
     * Hash a String using the sha256 method
     * @param base the String to hash
     * @return the hashed String
     */
    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
