package com.infinity.stone.db.core;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by infamouSs on 4/23/18.
 */

public interface ConnectionPool {
    
    void init() throws SQLException;
    
    Connection createConnection() throws SQLException;
    
    Connection get();
    
    boolean release(Connection connection);
    
    boolean isAvailableConnection();
    
    int sizePool();
}
