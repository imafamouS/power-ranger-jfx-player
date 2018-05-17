package com.infinity.stone.tracking;

import java.util.logging.Level;

/**
 * Created by infamouSs on 4/24/18.
 */

public enum Action {
    START_APP(Level.INFO, "START APP"),
    SELECT_VIDEO(Level.INFO, "SELECT VIDEO"),
    DELETE_FAVORITE(Level.INFO, "DELETE FAVORITE"),
    CHOOSE_TAB_SUB(Level.INFO, "CHOOSE TAB SUB"),
    CHOOSE_TAG_FAVORITE(Level.INFO, "CHOOSE TAB FAVORITE"),
    CLICK_TO_SUB(Level.INFO, "CLICK TO SUB"),
    ERROR(Level.INFO, "ERROR"),
    REMOVE_VIDEO(Level.INFO, "REMOVE VIDEO"),
    ADD_FAVORITE(Level.INFO, "ADD FAVORITE");
    
    final Level mLevel;
    final String name;
    
    Action(Level level, String name) {
        this.mLevel = level;
        this.name = name;
    }
}
