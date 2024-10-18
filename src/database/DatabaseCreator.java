package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {
    public void createDatabase() {
        DatabaseCredentials credentials = new DatabaseCredentials();

        try {
            Connection conn = DriverManager.getConnection(credentials.toRawURI(), credentials.getUser(), credentials.getPassword());
            Statement stmt = conn.createStatement();

            String sql = "CREATE DATABASE " + credentials.getDbName();
            stmt.executeUpdate(sql);
            System.out.println("Base de données " + credentials.getDbName() + " créée avec succès.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
