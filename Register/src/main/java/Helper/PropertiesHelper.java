package Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHelper {

    public PropertiesHelper(){
        setLocalProperties();
    }

    public Properties localProperties = new Properties();

    public void setLocalProperties(){
        try (InputStream input = PropertiesHelper.class.getClassLoader().getResourceAsStream("/database.properties")) {
            // load a properties file
           localProperties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
