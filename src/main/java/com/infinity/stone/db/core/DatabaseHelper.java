package com.infinity.stone.db.core;

import com.infinity.stone.util.Constant;
import com.infinity.stone.util.PropertyUtils;
import com.infinity.stone.util.ResourceUtils;
import com.infinity.stone.util.TextUtils;
import com.infinity.stone.util.URLConnectionUtils;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by infamouSs on 4/23/18.
 */

public class DatabaseHelper {
    
    private static final String DEFAULT_CLIENT_DATABASE = Constant.SQLITE_CLIENT;
    private static final String DEFAULT_DATABASE_NAME = "power_ranger";
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_USERNAME = "";
    private static final String DEFAULT_PASSWORD = "";
    private static Logger LOG = Logger.getLogger("DatabaseHelper");
    private static final DatabaseHelper INSTANCE = new DatabaseHelper();
    private String mURL;
    private ConnectionPool mConnectionPool;
    private ClientDatabaseType mDatabaseType;
    
    
    private DatabaseHelper() {
        initDefault();
        try {
            mConnectionPool = ConnectionPoolImpl.getInstance(mDatabaseType, mURL);
        } catch (SQLException ignored) {

        }
    }
    
    public static DatabaseHelper getInstance() {
        return INSTANCE;
    }
    
    public synchronized Connection getConnection() {
        if (mConnectionPool.isAvailableConnection()) {
            return mConnectionPool.get();
        }
        throw new RuntimeException("Not available connection !");
    }
    
    public synchronized void releaseConnection(Connection connection) {
        mConnectionPool.release(connection);
    }
    
    private void initDefault() {
        this.mDatabaseType = ClientDatabaseType.getByStr(DEFAULT_CLIENT_DATABASE);
        this.mURL = URLConnectionUtils.getURLSQLiteConnection(DEFAULT_DATABASE_NAME);
    }
    
    private void init() {
        URL urlConfigFile = ResourceUtils.getInstance().load("application.properties");
        
        Properties properties = PropertyUtils.getProperties(urlConfigFile);
        
        if (properties != null) {
            String databaseClient = properties
                      .getProperty(Constant.EXTRA_CLIENT_DATABASE, DEFAULT_CLIENT_DATABASE);
            String databaseName = properties
                      .getProperty(Constant.EXTRA_DATABASE_NAME, DEFAULT_CLIENT_DATABASE);
            String userName = properties
                      .getProperty(Constant.EXTRA_USERNAME, DEFAULT_DATABASE_NAME);
            String password = properties.getProperty(Constant.EXTRA_PASSWORD, DEFAULT_PASSWORD);
            
            String host = properties.getProperty(Constant.EXTRA_HOST, DEFAULT_HOST);
            
            String port = properties.getProperty(Constant.EXTRA_PORT, DEFAULT_PORT);
            
            mDatabaseType = ClientDatabaseType.getByStr(databaseClient);
            if (mDatabaseType == ClientDatabaseType.MYSQL) {
                if (TextUtils.isEmpty(host)) {
                    mURL = URLConnectionUtils
                              .getURLConnectionDefaultMySQL(databaseName, userName, password);
                } else {
                    mURL = URLConnectionUtils
                              .getURLConnectionMySQL(host, port, databaseName, userName, password);
                }
            } else if (mDatabaseType == ClientDatabaseType.SQLITE) {
                mURL = URLConnectionUtils.getURLSQLiteConnection(databaseName);
            } else {
                throw new IllegalArgumentException("Not support that database client");
            }
        } else {
            throw new NullPointerException("Not found file config (application.properties)");
        }
    }
}
