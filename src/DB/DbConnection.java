package DB;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Properties;

public class DbConnection {
    public Connection connect() throws IOException {
        Reader reader = new FileReader("/home/roosevelt/studManagement/src/common/config.properties");
        Properties properties = new Properties();
        properties.load(reader);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(properties.getProperty("db_url"), properties.getProperty("db_userName"), properties.getProperty("db_password"));
            return con;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }
}
