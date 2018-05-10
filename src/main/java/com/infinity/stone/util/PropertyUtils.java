package com.infinity.stone.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by infamouSs on 4/23/18.
 */

public class PropertyUtils {
    
    private static final Logger LOG = Logger.getLogger("PropertyUtils");
    
    public static Properties getProperties(URL path) {
        return getProperties(path.getPath());
    }
    
    public static Properties getProperties(String path) {
        Properties prop = null;
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            prop = new Properties();
            
            prop.load(input);
        } catch (IOException ex) {
            LOG.info("Not found properties at " + path);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
