package zombiedition.nikiss.com.gameorange;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by issouf on 02/09/18.
 */

public class Bonus  implements GameObject{

    private Rect rectangle;
    private int color;

    public Bonus(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);

    }

    @Override
    public void update() {

    }

    public Rect getRectangle() {
        return rectangle;
    }

}
