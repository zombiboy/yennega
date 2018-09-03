package zombiedition.nikiss.com.gameorange;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import zombiedition.nikiss.com.gameorange.utils.Constants;

import static zombiedition.nikiss.com.gameorange.MainActivity.gameOnsound;
//import android.support.annotation.MainThread;

/**
 * Created by Issouf on 20/08/2018.
 */

class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static MainThread thread;
    private SceneManager manager;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT= context;

        thread = new MainThread(getHolder(),this);


        manager = new SceneManager();

        setFocusable(true);
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new MainThread(getHolder(),this);
        Constants.INIT_TIME= System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)  {

        manager.recieveTouch(event);
        return true;
        // return super.onTouchEvent(event);
    }


    /**
     * tant qu'on ne obstacleManager.playerCollide(player) ne retourne pas true
     * on continuer seulement car il n'y pas collision entre le joueur et un bordure
     */
    public void update() {

        manager.update();

    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        manager.draw(canvas);

    }



    public void pause()  {

        try {
            thread.setRunning(false);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameOnsound.pause();

    }



    public void playGame() {
        gameOnsound.start();
        System.out.println("dans la Methode Play");

        //relanceThread();

        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();


    }




}
