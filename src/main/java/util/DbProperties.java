package util;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class DbProperties {
    private static Properties props = null;
    public Scanner scanner = new Scanner(System.in);
    static {
        props = getDbProperties();
    }
   private static Properties getDbProperties() {
        Properties props = new Properties();
        try {
            props.load(DbProperties.class.getClassLoader().getResourceAsStream(HexaConstants.DB_FILE_NAME));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return props;
    }

 public static String getDriver() {
        return props.getProperty(HexaConstants.DB_DRIVER);

    }
    public static String getDbUrl() {
        return props.getProperty(HexaConstants.DB_URL);

    }
    public static Properties getProps() {
        return props;

    }
}
