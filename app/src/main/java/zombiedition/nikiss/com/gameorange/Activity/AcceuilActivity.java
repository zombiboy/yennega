package zombiedition.nikiss.com.gameorange.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import zombiedition.nikiss.com.gameorange.MainActivity;
import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.ServiceBDD;

public class AcceuilActivity extends Activity implements View.OnClickListener{

    //image button
    private ImageButton buttonPlay;
    // the high score button
    private ImageButton buttonScore;
    private ServiceBDD serviceBDD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        //permet de changer d'orientation de l'appareil
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        //getting the button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);


        //initializing the highscore button
        buttonScore = (ImageButton) findViewById(R.id.buttonScore);

        //setting the on click listener to high score button
        buttonScore.setOnClickListener(this);
        //setting the on click listener to play now button
        buttonPlay.setOnClickListener(this);

        serviceBDD = new ServiceBDD(this);




    }

    private void test() {
        serviceBDD.openRead();

        if (serviceBDD.getAllMissions().size() != 0) {
            System.out.println("GET GET  GET");
            //au cas ou la base est vide
            Mission m=serviceBDD.getMissionByLevel(3);
            System.out.println(m.toString());
        }


        serviceBDD.close();
    }

    @Override
    public void onClick(View v) {

        if (v == buttonPlay) {

            //the transition from MainActivity to GameActivity
            //startActivity(new Intent(AcceuilActivity.this, MainActivity.class));
            startActivity(new Intent(AcceuilActivity.this, Level.class));
        }
        if (v == buttonScore) {

            //the transition from MainActivity to HighScore activity
            startActivity(new Intent(AcceuilActivity.this, HighScore.class));
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


}
