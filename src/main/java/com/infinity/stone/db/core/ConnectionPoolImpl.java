package com.infinity.stone.db.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/23/18.
 */

public class ConnectionPoolImpl implements ConnectionPool {
    
    public static final int MAX_CONNECTION = 10;
    
    private static final Object BLOCK = new Object();
    
    private static ConnectionPoolImpl INSTANCE;
    private final ClientDatabaseType mDatabaseType;
    private final String mUrl;
    private String mUserName;
    private String mPassword;
    private final List<Connection> mAvailableConnections = new ArrayList<>();
    private final List<Connection> mUsedConnections = new ArrayList<>();
    private ConnectionPoolImpl(ClientDatabaseType type, String url) throws SQLException {
        this.mDatabaseType = type;
        this.mUrl = url;
        init();
    }
    
    private ConnectionPoolImpl(String url, String userName, String password) throws SQLException {
        this.mUrl = url;
        this.mUserName = userName;
        this.mPassword = password;
        this.mDatabaseType = ClientDatabaseType.MYSQL;
        init();
    }
    
    public static ConnectionPoolImpl getInstance(ClientDatabaseType type, String url)
              throws SQLException {
        if (INSTANCE == null) {
            synchronized (BLOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionPoolImpl(type, url);
                }
            }
        }
        return INSTANCE;
    }
    
    @Override
    public void init() throws SQLException {
        for (int count = 0; count < MAX_CONNECTION; count++) {
            mAvailableConnections.add(createConnection());
        }
    }
    
    @Override
    public Connection createConnection() throws SQLException {
        try {
            Class.forName(mDatabaseType.mClassName);
            return DriverManager.getConnection(mUrl);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Not support classname" + mDatabaseType.mClassName);
        }
    }
    
    @Override
    public synchronized Connection get() {
        if (mAvailableConnections.size() == 0) {
            System.out.println("All connections are Used !!");
            return null;
        } else {
            Connection con =
                      mAvailableConnections.remove(
                                mAvailableConnections.size() - 1);
            mUsedConnections.add(con);
            return con;
        }
    }
    
    @Override
    public synchronized boolean release(Connection connection) {
        if (connection != null) {
            mUsedConnections.remove(connection);
            mAvailableConnections.add(connection);
            return true;
        }
        return false;
    }
    
    @Override
    public synchronized boolean isAvailableConnection() {
        return mAvailableConnections.size() > 0;
    }
    
    @Override
    public int sizePool() {
        return MAX_CONNECTION;
    }
}
