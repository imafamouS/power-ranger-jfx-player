package com.infinity.stone.db.video;

import com.infinity.stone.db.core.ContentProvider;
import com.infinity.stone.db.core.DatabaseHelper;
import com.infinity.stone.db.subtitle.SubtitleDaoImpl;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public class VideoDaoImpl extends ContentProvider<Video> implements VideoDao {
    
    private final DatabaseHelper mDatabaseHelper;
    
    public VideoDaoImpl() {
        mDatabaseHelper = DatabaseHelper.getInstance();
    }
    
    @Override
    public long create(Video video) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            List<Video> list = findByVideoPath(video.getUrl());
            if (list != null && list.isEmpty()) {
                String sql = "INSERT INTO " + Properties.TABLE_NAME + " VALUES (?, ?, ?, ?)";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, video.getId());
                preparedStatement.setString(2, video.getYoutubeId());
                preparedStatement.setString(3, video.getUrl());
                preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
                
                return preparedStatement.executeUpdate();
            } else {
                return 0L;
            }
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long update(Video video) throws SQLException {
        return 0;
    }
    
    @Override
    public long delete(Video video) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "DELETE FROM " + Properties.TABLE_NAME + " WHERE " + Properties.ID + " = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, video.getId());
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Video> findAll() throws SQLException {
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
    
    public List<Video> findByVideoPath(String path) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName() + " WHERE url = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, path);
            
            return fetchAllFromResultSet(preparedStatement.executeQuery());
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public Video findById(String id) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName() + " WHERE " +
                      SubtitleDaoImpl.Properties.ID + " = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.first()) {
                return new Video(resultSet);
            } else {
                return null;
            }
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    protected List<Video> fetchAllFromResultSet(ResultSet resultSet) throws SQLException {
        List<Video> list = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                list.add(new Video(resultSet));
            }
        }
        return list;
    }
    
    @Override
    protected String createTableQuery() {
        return "CREATE TABLE `tbl_video` ( `id` TEXT NOT NULL, `youtube_id` TEXT, `url` TEXT, `date_created` TEXT, PRIMARY KEY(`id`) )";
    }
    
    @Override
    protected String[] getAllColumns() {
        return Properties.toArray();
    }
    
    @Override
    protected String getTableName() {
        return Properties.TABLE_NAME;
    }
    
    public static class Properties {
        
        public static final String TABLE_NAME = "tbl_video";
        public static final String ID = "id";
        public static final String YOUTUBE_ID = "youtube_id";
        public static final String URL = "url";
        public static final String DATE_CREATED = "date_created";
        
        public static String[] toArray() {
            return new String[]{ID, YOUTUBE_ID, URL, DATE_CREATED};
        }
    }
    
}
