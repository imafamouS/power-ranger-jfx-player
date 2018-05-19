package com.infinity.stone.db.favorite;

import com.infinity.stone.exception.FavoriteException;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public class FavoriteRepositoryImpl implements FavoriteRepository {
    
    private final FavoriteDao mFavoriteDao;
    
    public FavoriteRepositoryImpl() {
        mFavoriteDao = new FavoriteDaoImpl();
    }
    
    public long _create(Favorite favorite) {
        try {
            return mFavoriteDao.create(favorite);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public long _delete(Favorite favorite) {
        try {
            return mFavoriteDao.delete(favorite);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public List<Favorite> _findFavoriteByVideoId(String videoId) {
        try {
            return mFavoriteDao.findFavoritesByVideoId(videoId);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return null;
        }
    }
    
    public long deleteByVideoId(String videoId) {
        try {
            return mFavoriteDao.deleteByVideoId(videoId);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    @Override
    public Single<Long> create(Favorite favorite) {
        return Single.fromCallable(() -> mFavoriteDao.create(favorite))
                  .onErrorResumeNext(
                            throwable -> Single.error(new FavoriteException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Long> delete(Favorite favorite) {
        return Single.fromCallable(() -> mFavoriteDao.delete(favorite))
                  .onErrorResumeNext(
                            throwable -> Single.error(new FavoriteException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<List<Favorite>> findFavoritesByVideoId(String videoId) {
        return Single.fromCallable(() -> mFavoriteDao.findFavoritesByVideoId(videoId))
                  .onErrorResumeNext(
                            throwable -> Single.error(new FavoriteException(throwable.getMessage(),
                                      throwable)));
    }
    
    public FavoriteDao getDao() {
        return mFavoriteDao;
    }
}
