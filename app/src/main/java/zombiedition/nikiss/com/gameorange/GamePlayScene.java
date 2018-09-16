package zombiedition.nikiss.com.gameorange;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.Constants;
import zombiedition.nikiss.com.gameorange.utils.ServiceBDD;

import static zombiedition.nikiss.com.gameorange.utils.Constants.MEILLEUR_SCORE;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PARAM_SOUND_ON;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SELECT_LEVEL_GAME;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN_YELLOW;
import static zombiedition.nikiss.com.gameorange.MainActivity.gameOnsound;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_MOTO;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_VOITURE;


/**
 *
 * Created by issouf on 24/08/18.
 * playerGap pour la taille disponible pour le jouer
 * obstacleGap est la hauteur entre les obstacles
 *
 */


public class GamePlayScene extends Activity implements Scene {


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
    private Context context;
    private ServiceBDD serviceBDD;

    /**
     *  DEFISSONS ICI La taille du Joeur  et  ou Son TYPE
     *  player = new RectPlayer(new Rect(100,100,200,200));
     *  RectPlayer(new Rect(100,100,200,200),TYPE_PLAYER_ALIEN_YELLOW);
     */

    public GamePlayScene(Context context){

        this.context = context;

        /**
         * definission de la taille du joueur
         * TYPE_PLAYER_ALIEN_YELLOW
         * SELECT_LEVEL_GAME =TYPE_PLAYER_ALIEN  = 0 = position
         * SELECT_LEVEL_GAME =TYPE_PLAYER_ALIEN_YELLOW;
         */


        serviceBDD = new ServiceBDD(context);
        player = new RectPlayer(new Rect(100,100,200,200),SELECT_LEVEL_GAME);
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(400,650,75,Color.BLACK,context);

        orientationData=new OrientationData();
        orientationData.register();
        frameTime= System.currentTimeMillis();


    }




    public void reset() {

        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(400,650,75,Color.BLACK,context);
        movingPlayer = false;

        if(PARAM_SOUND_ON) {
            gameOnsound.start();
        }

        System.out.println("RESET DAN GAMEPLAY");

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
                memoriserScoreJeux();
                System.out.println("GAMEOVERTIME OUF:"+gameOverTime);
                //ici qu'on vas essayer de sauvegarder le score
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


    private void memoriserScoreJeux() {


        serviceBDD.open();

        if (serviceBDD.getAllMissions().size() != 0) {

            System.out.println("LEVEL ISSSSSSSSSS "+SELECT_LEVEL_GAME);
            Mission missionEnCours=serviceBDD.getMissionByLevel(SELECT_LEVEL_GAME);
            if (missionEnCours!=null)
            {
                //Mise a jour de la mission
                int lastMeilleurScore = missionEnCours.getMeilleurScore();

                System.out.println("MEUILLEUR SOCORE EST "+MEILLEUR_SCORE+" last score is"+lastMeilleurScore);

                if(MEILLEUR_SCORE >lastMeilleurScore) {

                    missionEnCours.setMeilleurScore(MEILLEUR_SCORE);

                    // a debloquer une seul fois dans le jeux
                    if( MEILLEUR_SCORE > missionEnCours.getScorePourDebloqSuivant() && missionEnCours.getScorePourDebloqSuivant()>lastMeilleurScore ){

                        //TODO:: SI ca vaut le score pour debloquer la mission suivant donc on debloq et notifie au joueur
                        debloquerMissionSuivant();
                        System.out.println("LE LEVEL SUIVANT DOIT ETRE DEBLOK");

                    }

                    int i = serviceBDD.updateMission(missionEnCours);

                }
                else {
                    Log.d(this.getClass().getSimpleName(),"MISE A JOUR REFUSE");
                }
            }


        }


        serviceBDD.close();

    }

    private void debloquerMissionSuivant() {

        // si le score est superieur au score pour debloquer alors on debloq la mission
        switch (SELECT_LEVEL_GAME) {
            case 1:
                //creer le level 2
                Mission mission =new Mission(2,"ALLIEN 2",2,"la mission consiste a transporte des personnes à l'aeroport",3);
                serviceBDD.insertMission(mission);

                break;
            case 2:
                //creer le level 3
                serviceBDD.insertMission((new Mission(3,"MOTO",3,"la mission velo drome seulement l'aeroport",5)));
                break;
            case 3:
                serviceBDD.insertMission(new Mission(4,"VOITURE",4,"la mission consiste a  des personnes à l'aeroport",30));
                break;
            default:  ;
        }
    }


}
