package com.infinity.stone.util.image;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

/**
 * Created by infamouSs on 4/17/18.
 */

public class UiUtilities {
    
    
    public static Dimension2D shouldFitIn(double originalWidth, double originalHeight,
              double toFitWidth, double toFitHeight) {
        double fitRatio = toFitWidth / toFitHeight;
        double originalRatio = originalWidth / originalHeight;
        
        if (fitRatio > originalRatio) {
            //Die Weite muss zur Weite der Komponente passen. Oben & Unten abschneiden
            double widthFactor = toFitWidth / originalWidth;
            return new Dimension2D(toFitWidth, originalHeight * widthFactor);
        } else {
            //Die Höhe muss zur Höhe der Komponente passen. Links & Rechts abschneiden
            double heightFactor = toFitHeight / originalHeight;
            return new Dimension2D(originalWidth * heightFactor, toFitHeight);
        }
    }
    
    public static Dimension2D shouldFitIn(Dimension2D originalDim, double toFitWidth,
              double toFitHeigth) {
        return shouldFitIn(originalDim.getWidth(), originalDim.getHeight(), toFitWidth,
                  toFitHeigth);
    }
    
    public static Dimension2D shouldFitIn(double originalWidth, double originalHeigth,
              Dimension2D toFitDim) {
        return shouldFitIn(originalWidth, originalHeigth, toFitDim.getWidth(),
                  toFitDim.getHeight());
    }
    
    public static Dimension2D shouldFitIn(Dimension2D originalDim, Dimension2D toFitDim) {
        return shouldFitIn(originalDim.getWidth(), originalDim.getHeight(), toFitDim.getWidth(),
                  toFitDim.getHeight());
    }
    
    public void relocate(Node node, double x, double y, double z) {
        node.setLayoutX(x - node.getLayoutBounds().getMinX());
        node.setLayoutY(y - node.getLayoutBounds().getMinY());
    }
}
