package database;

import database.access.course.DAOCourse;
import database.access.course.IDAOCourse;

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

    public IDAOCourse createIDAOCourses() {
        return new DAOCourse(connection);
    }
}
