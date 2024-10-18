package database;

public class DatabaseCredentials {

    private final String user;
    private final String password;
    private final String host;
    private final String port;
    private final String dbName;

    public DatabaseCredentials() {
        this.user = "postgres";
        this.password = "password";
        this.host = "127.0.0.1";
        this.port = "5432";
        this.dbName = "ue1396";
    }
    
    public String getPassword() {
        return password;
    }

    public String getUser() {
        return user;
    }

    public String getDbName() {
        return dbName;
    }

    public String toRawURI() {
        return new StringBuilder()
                .append("jdbc:postgresql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .toString();
    }

    public String toURI() {
        return new StringBuilder()
                .append("jdbc:postgresql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName)
                .toString();
    }
}
