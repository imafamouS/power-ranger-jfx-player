package com.infinity.stone.util;

import java.net.URL;

/**
 * Created by infamouSs on 4/8/18.
 */

public class ResourceUtils {
    
    public static final String LAYOUT_PATH = "layout/";
    public static final String RAW_PATH = "raw/";
    
    private static ResourceUtils INSTANCE;
    
    private static final Object LOCK = new Object();
    
    private ResourceUtils() {
    
    }
    
    public static ResourceUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new ResourceUtils();
            }
        }
        return INSTANCE;
    }
    
    public URL load(String name) {
        
        URL url = getClass().getClassLoader().getResource(name);
        
        if (url == null) {
            throw new RuntimeException("Can not find resource with" + name);
        }
        return url;
    }
    
    public URL loadLayout(String name) {
        return load(LAYOUT_PATH + name);
    }
    
    public URL loadRaw(String name) {
        return load(RAW_PATH + name);
    }
    
}
