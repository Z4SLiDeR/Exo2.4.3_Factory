package database.access.course;

import school.Course;
import school.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOCourse implements IDAOCourse {

    private final Connection connection;

    PreparedStatement insertCourses;
    PreparedStatement updateCourses;
    PreparedStatement deleteCourses;
    PreparedStatement getCourseById;
    PreparedStatement getCourses;
    PreparedStatement getMaxId;

    public DAOCourse(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertCourses = connection.prepareStatement("INSERT INTO Courses (name, typeId) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            this.updateCourses = connection.prepareStatement("UPDATE Courses SET name = ?, typeId = ? WHERE id = ?");
            this.deleteCourses = connection.prepareStatement("DELETE FROM Courses WHERE id = ?");
            this.getCourseById = connection.prepareStatement("SELECT c.id, c.name, s.id AS typeId, s.name AS sectionName FROM Courses c JOIN Types s ON c.typeId = s.id WHERE c.id = ?");
            this.getCourses = connection.prepareStatement("SELECT c.id, c.name, s.id AS typeId, s.name AS sectionName FROM Courses c JOIN Types s ON c.typeId = s.id");
            this.getMaxId = connection.prepareStatement("SELECT MAX(id) AS max_id FROM Courses");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Courses (id SERIAL PRIMARY KEY, name VARCHAR(30), typeId INT REFERENCES Types(id))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            getCourseById.setInt(1, courseId);
            ResultSet resultSet = getCourseById.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int typeId = resultSet.getInt("typeId");
                String sectionName = resultSet.getString("sectionName");

                Type section = new Type(typeId, sectionName);
                return new Course(courseId, section, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Course addCourses(String name, Type section) {
        try {
            insertCourses.setString(1, name);
            insertCourses.setInt(2, section.getId());
            int affectedRows = insertCourses.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = insertCourses.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        return new Course(id, section, name);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateCourses(Course course, String name, Type section) {
        try {
            updateCourses.setString(1, name != null ? name : course.getName());
            updateCourses.setInt(2, section != null ? section.getId() : course.getType().getId());
            updateCourses.setInt(3, course.getId());
            int affectedRows = updateCourses.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateCourses(Course course, String name) {
        return updateCourses(course, name, null);
    }

    public boolean updateCourses(Course course, Type section) {
        return updateCourses(course, null, section);
    }

    @Override
    public boolean deleteCourses(Course course) {
        try {
            deleteCourses.setInt(1, course.getId());
            int affectedRows = deleteCourses.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try (ResultSet resultSet = getCourses.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int typeId = resultSet.getInt("typeId");
                String sectionName = resultSet.getString("sectionName");

                Type section = new Type(typeId, sectionName);
                Course course = new Course(id, section, name);
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    @Override
    public int getNextId() {
        try {
            ResultSet resultSet = getMaxId.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }
}