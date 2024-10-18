package database.access.role;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAORole {
    PreparedStatement insertRole;

    @Override
    public boolean addStatus(String status) {
        try {
            insertRole.setString(1, status);
            insertRole.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
