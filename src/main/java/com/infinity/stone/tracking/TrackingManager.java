package com.infinity.stone.tracking;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by infamouSs on 4/24/18.
 */

public class TrackingManager implements TrackingService {
    
    public static final TrackingManager INSTANCE = new TrackingManager();
    private static final Logger LOGGER = Logger.getLogger("TheGoodPlayer");
    
    static {
        try {
            String userPath = System.getProperty("user.home");
            File file = new File(userPath + "/TheGoodPlayer");
            file.mkdir();
            FileHandler fh = new FileHandler(
                      file.getAbsolutePath() + "/log-" + System.currentTimeMillis() + ".log");
            LOGGER.addHandler(fh);
            fh.setFormatter(new SimpleFormatter() {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";
                
                @Override
                public synchronized String format(LogRecord lr) {
                    return String.format(format,
                              new Date(lr.getMillis()),
                              lr.getLevel().getLocalizedName(),
                              lr.getMessage()
                    );
                }
            });
        } catch (Exception ignored) {
        
        }
    }
    
    private TrackingManager() {
    
    }
    
    public static TrackingManager getInstance() {
        return INSTANCE;
    }
    
    private String actionTag(Action action) {
        return "[" + action.name + "]: ";
    }
    
    @Override
    public void track(Action action, Object data) {
        LOGGER.log(action.mLevel, actionTag(action) + data.toString());
    }
    
    @Override
    public void track(Action action, List data) {
        for (Object i : data) {
            LOGGER.log(action.mLevel, actionTag(action) + i.toString());
        }
    }
    
    @Override
    public void track(Action action, String... data) {
        for (String i : data) {
            LOGGER.log(action.mLevel, actionTag(action) + i);
        }
    }
    
    @Override
    public void track(Action action, Exception data) {
        LOGGER.log(Level.INFO, actionTag(action) + data.getMessage());
    }
}
