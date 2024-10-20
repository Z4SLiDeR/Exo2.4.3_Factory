package database.access.role;

import school.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAORole implements IDAORole {
    private final Connection connection;

    PreparedStatement insertRole;
    PreparedStatement updateRole;
    PreparedStatement deleteRole;
    PreparedStatement getRoleById;
    PreparedStatement getRoles;
    PreparedStatement getMaxId;

    public DAORole(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertRole = connection.prepareStatement("INSERT INTO Roles (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            this.updateRole = connection.prepareStatement("UPDATE Roles SET name = ? WHERE id = ?");
            this.deleteRole = connection.prepareStatement("DELETE FROM Roles WHERE id = ?");
            this.getRoleById = connection.prepareStatement("SELECT id, name FROM Roles WHERE id = ?");
            this.getRoles = connection.prepareStatement("SELECT id, name FROM Roles");
            this.getMaxId = connection.prepareStatement("SELECT MAX(id) AS max_id FROM Roles");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Roles (id SERIAL PRIMARY KEY, name VARCHAR(30))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Role addRole(String name) {
        try {
            insertRole.setString(1, name);
            int affectedRows = insertRole.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertRole.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new Role(id, name);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateRole(Role role) {
        try {
            updateRole.setString(1, role.getName());
            updateRole.setInt(2, role.getId());
            int affectedRows = updateRole.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteRole(Role role) {
        try {
            deleteRole.setInt(1, role.getId());
            int affectedRows = deleteRole.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Role getRoleById(int roleId) {
        try {
            getRoleById.setInt(1, roleId);
            ResultSet resultSet = getRoleById.executeQuery();
            if (resultSet.next()) {
                String roleName = resultSet.getString("name");
                return new Role(roleId, roleName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Role(roleId, "Default Role Name");
    }

    @Override
    public ArrayList<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();
        try (ResultSet resultSet = getRoles.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                roles.add(new Role(id, name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return roles;
    }
}