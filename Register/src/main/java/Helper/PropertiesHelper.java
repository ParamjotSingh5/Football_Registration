package Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    public static Properties localProperties = new Properties();

    static {
        setLocalProperties();
    }

    public static void setLocalProperties(){
        try (InputStream input = new FileInputStream("src/main/java/configuration/database.properties")) {
            // load a properties file
            localProperties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
