package zombiedition.nikiss.com.gameorange.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import zombiedition.nikiss.com.gameorange.R;

import static zombiedition.nikiss.com.gameorange.utils.Constants.PARAM_SOUND_ON;

public class OptionsActivity extends AppCompatActivity {

    private RadioGroup radioGroupSound;
    private RadioButton radioButtonOn;
    private RadioButton radioButtonOFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        radioGroupSound = findViewById(R.id.radio_group_sound);
        radioButtonOn = findViewById(R.id.radio_sound_on);
        radioButtonOFF = findViewById(R.id.radio_sound_off);

        if(PARAM_SOUND_ON){
            radioButtonOn.setChecked(true);
            radioButtonOFF.setChecked(false);
        }
        else {
            radioButtonOn.setChecked(false);
            radioButtonOFF.setChecked(true);
        }

        radioGroupSound.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.radio_sound_on) {
                    PARAM_SOUND_ON=true;
                }
                else {
                    PARAM_SOUND_ON=false;
                }
            }
        });

    }


}
