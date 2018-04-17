package com.infinity.stone.custom;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Created by infamouSs on 4/17/18.
 */

public class MyFileChooserDialog {
    
    private FileChooser mFileChooser;
    
    public MyFileChooserDialog() {
        mFileChooser = new FileChooser();
        mFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
    
    public MyFileChooserDialog setTitle(String title) {
        mFileChooser.setTitle(title);
        
        return this;
    }
    
    public MyFileChooserDialog setExtensions(String[] extensions) {
        
        for (String ext : extensions) {
            mFileChooser.getExtensionFilters()
                      .add(new ExtensionFilter(buildDesExtension(ext), ext));
        }
        
        return this;
    }
    
    private String buildDesExtension(String extension) {
        return new StringBuilder("Video Only").append("(").append(extension).append(")").toString();
    }
    
    public File show() {
        return mFileChooser.showOpenDialog(null);
    }
}
