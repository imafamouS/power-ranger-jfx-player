package com.infinity.stone.tracking;

import java.util.List;

/**
 * Created by infamouSs on 4/24/18.
 */

public interface TrackingService {
    
    void track(Action action, Object data);
    
    void track(Action action, List data);
    
    void track(Action action, String... data);
    
    void track(Action action, Exception ex);
}
