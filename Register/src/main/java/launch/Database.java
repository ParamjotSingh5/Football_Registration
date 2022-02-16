package launch;

import Utilities.GenericResponse;
import Utilities.ValidationMessages;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.coyote.http2.Stream;

import static Helper.PropertiesHelper.localProperties;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

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

        try {
            //get the property value and print it out
            String url = localProperties.getProperty("DATABASE_BASE_URL") + localProperties.getProperty("DATABASE");
            String username =  localProperties.getProperty("DATABASE_USERNAME");
            String pass = localProperties.getProperty("DATABASE_PASSWORD");

            System.out.println("Database connection properties. URL: " + url + " , username: " + username +" , pass: " + pass);

            return connect(url, username, pass);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void ConfigureDatabase() throws Exception{

        System.out.println("Checking Database configurations...");

        GenericResponse parentRes = new GenericResponse();

        parentRes = LoginToDatabaseServer();

        if(!parentRes.status || !parentRes.success){
            System.out.println(ValidationMessages.DROPPING_WEBAPP_START_REQUEST.toString() + " source error: " + parentRes.message);
        }

        Connection dbCon = (Connection) parentRes.data;

        parentRes = CheckIfDatabaseExists(dbCon);

        if(!parentRes.status){
            System.out.println(ValidationMessages.DROPPING_WEBAPP_START_REQUEST.toString() + " source error: " + parentRes.message);
            throw new Exception(parentRes.message);
        }

        if(parentRes.success){
            System.out.println("Connection to database is successful. Closing the database connection.");
            dbCon.close();

            return;
        }

        System.out.println("Database server does not have an instance of the application integrated database.");

        parentRes = CreateInitialDatabase(dbCon);

        if(!parentRes.status || !parentRes.success){
            System.out.println(ValidationMessages.DROPPING_WEBAPP_START_REQUEST.toString() + " source error: " + parentRes.message);
            throw new Exception(parentRes.message);
        }
    }

    private GenericResponse LoginToDatabaseServer(){
        try {

            System.out.println("Logging into Database server.");

            //get the property value and print it out
            String url = localProperties.getProperty("DATABASE_BASE_URL");
            String username =  localProperties.getProperty("DATABASE_USERNAME");
            String pass = localProperties.getProperty("DATABASE_PASSWORD");

            System.out.println("Database connection properties. URL: " + url + " , username: " + username +" , pass: " + pass);

            Connection con = connect(url, username, pass);

            System.out.println("Successfully logged into the Database server.");

            return new GenericResponse(true, true, "Connection successful.", con);
        }
        catch (Exception ex) {
            System.out.println("An error occurred while Logging into the Database server.");
            System.out.println( ex.getMessage());

            return new GenericResponse(false, false, ex.getMessage());
        }
    }

    private GenericResponse CheckIfDatabaseExists(Connection dbCon){

        try{
            boolean isDatabaseExistsFlag = false;

            ResultSet resultSet = dbCon.getMetaData().getCatalogs();

            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
                if(Objects.equals(databaseName, localProperties.getProperty("DATABASE_NAME"))){
                    isDatabaseExistsFlag = true;
                }
            }

            resultSet.close();

            return new GenericResponse(true, isDatabaseExistsFlag,"");
        }
        catch (SQLException ex){
            System.out.println("An error occurred while ");
            System.out.println(ex.getMessage());

            return new GenericResponse(true, false, ex.getMessage());
        }
    }

    private GenericResponse ConnectToDatabase() {
        try {
            //get the property value and print it out
            String url = localProperties.getProperty("DATABASE_BASE_URL") + localProperties.getProperty("DATABASE");
            String username =  localProperties.getProperty("DATABASE_USERNAME");
            String pass = localProperties.getProperty("DATABASE_PASSWORD");

            System.out.println("Database connection properties. URL: " + url + " , username: " + username +" , pass: " + pass);

            Connection con = connect(url, username, pass);

            System.out.println("Successfully connected to the server database.");

            return new GenericResponse(true, true, "Connection successful to database.", con);
        }
        catch (SQLServerException ex){
            System.out.println("Unable to open connection with Database.");
            System.out.println(ex.getMessage());

            return new GenericResponse(true, false, ex.getMessage());
        }
        catch (Exception ex) {
            System.out.println("An unhandled error occurred while Logging into the database.");
            System.out.println( ex.getMessage());

            return new GenericResponse(false, false, ex.getMessage());
        }
    }

    private GenericResponse CreateInitialDatabase(Connection dbCon){
        System.out.println("Initiating process to create a new database.");

        String content = "";

        try{

            FileReader fr = new FileReader("src/main/resources/create_new_database_script.txt") ;
            BufferedReader br = new BufferedReader(fr);

            content = br.lines().parallel().collect(Collectors.joining("\n"));
            System.out.println("generated script: " + content);
        }
        catch (IOException ex){
            System.out.println("An unhandled error occurred while reading 'create_new_database_script.txt' file.");
            ex.printStackTrace();

            return new GenericResponse(false, false, ex.getMessage(), ex);
        }

        try{

            String createdDatabase = "CREATE DATABASE [football_registration_2]\n" +
                    " CONTAINMENT = NONE\n" +
                    " ON  PRIMARY \n" +
                    "( NAME = N'football_registration_2', FILENAME = N'c:\\Program Files\\Microsoft SQL Server\\MSSQL14.MSSQLSERVER\\MSSQL\\DATA\\football_registration_2.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )\n" +
                    " LOG ON \n" +
                    "( NAME = N'football_registration_2_log', FILENAME = N'c:\\Program Files\\Microsoft SQL Server\\MSSQL14.MSSQLSERVER\\MSSQL\\DATA\\football_registration_2_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )\n" +
                    "\n" +
                    "\n" +
                    "COMMIT \n" +
                    "\n" +
                    "--End Batch--\n";

            executeDDL(createdDatabase, dbCon);


            executeDDL(content, dbCon);
            return new GenericResponse(true, true, "database created successfully.");
        }
        catch (SQLException ex){
            System.out.println("An unhandled error occurred while generating a new database.");
            ex.printStackTrace();

            return new GenericResponse(false, false, ex.getMessage(), ex);
        }
    }

    private void executeDDL(String ddl, Connection con) throws SQLException {

        String delimiter = "--End Batch--";

        try {

            // enable transaction
            con.setAutoCommit(false);

            Statement statement = con.createStatement();

            // for every DDL statement, execute it
            for (String sql : ddl.split(delimiter)) {
                if (!sql.isEmpty()) {
                    statement.executeUpdate(sql);
                }
            }

            statement.close();
            con.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();

            throw e;
        }

    }

}
