package com.infinity.stone.db.favorite;

import com.infinity.stone.db.core.Repository;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface FavoriteRepository extends Repository<FavoriteDao> {
    
    Single<Long> create(Favorite favorite);
    
    Single<Long> delete(Favorite favorite);
    
    Single<List<Favorite>> findFavoritesByVideoId(String videoId);
}
