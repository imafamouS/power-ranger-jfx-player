package com.infinity.stone.custom;

import java.io.IOException;
import java.util.logging.Logger;

import com.infinity.stone.util.ResourceUtils;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXSlider.IndicatorPosition;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MySliderView extends VBox {
	/*private static final Logger LOG = Logger.getLogger("MySliderView");
    private ListenerSliderChange listener;
    @FXML JFXSlider slider;
    public MySliderView() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(
                      ResourceUtils.getInstance().loadLayout("custom_slider.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException exc) {
            LOG.info(exc.getMessage());
        }
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(listener!=null) {
            	if (isMin()) {
                    listener.onMin();
                }
                if (isMax()) {
                	listener.onMax();
                }
                if (isMute()) {
                	listener.onMute();
                }
                if (isAverage()) {
                	listener.onAverage();
                }
                listener.onValueChanged(newValue.doubleValue());
            }
        });
        slider.setIndicatorPosition(IndicatorPosition.LEFT);
        slider.setValueFactory(param -> Bindings.createStringBinding(
                  () -> ((int) param.getValue() * 100) + "%",
                  param.valueProperty()));
        //this.apply(this);
    }
    
    
    public void bindHoverVolumeToTransition(ImageView imageView) {

    }
    
    public void setOnListenSliderChange(ListenerSliderChange listener) {
    	this.listener = listener;
    }
    
    public JFXSlider getSlider() {
		return slider;
	}
    
    public interface ListenerSliderChange{
    	void onMute();
    	void onMin();
    	void onMax();
    	void onValueChanged(double value);
    	void onAverage();
    }
    
    public boolean isMin() {
        return 
    }
    
    public boolean isMute() {
        return ;
    }
    
    public boolean isMax() {
        return ;
    }
    
    public boolean isAverage() {
        return slider.getValue() >= 0.3 && slider.getValue() < 0.7;
    }
    
    public abstract JFXSlider apply(JFXSlider slider);
    
    public abstract void onMute();
    
    public abstract void onMin();
    
    public abstract void onMax();
    
    public abstract void onValueChanged(double value);
    
    public abstract void onAverage();*/
}
