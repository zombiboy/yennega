package zombiedition.nikiss.com.gameorange;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.HashMap;

import static zombiedition.nikiss.com.gameorange.Constants.TYPE_PLAYER_ALIEN;
import static zombiedition.nikiss.com.gameorange.Constants.TYPE_PLAYER_ALIEN_YELLOW;
import static zombiedition.nikiss.com.gameorange.MainActivity.gameOnsound;

/**
 *
 * Created by issouf on 24/08/18.
 * playerGap pour la taille disponible pour le jouer
 * obstacleGap est la hauteur entre les obstacles
 *
 */


public class GamePlayScene implements Scene {


    //==============

    //variable for counting two successive up-down events
    int clickCount = 0;
    //variable for storing the time of first click
    long startTime;
    //variable for calculating the total time
    long duration;
    //constant for defining the time duration between the click that can be considered as double-tap
    static final int MAX_DURATION = 500;
    //================


    long startTimeDoubleClik;
    private RectPlayer player;
    private Point playerPoint;

    private ObstacleManager obstacleManager;

    private boolean movingPlayer= false;
    private boolean gameOver = false;
    private long gameOverTime;
    private OrientationData orientationData;
    private long frameTime;

    private Rect r = new Rect();

    /**
     *  DEFISSONS ICI La taille du Joeur  et  ou Son TYPE
     *  player = new RectPlayer(new Rect(100,100,200,200));
     *  RectPlayer(new Rect(100,100,200,200),TYPE_PLAYER_ALIEN_YELLOW);
     */
    public GamePlayScene(){

        /**
         * definission de la taille du joueur
         */


        player = new RectPlayer(new Rect(100,100,200,200),TYPE_PLAYER_ALIEN_YELLOW);
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(400,650,75,Color.BLACK);

        orientationData=new OrientationData();
        orientationData.register();
        frameTime= System.currentTimeMillis();
    }


    public void reset() {

        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(400,650,75,Color.BLACK);
        movingPlayer = false;
        gameOnsound.start();


    }


    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE= 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //**** Double clik
                startTime = System.currentTimeMillis();
                clickCount++;
                //***

                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    reset();
                    gameOver= false;
                    orientationData.newGame();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)event.getX(),(int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;

                //**** Pour les double clik de pause
                long time = System.currentTimeMillis() - startTime;
                duration=  duration + time;
                if(clickCount == 2)
                {
                    if(duration<= MAX_DURATION)
                    {
                        System.out.println("DOUBLE TAP");

                    }
                    clickCount = 0;
                    duration = 0;
                    break;
                }
                //*********

                break;
        }
    }


    @Override
    public void draw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas,paint,"Fin de Partie");

        }
    }

    @Override
    public void update() {
        if (!gameOver){

            if (frameTime < Constants.INIT_TIME)
                frameTime= Constants.INIT_TIME;
            int elapsedTime = (int)(System.currentTimeMillis()-frameTime);
            frameTime =System.currentTimeMillis();
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() !=null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xSpeed = 2* roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;

                playerPoint.x += Math.abs(xSpeed*elapsedTime) > 5 ? xSpeed * elapsedTime:0;
                playerPoint.y -= Math.abs(ySpeed*elapsedTime) > 5 ? ySpeed * elapsedTime:0;

            }

            if (playerPoint.x < 0)
                playerPoint.x = 0;
            else if (playerPoint.x >Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            if (playerPoint.y < 0)
                playerPoint.y = 0;
            else if (playerPoint.y >Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;


            player.update(playerPoint);
            obstacleManager.update();

            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime= System.currentTimeMillis();
            }
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }





}
