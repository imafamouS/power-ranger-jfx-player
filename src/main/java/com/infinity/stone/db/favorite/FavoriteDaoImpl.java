package com.infinity.stone.db.favorite;

import com.infinity.stone.db.core.ContentProvider;
import com.infinity.stone.db.core.DatabaseHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by infamouSs on 4/24/18.
 */

public class FavoriteDaoImpl extends ContentProvider<Favorite> implements FavoriteDao {
    
    private static final Logger LOG = Logger.getLogger("FavoriteDao");
    
    private final DatabaseHelper mDatabaseHelper;
    
    public FavoriteDaoImpl() {
        this.mDatabaseHelper = DatabaseHelper.getInstance();
    }
    
    @Override
    public long create(Favorite favorite) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = createQuery();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, favorite.getVideoId());
            preparedStatement.setString(2, favorite.getSubId());
            
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
            return -1L;
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long update(Favorite favorite) throws SQLException {
        return -1;
    }
    
    @Override
    public long delete(Favorite favorite) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = deleteQuery();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setString(1, favorite.getVideoId());
            preparedStatement.setString(2, favorite.getSubId());
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Favorite> findFavoritesByVideoId(String videoId) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "SELECT * FROM " + getTableName() + " WHERE " + Properties.VIDEO_ID + " = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, videoId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            return fetchAllFromResultSet(resultSet);
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public long deleteByVideoId(String videoId) throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql =
                      "DELETE FROM " + getTableName() + " WHERE " + Properties.VIDEO_ID + "= ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, videoId);
            
            return preparedStatement.executeUpdate();
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public List<Favorite> findAll() throws SQLException {
        Connection connection = mDatabaseHelper.getConnection();
        try {
            String sql = "SELECT * FROM " + getTableName();
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            return fetchAllFromResultSet(resultSet);
        } finally {
            mDatabaseHelper.releaseConnection(connection);
        }
    }
    
    @Override
    public Favorite findById(String id) throws SQLException {
        return null;
    }
    
    private String createQuery() {
        return "INSERT INTO " + getTableName() + " " + "VALUES (?, ?)";
    }
    
    private String deleteQuery() {
        return "DELETE FROM " + getTableName() + " WHERE " + Properties.VIDEO_ID + "= ? AND " +
               Properties.SUBTITLE_ID + "= ?";
    }
    
    @Override
    protected List<Favorite> fetchAllFromResultSet(ResultSet resultSet) throws SQLException {
        List<Favorite> favorites = new ArrayList<>();
        if (resultSet != null) {
            while (resultSet.next()) {
                favorites.add(new Favorite(resultSet));
            }
        }
        
        return favorites;
    }
    
    @Override
    protected String createTableQuery() {
        return "CREATE TABLE `tbl_favorite` ( `video_id` TEXT, `sub_id` TEXT, PRIMARY KEY(`video_id`,`sub_id`) )";
        
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
        
        public static final String TABLE_NAME = "tbl_favorite";
        public static final String VIDEO_ID = "video_id";
        public static final String SUBTITLE_ID = "sub_id";
        
        public static String[] toArray() {
            return new String[]{VIDEO_ID, SUBTITLE_ID};
        }
    }
}
