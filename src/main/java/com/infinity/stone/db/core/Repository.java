package com.infinity.stone.db.core;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface Repository<T extends BaseDao> {
    
    T getDao();
}
