package zombiedition.nikiss.com.gameorange;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by issouf on 24/08/18.
 */

public class SceneManager {


    private Context context;

    private ArrayList <Scene> scenes= new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager() {
        ACTIVE_SCENE =0;
        scenes.add(new GamePlayScene());

    }

    public SceneManager(Context context) {
        ACTIVE_SCENE =0;
        this.context=context;
        scenes.add(new GamePlayScene(context));

    }

    public void recieveTouch(MotionEvent event) {
        scenes.get(ACTIVE_SCENE).recieveTouch(event);

    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();

    }

    public void draw(Canvas canvas) {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
