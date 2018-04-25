package com.infinity.stone.db.video;

import com.infinity.stone.db.core.Repository;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface VideoRepository extends Repository<VideoDao> {
    
    Single<Long> create(Video video);
    
    Single<Long> delete(Video video);
    
    Single<List<Video>> findAll();
    
    Single<Video> findById(String id);
}
