package com.infinity.stone.db;

import com.infinity.stone.db.core.Repository;
import com.infinity.stone.db.favorite.FavoriteRepository;
import com.infinity.stone.db.favorite.FavoriteRepositoryImpl;
import com.infinity.stone.db.subtitle.SubtitleRepository;
import com.infinity.stone.db.subtitle.SubtitleRepositoryImpl;
import com.infinity.stone.db.video.VideoRepository;
import com.infinity.stone.db.video.VideoRepositoryImpl;

/**
 * Created by infamouSs on 4/24/18.
 */

public class RepositoryManager {
    
    private static final FavoriteRepository INSTANCE_FAVORITE_REPOSITORY = new FavoriteRepositoryImpl();
    private static final SubtitleRepository INSTANCE_SUBTITLE_REPOSITORY = new SubtitleRepositoryImpl();
    private static final VideoRepository INSTANCE_VIDEO_REPOSITORY = new VideoRepositoryImpl();
    
    
    public static Repository getInstance(RepositoryType type) {
        switch (type) {
            case FAVORITE:
                return INSTANCE_FAVORITE_REPOSITORY;
            case SUBTITLE:
                return INSTANCE_SUBTITLE_REPOSITORY;
            case VIDEO:
                return INSTANCE_VIDEO_REPOSITORY;
            default:
                throw new IllegalArgumentException("Not found repository");
        }
    }
    //
    //    public static void main(String[] args) {
    //        try {
    //            SubtitleRepository subtitleRepository = (SubtitleRepository) RepositoryManager
    //                      .getInstance(RepositoryType.SUBTITLE);
    //            List<Subtitle> subtitleList = new ArrayList<>();
    //            for (int i = 0; i < 10; i++) {
    //                subtitleList.add(new Subtitle("id" + i, "videoid" + i, "time" + i, "end" + i,
    //                          "content" + i));
    //            }
    //
    //            subtitleRepository.create(subtitleList)
    //                      .subscribe(System.out::print);
    //        } catch (Exception ex) {
    //
    //        }
    //    }
}
