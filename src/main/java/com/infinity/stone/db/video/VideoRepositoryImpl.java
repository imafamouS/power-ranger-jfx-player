package com.infinity.stone.db.video;

import com.infinity.stone.exception.VideoException;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public class VideoRepositoryImpl implements VideoRepository {
    
    private final VideoDao mVideoDao;
    
    public VideoRepositoryImpl() {
        mVideoDao = new VideoDaoImpl();
    }
    
    public long _create(Video video) {
        try {
            return mVideoDao.create(video);
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public long _delete(Video video) {
        try {
            return mVideoDao.delete(video);
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public List<Video> _findAll() {
        try {
            return mVideoDao.findAll();
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return null;
        }
    }
    
    public Video _findById(String id) {
        try {
            return mVideoDao.findById(id);
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return null;
        }
    }
    
    
    @Override
    public Single<Long> create(Video video) {
        return Single.fromCallable(() -> mVideoDao.create(video))
                  .onErrorResumeNext(
                            throwable -> Single.error(new VideoException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Long> delete(Video video) {
        return Single.fromCallable(() -> mVideoDao.delete(video))
                  .onErrorResumeNext(
                            throwable -> Single.error(new VideoException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<List<Video>> findAll() {
        return Single.fromCallable(mVideoDao::findAll)
                  .onErrorResumeNext(
                            throwable -> Single.error(new VideoException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Video> findById(String id) {
        return Single.fromCallable(() -> mVideoDao.findById(id))
                  .onErrorResumeNext(
                            throwable -> Single.error(new VideoException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public VideoDao getDao() {
        return mVideoDao;
    }
}
