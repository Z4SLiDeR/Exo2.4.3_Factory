package database.access.user;

import school.User;
import school.Role;

import java.util.ArrayList;

public interface IDAOUser {
    User addUser(String lastName, String firstName, Role role);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    User getUserById(int userId);
    ArrayList<User> getUsers();
}
