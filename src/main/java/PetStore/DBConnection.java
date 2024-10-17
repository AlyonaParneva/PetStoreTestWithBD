package PetStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url_db="jdbc:postgresql://localhost:5432/petStore";
    private static final String user_db="postgres";
    private static final String password_db="1305";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url_db,user_db,password_db);
    }
}
