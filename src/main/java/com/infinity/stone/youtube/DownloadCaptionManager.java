package com.infinity.stone.youtube;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Captions.Download;
import com.google.api.services.youtube.model.Caption;
import com.google.api.services.youtube.model.CaptionListResponse;
import com.google.api.services.youtube.model.CaptionSnippet;
import com.google.common.collect.Lists;
import com.infinity.stone.model.CaptionsModel;
import com.infinity.stone.model.SubtitleCollection;
import com.infinity.stone.model.SubtitleModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by infamouSs on 4/19/18.
 */

public class DownloadCaptionManager {
    
    private static final String TTML = "ttml";
    private static final String EXTENSION_TTML = "." + TTML;
    private final static String PATH = "sub/";
    private static final Logger LOG = Logger.getLogger("DownloadCaptionManager");
    
    static {
        boolean makeDir = new File(PATH).mkdir();
    }
    
    private final String CAPTION_FILE_FORMAT = "*/*";
    private final List<String> SCOPES = Lists
              .newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");
    private YouTube mYouTube;
    
    public DownloadCaptionManager() {
        Credential credential;
        try {
            credential = Auth.authorize(SCOPES, "captions");
            mYouTube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                      .setApplicationName("youtube-cmdline-captions-sample").build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        DownloadCaptionManager aa = new DownloadCaptionManager();
        String videoId = "CDj9gkIe5iY";
        
        SubtitleCollection subtitleCollection = aa.downloadSubtitle(videoId);
        
        subtitleCollection.printValue();
    }
    
    public SubtitleCollection downloadSubtitle(String videoId) {
        boolean isSuccessDownloadSubtitle = downloadSubtitleWithVideoIdToFileTTML(videoId);
        SubtitleCollection subtitleCollection = null;
        if (isSuccessDownloadSubtitle) {
            subtitleCollection = buildSubtitleCollectionFromFileTTML(
                      PATH + videoId + EXTENSION_TTML);
        }
        return subtitleCollection;
    }
    
    public SubtitleCollection buildSubtitleCollectionFromFileTTML(String filePath) {
        SubtitleCollection subtitleCollection = null;
        
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements pTags = doc.body().select("p");
            subtitleCollection = new SubtitleCollection();
            for (Element element : pTags) {
                String timeStart = element.attr("begin");
                String sentence = element.text();
                
                SubtitleModel subtitleModel = new SubtitleModel(timeStart, sentence);
                subtitleCollection.add(subtitleModel);
            }
        } catch (IOException ex) {
            LOG.info(ex.getMessage());
        }
        
        return subtitleCollection;
    }
    
    public boolean downloadSubtitleWithVideoIdToFileTTML(String videoId) {
        boolean isSuccess = false;
        try {
            
            CaptionsModel captionsModel = getCaptions(videoId);
            downloadCaption(captionsModel.getId(), videoId);
            
            File file = new File(PATH + videoId + ".ttml");
            
            if (!file.exists()) {
                return false;
            }
            if (file.length() <= 0) {
                boolean isDeleteSuccessFul = file.delete();
                if (isDeleteSuccessFul) {
                    isSuccess = false;
                } else {
                    isSuccess = false;
                }
            } else {
                isSuccess = true;
            }
            
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            File file = new File(PATH + videoId + ".ttml");
            
            if (!file.exists()) {
                return false;
            }
            if (file.length() <= 0) {
                boolean isDeleteSuccessFul = file.delete();
            }
            return false;
        }
        
        return isSuccess;
    }
    
    
    private CaptionsModel getCaptions(String videoId) throws IOException {
        CaptionListResponse captionListResponse = mYouTube.captions().
                  list("snippet", videoId).execute();
        
        List<Caption> captions = captionListResponse.getItems();
        CaptionSnippet snippet;
        
        CaptionsModel captionsModel = null;
        for (Caption caption : captions) {
            snippet = caption.getSnippet();
            if (snippet.getLanguage().equals("en")) {
                captionsModel = new CaptionsModel(caption.getId(), snippet.getLanguage());
            }
        }
        
        return captionsModel;
    }
    
    private void downloadCaption(String captionId, String fileName) throws IOException {
        Download captionDownload = mYouTube
                  .captions()
                  .download(captionId)
                  .setTfmt(TTML);
        
        MediaHttpDownloader downloader = captionDownload.getMediaHttpDownloader();
        OutputStream outputFile = new FileOutputStream(PATH + fileName + ".ttml");
        captionDownload.executeAndDownloadTo(outputFile);
    }
}
