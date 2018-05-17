package com.infinity.stone.db.subtitle;

import com.infinity.stone.exception.SubtitleException;
import com.infinity.stone.tracking.Action;
import com.infinity.stone.tracking.TrackingManager;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public class SubtitleRepositoryImpl implements SubtitleRepository {
    
    private final SubtitleDao mSubtitleDao;
    
    public SubtitleRepositoryImpl() {
        this.mSubtitleDao = new SubtitleDaoImpl();
    }
    
    
    public long _create(Subtitle subtitle) {
        try {
            return mSubtitleDao.create(subtitle);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public long _create(List<Subtitle> subtitle) {
        try {
            return mSubtitleDao.create(subtitle);
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public long _delete(Subtitle subtitle) {
        try {
            return mSubtitleDao.delete(subtitle);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public long _deleteByVideoId(String videoId) {
        try {
            return mSubtitleDao.deleteByVideoId(videoId);
            
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return -1;
        }
    }
    
    public List<Subtitle> _findAllSubtitleByVideoId(String videoId) {
        try {
            return mSubtitleDao.findAllSubtitleByVideoId(videoId);
        } catch (Exception ex) {
            TrackingManager.getInstance().track(Action.ERROR, ex.getCause());
            return null;
        }
    }
    
    @Override
    public Single<Long> create(Subtitle subtitle) {
        return Single.fromCallable(() -> mSubtitleDao.create(subtitle))
                  .onErrorResumeNext(
                            throwable -> Single.error(new SubtitleException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Long> create(List<Subtitle> subtitles) {
        return Single.fromCallable(() -> mSubtitleDao.create(subtitles))
                  .onErrorResumeNext(
                            throwable -> Single.error(new SubtitleException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Long> delete(Subtitle subtitle) {
        return Single.fromCallable(() -> mSubtitleDao.delete(subtitle))
                  .onErrorResumeNext(
                            throwable -> Single.error(new SubtitleException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<Long> deleteByVideoId(String videoId) {
        return Single.fromCallable(() -> mSubtitleDao.deleteByVideoId(videoId))
                  .onErrorResumeNext(
                            throwable -> Single.error(new SubtitleException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public Single<List<Subtitle>> findAllSubtitleByVideoId(String videoId) {
        return Single.fromCallable(() -> mSubtitleDao.findAllSubtitleByVideoId(videoId))
                  .onErrorResumeNext(
                            throwable -> Single.error(new SubtitleException(throwable.getMessage(),
                                      throwable)));
    }
    
    @Override
    public SubtitleDao getDao() {
        return mSubtitleDao;
    }
}
