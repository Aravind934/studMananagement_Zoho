package SchoolEntity;

import DB.DbConnection;

import java.io.IOException;
import java.sql.*;


public class Management {
    public DbConnection dbConnection = new DbConnection();
    public Connection con;

    {
        try {
            this.con = this.dbConnection.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Management() {
    }

    //for making single instance
    private static Management management;

    public static Management getInstance() {
        if (management == null) management = new Management();
        return management;
    }

}
