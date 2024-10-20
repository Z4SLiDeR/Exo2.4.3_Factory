package database.access.user;

import school.User;
import school.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOUser implements IDAOUser {
    private final Connection connection;

    PreparedStatement insertUser;
    PreparedStatement updateUser;
    PreparedStatement deleteUser;
    PreparedStatement getUserById;
    PreparedStatement getUsers;
    PreparedStatement getMaxId;

    public DAOUser(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertUser = connection.prepareStatement("INSERT INTO Users (lastName, firstName, roleId) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            this.updateUser = connection.prepareStatement("UPDATE Users SET lastName = ?, firstName = ?, roleId = ? WHERE id = ?");
            this.deleteUser = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            this.getUserById = connection.prepareStatement("SELECT u.id, u.lastName, u.firstName, r.id AS roleId, r.name AS roleName FROM Users u JOIN Roles r ON u.roleId = r.id WHERE u.id = ?");
            this.getUsers = connection.prepareStatement("SELECT u.id, u.lastName, u.firstName, r.id AS roleId, r.name AS roleName FROM Users u JOIN Roles r ON u.roleId = r.id");
            this.getMaxId = connection.prepareStatement("SELECT MAX(id) AS max_id FROM Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY, lastName VARCHAR(15), firstName VARCHAR(15), roleId INT REFERENCES Roles(id))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User addUser(String lastName, String firstName, Role role) {
        try {
            insertUser.setString(1, lastName);
            insertUser.setString(2, firstName);
            insertUser.setInt(3, role.getId());
            int affectedRows = insertUser.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new User(id, lastName, firstName, role);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            updateUser.setString(1, user.getLastName());
            updateUser.setString(2, user.getFirstName());
            updateUser.setInt(3, user.getRole().getId());
            updateUser.setInt(4, user.getId());
            int affectedRows = updateUser.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            deleteUser.setInt(1, user.getId());
            int affectedRows = deleteUser.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(int userId) {
        try {
            getUserById.setInt(1, userId);
            ResultSet resultSet = getUserById.executeQuery();
            if (resultSet.next()) {
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");

                Role role = new Role(roleId, roleName);
                return new User(userId, lastName, firstName, role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try (ResultSet resultSet = getUsers.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");

                Role role = new Role(roleId, roleName);
                users.add(new User(id, lastName, firstName, role));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
