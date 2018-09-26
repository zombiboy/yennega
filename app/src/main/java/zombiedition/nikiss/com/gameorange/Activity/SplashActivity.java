package zombiedition.nikiss.com.gameorange.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.utils.Constants;


public class SplashActivity extends Activity {

    public ImageView logo;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        View mContentView = ButterKnife.findById(this, R.id.logo_id);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        logo = (ImageView) findViewById(R.id.logo_id);
        logo.setMinimumHeight(Constants.SCREEN_WIDTH);
        logo.setMinimumWidth(Constants.SCREEN_HEIGHT);

        handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Lancer et attendint 1 second");
                logo.setImageResource(R.drawable.logo_nikiss_edition);

            }
        },1000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Lancer et attendint 1 second");
                logo.setImageResource(R.drawable.logo_orange);

            }
        },3000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,SplashLoadActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}
