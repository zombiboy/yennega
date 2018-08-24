package zombiedition.nikiss.com.gameorange;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by issouf on 24/08/18.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
