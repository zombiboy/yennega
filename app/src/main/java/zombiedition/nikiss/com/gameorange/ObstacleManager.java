package zombiedition.nikiss.com.gameorange;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import zombiedition.nikiss.com.gameorange.utils.Constants;

import static zombiedition.nikiss.com.gameorange.MainActivity.gameOnsound;
import static zombiedition.nikiss.com.gameorange.utils.Constants.MEILLEUR_SCORE;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SELECT_LEVEL_GAME;

/**
 * Created by issouf on 23/08/18.
 */

public class ObstacleManager {
    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private int score=0;

    private long startTime;
    private long initTime;

    private SharedPreferences sharedPreferences;



    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        // si pause est faux alors on populate le jeux
        populateObstacles();
    }

    /**
     * ici si y'a collision entre un obstacles et le joueur
     * alors on retourn true pour stoper le jeux
     * @param player
     * @return
     */
    public boolean playerCollide(RectPlayer player) {
        for (Obstacle ob: obstacles ) {
            if (ob.playerCollide(player)){

                MemoriserScoreJeux();
                gameOnsound.pause();
            return true;
            }
        }
        return false;
    }

    /**
     * Cette methode nous permettra de memoriser les meilleurs scores de chaque etapes
     * a la fi n du jeux, la VARAIBLE MEILLEUR_SCORE nous permettra de sauvegarder les scores
     */
    private void MemoriserScoreJeux() {

        //initializing shared Preferences
        //sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);

        System.out.println("AN avant "+MEILLEUR_SCORE+"DU LEVEL"+SELECT_LEVEL_GAME);

        MEILLEUR_SCORE+=score;
        System.out.println("NN avant "+MEILLEUR_SCORE);
    }

    private void populateObstacles() {
        int currY = -5* Constants.SCREEN_HEIGHT/4;
        while(currY < 0) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    /**
     *  la vitesse du jeux est geré par speed ici
     *  si speed est grand alors ca acceler alors on augmente la valeur de speed
     * peu a peu seulement avec le temps en faisant plus on dure plus ca accelere
     * en faisant
     */
    public void update() {

        if(startTime< Constants.INIT_TIME)
            startTime= Constants.INIT_TIME;


        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1+(startTime-initTime)/2000.0)) * Constants.SCREEN_HEIGHT/(10000.0f);
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;


        }
    }

    /**
     * la fonction qui dessine a l'ecran  on l'utilise pour dessine le score
     * en fonction de l'avancement du jeux
     * @param canvas
     */
    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles)
            ob.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText(""+score,50,50+paint.descent()-paint.ascent(),paint);
    }
}
