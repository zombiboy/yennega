package zombiedition.nikiss.com.gameorange.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;

import zombiedition.nikiss.com.gameorange.R;

public class SplashLoadActivity extends AppCompatActivity {


    /**
     * Temps d'attente avant l'affichage de l'écran principale.
     */
    private static int DELAY = 3000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            startIndexActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msplash_screen);
        View mContentView = ButterKnife.findById(this, R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //this.accountExists();
        handler.postDelayed(runnable, SplashLoadActivity.DELAY);
    }


    private void startIndexActivity() {
        Intent intent = new Intent(this, AcceuilActivity.class);
        startActivity(intent);
        finish();
    }

}
