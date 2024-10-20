package database.access.type;

import school.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOType implements IDAOType {
    private final Connection connection;

    PreparedStatement insertType;
    PreparedStatement updateType;
    PreparedStatement deleteType;
    PreparedStatement getTypeById;
    PreparedStatement getTypes;
    PreparedStatement getMaxId;

    public DAOType(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertType = connection.prepareStatement("INSERT INTO Types (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            this.updateType = connection.prepareStatement("UPDATE Types SET name = ? WHERE id = ?");
            this.deleteType = connection.prepareStatement("DELETE FROM Types WHERE id = ?");
            this.getTypeById = connection.prepareStatement("SELECT id, name FROM Types WHERE id = ?");
            this.getTypes = connection.prepareStatement("SELECT id, name FROM Types");
            this.getMaxId = connection.prepareStatement("SELECT MAX(id) AS max_id FROM Types");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Types (id SERIAL PRIMARY KEY, name VARCHAR(30))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type addType(String name) {
        try {
            insertType.setString(1, name);
            int affectedRows = insertType.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertType.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new Type(id, name);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateType(Type type) {
        try {
            updateType.setString(1, type.getName());
            updateType.setInt(2, type.getId());
            int affectedRows = updateType.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteType(Type type) {
        try {
            deleteType.setInt(1, type.getId());
            int affectedRows = deleteType.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Type getTypeById(int typeId) {
        try {
            getTypeById.setInt(1, typeId);
            ResultSet resultSet = getTypeById.executeQuery();
            if (resultSet.next()) {
                String typeName = resultSet.getString("name");
                return new Type(typeId, typeName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Type(typeId, "Default Type Name");
    }

    @Override
    public ArrayList<Type> getTypes() {
        ArrayList<Type> types = new ArrayList<>();
        try (ResultSet resultSet = getTypes.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                types.add(new Type(id, name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return types;
    }
}
