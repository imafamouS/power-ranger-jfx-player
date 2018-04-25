package com.infinity.stone.db.subtitle;

import com.infinity.stone.db.core.ContentProvider;
import com.infinity.stone.db.core.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public class SubtitleDaoImpl extends ContentProvider<Subtitle> implements SubtitleDao {
    
    private final DatabaseHelper mDatabaseHelper;
    
    public SubtitleDaoImpl() {
        this.mDatabaseHelper = DatabaseHelper.getInstance();
    }
    
    @Override
    public long create(Subtitle subtitle) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = "INSERT INTO " + Properties.TABLE_NAME + " VALUES (?, ?, ?, ?)";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return executeInsert(preparedStatement, subtitle);
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long create(List<Subtitle> subtitles) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = "INSERT INTO " + Properties.TABLE_NAME + " VALUES (?, ?, ?, ?)";
            long result = 0;
            for (Subtitle subtitle : subtitles) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                result += executeInsert(preparedStatement, subtitle);
            }
            return result;
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long update(String videoId, List<Subtitle> subtitles) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            long resultDelete = deleteByVideoId(videoId);
            long resultInsert = 0;
            if (resultDelete > 0) {
                resultInsert = create(subtitles);
            }
            if (resultInsert > 0) {
                return 1L;
            } else {
                return -1L;
            }
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long update(Subtitle subtitle) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = "UPDATE " + getTableName() +
                         " SET video_id = ?, time_start = ?, content = ?" + " WHERE " +
                         Properties.ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subtitle.getVideoId());
            preparedStatement.setString(2, subtitle.getTimeStart());
            preparedStatement.setString(3, subtitle.getContent());
            preparedStatement.setString(4, subtitle.getId());
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long delete(Subtitle subtitle) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "DELETE FROM " + Properties.TABLE_NAME + " WHERE " + Properties.ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subtitle.getId());
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Subtitle> findAll() throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            return fetchAllFromResultSet(preparedStatement.executeQuery());
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public Subtitle findById(String id) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName() + " WHERE " + Properties.ID + " = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.first()) {
                return new Subtitle(resultSet);
            } else {
                return null;
            }
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long deleteByVideoId(String videoId) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "DELETE FROM " + Properties.TABLE_NAME + " WHERE " + Properties.VIDEO_ID +
                      " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, videoId);
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Subtitle> findAllSubtitleByVideoId(String videoId) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName() + " WHERE " + Properties.VIDEO_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, videoId);
            
            return fetchAllFromResultSet(preparedStatement.executeQuery());
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    protected List<Subtitle> fetchAllFromResultSet(ResultSet resultSet) throws SQLException {
        List<Subtitle> list = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                list.add(new Subtitle(resultSet));
            }
        }
        return list;
    }
    
    @Override
    protected String createTableQuery() {
        return "CREATE TABLE `tbl_subtitle` ( `id` TEXT NOT NULL, `video_id` TEXT, `time_start` TEXT, `content` TEXT, PRIMARY KEY(`id`) )";
    }
    
    @Override
    protected String[] getAllColumns() {
        return Properties.toArray();
    }
    
    @Override
    protected String getTableName() {
        return Properties.TABLE_NAME;
    }
    
    private long executeInsert(PreparedStatement preparedStatement, Subtitle subtitle)
              throws SQLException {
        preparedStatement.setString(1, subtitle.getId());
        preparedStatement.setString(2, subtitle.getVideoId());
        preparedStatement.setString(3, subtitle.getTimeStart());
        preparedStatement.setString(4, subtitle.getContent());
        
        return preparedStatement.executeUpdate();
    }
    
    public static class Properties {
        
        public static final String TABLE_NAME = "tbl_subtitle";
        public static final String ID = "id";
        public static final String VIDEO_ID = "video_id";
        public static final String TIME_START = "time_start";
        public static final String CONTENT = "content";
        
        public static String[] toArray() {
            return new String[]{ID, VIDEO_ID, TIME_START, CONTENT};
        }
    }
    
}
