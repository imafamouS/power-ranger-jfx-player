package com.infinity.stone.db.favorite;

import com.infinity.stone.exception.FavoriteException;
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
