package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DBConnection {

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TrendMallDB", "postgres", "AmrF.C.B");
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Connection Failed");
            }
        }
        return connection;
    }
}
