package com.infinity.stone.db.favorite;

import com.infinity.stone.db.core.BaseDao;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface FavoriteDao extends BaseDao<Favorite> {
    
    List<Favorite> findFavoritesByVideoId(String videoId) throws SQLException;
    
    long deleteByVideoId(String videoId) throws SQLException;
}
