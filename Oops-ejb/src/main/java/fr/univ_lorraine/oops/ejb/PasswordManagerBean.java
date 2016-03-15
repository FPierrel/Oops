/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
