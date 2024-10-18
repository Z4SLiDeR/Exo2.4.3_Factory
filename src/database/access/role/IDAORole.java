package database.access.role;

import java.util.ArrayList;

public interface IDAORole {
    boolean addRoles(String status);
    boolean updateRoles(int id, String status);
    boolean deleteRoles(int id);
    ArrayList<Roles> getRoles();
    int getIDStatus(String status);
}
