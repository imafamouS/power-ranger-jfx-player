package com.infinity.stone.db.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public abstract class ContentProvider<Entity> {
    
    protected abstract List<Entity> fetchAllFromResultSet(ResultSet resultSet) throws SQLException;
    
    protected abstract String createTableQuery();
    
    protected abstract String[] getAllColumns();
    
    protected abstract String getTableName();
}
