package com.infinity.stone.db.video;

import com.infinity.stone.exception.VideoException;
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
