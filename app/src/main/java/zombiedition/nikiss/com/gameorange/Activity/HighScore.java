package zombiedition.nikiss.com.gameorange.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.adapter.ScoreMissionAdapter;
import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.ServiceBDD;

import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_HIGHSCORE_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SHAR_PREF_NAME;

public class HighScore extends AppCompatActivity {

    private TextView textView;


    private RecyclerView rv;
    private List<Mission> missionList = new ArrayList<>();
    private ScoreMissionAdapter adapter;
    private ServiceBDD serviceBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        rv = (RecyclerView) findViewById(R.id.recycleview_score);
        serviceBDD = new ServiceBDD(this);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ScoreMissionAdapter(missionList);
        rv.setAdapter(adapter);

        chargerScoreMission();

    }

    /**
     * La methode devrait permettre de charger les missions qui sont soit dans le sharedPrefern,Local
     */
    private void chargerScoreMission() {

        //On ouvre la base de données pour écrire dedans
        serviceBDD.open();

        if (serviceBDD.getAllMissions().size() == 0) {
            //au cas ou la base est vide
            serviceBDD.ajouterDefaultMissions();
            missionList.addAll(serviceBDD.getAllMissions());
        }
        else  {

            missionList.addAll(serviceBDD.getAllMissions());

        }

        serviceBDD.close();


    }
}
