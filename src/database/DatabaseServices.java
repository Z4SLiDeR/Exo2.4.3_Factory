package database;

import database.access.active_course.DAOActiveCourse;
import database.access.active_course.IDAOActiveCourse;
import database.access.course.DAOCourse;
import database.access.course.IDAOCourse;
import database.access.role.DAORole;
import database.access.role.IDAORole;
import database.access.type.DAOType;
import database.access.type.IDAOType;
import database.access.user.DAOUser;
import database.access.user.IDAOUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServices {

    private Connection connection;

    public void connect() {
        DatabaseCredentials credentials = new DatabaseCredentials();
        new DatabaseCreator().createDatabase();

        try {
            connection = DriverManager.getConnection(credentials.toURI(), credentials.getUser(), credentials.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void close() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public IDAOType createIDAOType() {
        return new DAOType(getConnection());
    }

    public IDAORole createIDAORole() {
        return new DAORole(getConnection());
    }

    public IDAOUser createIDAOUser() {
        return new DAOUser(getConnection());
    }

    public IDAOCourse createIDAOCourses() {
        return new DAOCourse(getConnection());
    }

    public IDAOActiveCourse createIDAOActiveCourse() {
        return new DAOActiveCourse(getConnection());
    }

}
