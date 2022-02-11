package launch;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {

    public Connection connect(String URL, String Username, String Password) throws SQLServerException, Exception{

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL,
                    Username, Password);
            System.out.println("connected to database");

            return conn;
        }
        catch (SQLServerException sqlException) {
            System.out.println("Unable to create connection to the database. error: " + sqlException.getMessage());
            throw sqlException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Connection connect() throws SQLServerException, Exception{

        Properties prop = new Properties();
        try {
            //load a properties file from class path, inside static method
            prop.load(new FileInputStream("src/main/java/configuration/database.properties"));

            //get the property value and print it out
            String url = prop.getProperty("DATABASE_BASE_URL") + prop.getProperty("DATABASE_NAME");
            String username =  prop.getProperty("DATABASE_USERNAME");
            String pass = prop.getProperty("DATABASE_PASSWORD");

            System.out.println("Database connection properties. URL: " + url + " , username: " + username +" , pass: " + pass);

            return connect(url, username, pass);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
