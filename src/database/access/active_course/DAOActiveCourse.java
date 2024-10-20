package database.access.active_course;

import school.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOActiveCourse implements IDAOActiveCourse {
    private final Connection connection;

    PreparedStatement insertActiveCourse;
    PreparedStatement updateActiveCourse;
    PreparedStatement deleteActiveCourse;
    PreparedStatement getActiveCourseByUserIdAndCourseId;
    PreparedStatement getActiveCourses;

    public DAOActiveCourse(Connection connection) {
        this.connection = connection;

        try {
            // Création de la table si elle n'existe pas
            createTable();
            // Préparation des requêtes
            this.insertActiveCourse = connection.prepareStatement("INSERT INTO ActiveCourses (userId, courseId, year) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            this.updateActiveCourse = connection.prepareStatement("UPDATE ActiveCourses SET year = ? WHERE userId = ? AND courseId = ?");
            this.deleteActiveCourse = connection.prepareStatement("DELETE FROM ActiveCourses WHERE userId = ? AND courseId = ?");
            this.getActiveCourseByUserIdAndCourseId = connection.prepareStatement("SELECT ac.userId, ac.courseId, ac.year, u.lastName, u.firstName, r.id AS roleId, r.name AS roleName, c.id AS courseId, c.name AS courseName, s.id AS sectionId, s.name AS sectionName FROM ActiveCourses ac JOIN Users u ON ac.userId = u.id JOIN Roles r ON u.roleId = r.id JOIN Courses c ON ac.courseId = c.id JOIN Sections s ON c.sectionId = s.id WHERE ac.userId = ? AND ac.courseId = ?");
            this.getActiveCourses = connection.prepareStatement("SELECT ac.userId, ac.courseId, ac.year, u.lastName, u.firstName, r.id AS roleId, r.name AS roleName, c.id AS courseId, c.name AS courseName, s.id AS sectionId, s.name AS sectionName FROM ActiveCourses ac JOIN Users u ON ac.userId = u.id JOIN Roles r ON u.roleId = r.id JOIN Courses c ON ac.courseId = c.id JOIN Sections s ON c.sectionId = s.id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS ActiveCourses (userId INT REFERENCES Users(id), courseId INT REFERENCES Courses(id), year INT, PRIMARY KEY (userId, courseId, year))";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(createTableQuery);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ActiveCourse addActiveCourse(User user, Course course, int year) {
        try {
            insertActiveCourse.setInt(1, user.getId());
            insertActiveCourse.setInt(2, course.getId());
            insertActiveCourse.setInt(3, year);
            int affectedRows = insertActiveCourse.executeUpdate();

            if (affectedRows > 0) {
                return new ActiveCourse(user, course, year);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateActiveCourse(ActiveCourse activeCourse) {
        try {
            updateActiveCourse.setInt(1, activeCourse.getYears());
            updateActiveCourse.setInt(2, activeCourse.getUser().getId());
            updateActiveCourse.setInt(3, activeCourse.getCourse().getId());
            int affectedRows = updateActiveCourse.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteActiveCourse(ActiveCourse activeCourse) {
        try {
            deleteActiveCourse.setInt(1, activeCourse.getUser().getId());
            deleteActiveCourse.setInt(2, activeCourse.getCourse().getId());
            int affectedRows = deleteActiveCourse.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public ActiveCourse getActiveCourseByUserIdAndCourseId(int userId, int courseId) {
        try {
            getActiveCourseByUserIdAndCourseId.setInt(1, userId);
            getActiveCourseByUserIdAndCourseId.setInt(2, courseId);
            ResultSet resultSet = getActiveCourseByUserIdAndCourseId.executeQuery();
            if (resultSet.next()) {
                int year = resultSet.getInt("year");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");
                int sectionId = resultSet.getInt("sectionId");
                String sectionName = resultSet.getString("sectionName");
                String courseName = resultSet.getString("courseName");

                Role role = new Role(roleId, roleName);
                User user = new User(userId, lastName, firstName, role);
                Type section = new Type(sectionId, sectionName);
                Course course = new Course(courseId, section, courseName);
                return new ActiveCourse(user, course, year);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<ActiveCourse> getActiveCourses() {
        ArrayList<ActiveCourse> activeCourses = new ArrayList<>();
        try (ResultSet resultSet = getActiveCourses.executeQuery()) {
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                int courseId = resultSet.getInt("courseId");
                int year = resultSet.getInt("year");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                int roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");
                int sectionId = resultSet.getInt("sectionId");
                String sectionName = resultSet.getString("sectionName");
                String courseName = resultSet.getString("courseName");

                Role role = new Role(roleId, roleName);
                User user = new User(userId, lastName, firstName, role);
                Type section = new Type(sectionId, sectionName);
                Course course = new Course(courseId, section, courseName);
                activeCourses.add(new ActiveCourse(user, course, year));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return activeCourses;
    }
}
