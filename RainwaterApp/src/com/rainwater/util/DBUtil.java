package com.rainwater.util;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // adjust as per your DB
    private static final String USER = "system";
    private static final String PASSWORD = "sanjanarr10";

    public static Connection getDBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
