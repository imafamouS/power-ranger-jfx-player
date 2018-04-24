package com.infinity.stone.custom;

import com.jfoenix.controls.JFXSlider;
import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;

public abstract class MySliderView extends JFXSlider {
    
    public MySliderView(double min, double max, double value) {
        super(min, max, value);
        this.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isMin()) {
                onMin();
            }
            if (isMax()) {
                onMax();
            }
            if (isMute()) {
                onMute();
            }
            if (isAverage()) {
                onAverage();
            }
            onValueChanged(newValue.doubleValue());
        });
        
        this.setValueFactory(param -> Bindings.createStringBinding(
                  () -> ((int) param.getValue() * 100) + "%",
                  param.valueProperty()));
        this.apply(this);
    }
    
    public void bindHoverVolumeToTransition(ImageView imageView) {
    
    }
    
    
    public boolean isMin() {
        return this.getValue() > 0.0 && this.getValue() < 30.0;
    }
    
    public boolean isMute() {
        return this.getValue() == 0.0;
    }
    
    public boolean isMax() {
        return this.getValue() >= 0.7 && this.getValue() <= 1;
    }
    
    public boolean isAverage() {
        return this.getValue() >= 0.3 && this.getValue() < 0.7;
    }
    
    public abstract JFXSlider apply(JFXSlider slider);
    
    public abstract void onMute();
    
    public abstract void onMin();
    
    public abstract void onMax();
    
    public abstract void onValueChanged(double value);
    
    public abstract void onAverage();
}
