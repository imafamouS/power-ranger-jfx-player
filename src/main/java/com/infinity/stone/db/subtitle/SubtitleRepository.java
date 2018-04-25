package com.infinity.stone.db.subtitle;

import com.infinity.stone.db.core.Repository;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface SubtitleRepository extends Repository<SubtitleDao> {
    
    Single<Long> create(Subtitle subtitle);
    
    Single<Long> create(List<Subtitle> subtitles);
    
    Single<Long> delete(Subtitle subtitle);
    
    Single<Long> deleteByVideoId(String videoId);
    
    Single<List<Subtitle>> findAllSubtitleByVideoId(String videoId);
    
}
