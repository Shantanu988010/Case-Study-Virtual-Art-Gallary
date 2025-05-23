package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DbConnectionException;

public class DbConnectionUtil {

    static {
        try {
            Class.forName(DbProperties.getDriver());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getDbConnection() throws DbConnectionException {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DbProperties.getDbUrl(),DbProperties.getProps());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new DbConnectionException(HexaConstants.CANNOT_OPEN_CONNECTION);
        }
        return con;

    }

    public static void closeConnection(Connection con) {
        try {
            if(con !=null)  con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
