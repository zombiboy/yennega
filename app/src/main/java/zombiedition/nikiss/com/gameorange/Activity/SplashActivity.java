package zombiedition.nikiss.com.gameorange.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import zombiedition.nikiss.com.gameorange.R;


public class SplashActivity extends Activity {

    public ImageView logo;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View mContentView = ButterKnife.findById(this, R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        logo = (ImageView) findViewById(R.id.logo_id);

        handler=new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Lancer et attendint 1 second");
                logo.setImageResource(R.drawable.splash_img2);

            }
        },1000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Lancer et attendint 1 second");
                logo.setImageResource(R.drawable.splash_img1);

            }
        },3000);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,AcceuilActivity.class);
                startActivity(intent);
                finish();
            }
        },6000);

    }
}
