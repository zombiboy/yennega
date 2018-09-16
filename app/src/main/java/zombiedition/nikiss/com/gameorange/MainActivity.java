package zombiedition.nikiss.com.gameorange;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import zombiedition.nikiss.com.gameorange.Activity.AcceuilActivity;
import zombiedition.nikiss.com.gameorange.utils.Constants;

import static zombiedition.nikiss.com.gameorange.utils.Constants.PARAM_SOUND_ON;


public class MainActivity extends Activity {


    public static MediaPlayer gameOnsound;
    public GamePanel gamePanel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;


        /**
         * partie musique
         */

        //initializing the media players for the game sounds
        gameOnsound = MediaPlayer.create(this,R.raw.audio);

        if(PARAM_SOUND_ON) {
            gameOnsound.start();
            System.out.println("RENTRERTERTETRE");
        }

        //setContentView(R.layout.activity_main);
        gamePanel =new GamePanel(this);
        setContentView(gamePanel);
    }




    @Override
    public void onBackPressed() {

        gamePanel.pause();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quittez la partie?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        //GameView.stopMusic();

                        if(PARAM_SOUND_ON) {
                            gameOnsound.stop();
                        }
                        startActivity(new Intent(MainActivity.this, AcceuilActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("close dialog");

                        dialog.cancel();
                        System.out.println("apres  le dialog cancel");
                        gamePanel.playGame();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.pause();
    }



}
