package zombiedition.nikiss.com.gameorange;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.Constants;
import zombiedition.nikiss.com.gameorange.utils.ServiceBDD;

import static zombiedition.nikiss.com.gameorange.MainActivity.gameOnsound;
import static zombiedition.nikiss.com.gameorange.utils.Constants.MEILLEUR_SCORE;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PARAM_SOUND_ON;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_HIGHSCORE_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SELECT_LEVEL_GAME;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SHAR_PREF_NAME;

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
    private Boom boom;
    private int score=0;

    private long startTime;
    private long initTime;

    private Context context;
    static MediaPlayer gameOversound;

    private SharedPreferences sharedPreferences;
    private ServiceBDD serviceBDD;


    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color,Context context) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        this.context = context;

        sharedPreferences = context.getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);
        serviceBDD = new ServiceBDD(context);

        startTime = initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();
        boom = new  Boom(context);
        //Permet de mettre l'image du boom hors de l'ecran lors de l'initialisation du BOOM
        boom.setX(-350);
        boom.setY(-350);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);

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
                dessinerBoom(player);
                memoriserInfoScore();

                if(PARAM_SOUND_ON) {
                    gameOnsound.pause();
                    gameOversound.start();
                }

            return true;
            }
        }
        return false;
    }

    private void dessinerBoom(RectPlayer player) {
        boom.setX(player.getRectangle().centerX()-100);
        boom.setY(player.getRectangle().centerY()-100);
    }

    /**
     * Cette methode nous permettra de memoriser les meilleurs scores de chaque etapes
     * a la fi n du jeux, la VARAIBLE MEILLEUR_SCORE nous permettra de sauvegarder les scores
     */
    private void memoriserInfoScore() {

        //NOUS PERMET DE MEMORISER DES INFOS EN LOCAL
        MEILLEUR_SCORE=score;

    }

    private void Memoriser() {



        //initializing shared Preferences

        System.out.println("AN avant "+MEILLEUR_SCORE+"DU LEVEL"+SELECT_LEVEL_GAME);


        MEILLEUR_SCORE+=score;
        System.out.println("NN avant "+MEILLEUR_SCORE);

        // PREFS_LEVEL+SELECT_LEVEL_GAME donne PREFS_LEVEL0 ,PREFS_LEVEL1
        //pour cela, on commence par regarder si on a déjà des éléments sauvegardés
        if (sharedPreferences.contains(PREFS_LEVEL+SELECT_LEVEL_GAME) && sharedPreferences.contains(PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME)) {

            int levelEnCours = sharedPreferences.getInt(PREFS_LEVEL+SELECT_LEVEL_GAME, SELECT_LEVEL_GAME);
            int lastscoreLevelEnCours = sharedPreferences.getInt(PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME, 0);
            //String name = sharedPreferences.getString(PREFS_NAME, null);
            // Si le nouveau score est superieur a l'ancien alors on sauvegarde le nouveau Level

            if(score > lastscoreLevelEnCours ){
                System.out.println("MISE A JOUR DU SCORE");
                System.out.println("NOUVEL SCORE  == "+PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME+" "+score);
                sharedPreferences
                        .edit()
                        .putInt(PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME, score)
                        .apply();

                //TODO:: On pourra comparer le Score et debloquer le nouveau Niveau si possible

            }
            System.out.println("ANCIEN SCORE  == "+PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME+" "+lastscoreLevelEnCours);


        } else {
            //si aucun utilisateur n'est sauvegardé, on ajouter [24,florent]
            sharedPreferences
                    .edit()
                    .putInt(PREFS_LEVEL+SELECT_LEVEL_GAME, SELECT_LEVEL_GAME)
                    .putInt(PREFS_HIGHSCORE_LEVEL+SELECT_LEVEL_GAME, score)
                    .apply();
            System.out.println("SCORE SAUVEGARDER AVEC SUCCES");
        }

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
        //draw le boom

        canvas.drawBitmap(
                boom.getBitmap(),
                boom.getX(),
                boom.getY(),
                paint
        );
        //End drawing boom image
    }
}
