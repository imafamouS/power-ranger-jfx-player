package com.infinity.stone.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;

/**
 * Created by infamouSs on 4/12/18.
 */

class TFListCell extends ListCell<String> {
    
    private TextField textField;
    
    @Override
    public void startEdit() {
        if (!isEditable() || !getListView().isEditable()) {
            return;
        }
        super.startEdit();
        
        if (isEditing()) {
            if (textField == null) {
                textField = new TextField(getItem());
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        commitEdit(textField.getText());
                    }
                });
            }
        }
        
        textField.setText(getItem());
        setText(null);
        
        setGraphic(textField);
        textField.selectAll();
    }
    
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (!isEditing()) {
                if (textField != null) {
                    setText(textField.getText());
                } else {
                    setText(item);
                }
                setGraphic(null);
            }
        }
    }
}
