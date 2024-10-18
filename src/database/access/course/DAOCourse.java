package database.access.course;

import school.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCourse implements IDAOCourse {

    private final Connection connection;

    PreparedStatement insertCourses;
    PreparedStatement updateCourses;
    PreparedStatement deleteCourses;
    PreparedStatement getIDCourses;
    PreparedStatement getCourses;

    public DAOCourse(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertCourses = connection.prepareStatement("INSERT INTO Cours (nom, id_section) VALUES (?, ?)");
            this.updateCourses = connection.prepareStatement("UPDATE Cours SET nom = ?, id_section = ? WHERE id = ?");
            this.deleteCourses = connection.prepareStatement("DELETE FROM Cours WHERE id = ?");
            this.getIDCourses = connection.prepareStatement("SELECT id FROM Cours WHERE nom = ?");
            this.getCourses = connection.prepareStatement("SELECT id, nom, id_section FROM Cours");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Cours (id SERIAL PRIMARY KEY, nom VARCHAR(30), id_section INT REFERENCES Section(id))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCoursesId(String nom) {
        return 0;
    }

    @Override
    public Course addCourses(String nom, int idSection) {
        try {
            insertCourses.setString(1, nom);
            insertCourses.setInt(2, idSection);
            insertCourses.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCourses(int id, String nom, int idSection) {
        return false;
    }

    @Override
    public boolean deleteCourses(int id) {
        return false;
    }

    @Override
    public ArrayList<Course> getCourses() {
        return null;
    }
}