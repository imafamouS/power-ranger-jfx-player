package com.infinity.stone.db.core;

import com.infinity.stone.util.Constant;

/**
 * Created by infamouSs on 4/23/18.
 */

public enum ClientDatabaseType {
    MYSQL("com.mysql.jdbc.Driver"),
    SQLITE("org.sqlite.JDBC");
    
    final String mClassName;
    
    ClientDatabaseType(String className) {
        mClassName = className;
    }
    
    public static ClientDatabaseType getByStr(String str) {
        switch (str) {
            case Constant.MYSQL_CLIENT:
                return MYSQL;
            case Constant.SQLITE_CLIENT:
            default:
                return SQLITE;
        }
    }
}
