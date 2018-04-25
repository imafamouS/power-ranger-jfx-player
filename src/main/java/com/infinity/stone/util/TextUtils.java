package com.infinity.stone.util;

import javafx.util.Duration;

/**
 * Created by infamouSs on 4/24/18.
 */

public class TextUtils {
    
	public static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                             - elapsedMinutes * 60;
        
        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                                  durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                          elapsedHours, elapsedMinutes, elapsedSeconds,
                          durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                          elapsedMinutes, elapsedSeconds, durationMinutes,
                          durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                          elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                          elapsedSeconds);
            }
        }
    }
    
    public static Duration reverseFormatTime(String time) {
    	String[] timeArr = time.split(":");
    	int hour = Integer.valueOf(timeArr[0]);
    	int minute = Integer.valueOf(timeArr[1]);
    	int second = (int)Math.floor(Double.valueOf(timeArr[2]));
		return Duration.seconds(hour*60*60 + minute*60 + second);
    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
}
