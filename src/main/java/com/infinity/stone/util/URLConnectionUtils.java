package com.infinity.stone.util;

/**
 * Created by infamouSs on 4/24/18.
 */

public class URLConnectionUtils {
    
    private static final String BASE_URL_MYSQL = "jdbc:mysql://%s:%s/%s?user=%s&password=%s";
    private static final String BASE_URL_SQLITE = "jdbc:sqlite::resource:%s.db";
 
    		//"jdbc:sqlite:D:\\Source Java\\power-ranger-jfx-player-master\\power_ranger.db";
    	    
    public static String getURLConnectionMySQL(String host, String port, String databaseName,
              String username,
              String password) {
        return String.format(BASE_URL_MYSQL, host, port, databaseName, username, password);
    }
    
    public static String getURLConnectionDefaultMySQL(String databaseName, String username,
              String password) {
        return getURLConnectionMySQL("localhost", "3306", databaseName, username, password);
    }
    
    public static String getURLSQLiteConnection(String databaseName) {
    	return String.format(BASE_URL_SQLITE, databaseName);
    }
}
