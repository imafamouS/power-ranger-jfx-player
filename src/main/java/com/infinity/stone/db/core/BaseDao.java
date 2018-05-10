package com.infinity.stone.db.core;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface BaseDao<Entity> {
    
    long create(Entity entity) throws SQLException;
    
    long update(Entity entity) throws SQLException;
    
    long delete(Entity entity) throws SQLException;
    
    List<Entity> findAll() throws SQLException;
    
    Entity findById(String id) throws SQLException;
}
