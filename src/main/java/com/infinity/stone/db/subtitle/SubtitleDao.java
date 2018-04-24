package com.infinity.stone.db.subtitle;

import com.infinity.stone.db.core.BaseDao;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface SubtitleDao extends BaseDao<Subtitle> {
    
    long create(List<Subtitle> subtitles) throws SQLException;
    
    long update(String videoId, List<Subtitle> subtitles) throws SQLException;
    
    long deleteByVideoId(String videoId) throws SQLException;
    
    List<Subtitle> findAllSubtitleByVideoId(String videoId) throws SQLException;
}
