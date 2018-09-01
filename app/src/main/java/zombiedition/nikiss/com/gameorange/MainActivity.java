package zombiedition.nikiss.com.gameorange;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {


    static MediaPlayer gameOnsound;
    GamePanel gamePanel;


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

        //playMusik();

        //initializing the media players for the game sounds
        gameOnsound = MediaPlayer.create(this,R.raw.audio);
        gameOnsound.start();
        //setContentView(R.layout.activity_main);
        gamePanel =new GamePanel(this);
        setContentView(gamePanel);
    }

    private void playMusik() {

        MusicService mServ = new MusicService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);
    }


    /**
     * Partie pour gerer la musique
     * mServ.pauseMusic();
     mServ.resumeMusic();
     mServ.stopMusic();
     */
    private boolean mIsBound = false;
    public MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();

        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class),
                Scon,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
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
                        gameOnsound.stop();
                        startActivity(new Intent(MainActivity.this, AcceuilActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.out.println("close dialog");

                        dialog.cancel();
                        System.out.println("apres  le dialog cancel");
                        //gamePanel.playGame();

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
