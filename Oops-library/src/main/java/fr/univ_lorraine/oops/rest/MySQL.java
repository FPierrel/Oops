package fr.univ_lorraine.oops.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {

    private final String url = "jdbc:mysql://test.pi-r-l.ovh:3306/oops";
    private final String utilisateur = "root";
    private final String motDePasse = "fakepwd88";
    private Connection connexion;
    private Statement statement;

    private static MySQL instance;

    private MySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connexion = (Connection) DriverManager.getConnection(url, utilisateur, motDePasse);
            this.statement = connexion.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static MySQL getInstance() {
        if (instance == null){
            synchronized(MySQL.class){
                if (instance == null){
                    instance = new MySQL();
                }
            }
        }
        return instance;
    }

    public ResultSet search(String query) {
        try {
            return this.statement.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
