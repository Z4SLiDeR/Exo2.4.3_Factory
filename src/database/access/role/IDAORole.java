package database.access.role;

import school.Role;

import java.util.ArrayList;

public interface IDAORole {
    Role addRole(String name);
    boolean updateRole(Role role);
    boolean deleteRole(Role role);
    Role getRoleById(int roleId);
    ArrayList<Role> getRoles();
}
