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

import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_HIGHSCORE_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.PREFS_LEVEL;
import static zombiedition.nikiss.com.gameorange.utils.Constants.SHAR_PREF_NAME;

public class HighScore extends AppCompatActivity {

    private TextView textView,textView2,textView3;

    private SharedPreferences sharedPreferences;

    private RecyclerView rv;
    private List<Mission> missionList = new ArrayList<>();
    private ScoreMissionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        rv = (RecyclerView) findViewById(R.id.recycleview_score);

        chargerDefaultScoreMission();

        sharedPreferences  = getSharedPreferences(SHAR_PREF_NAME, Context.MODE_PRIVATE);

        //TODO: Mettre un increment pour defiler les scores ,Afficher avec un Recycleview

        int score;

        score = sharedPreferences.getInt(PREFS_HIGHSCORE_LEVEL+"0",0);
        textView.setText("Niveau "+"1 : "+"Score "+score);
        textView2.setText("2."+"13");
        textView3.setText("3."+"AZ");
        //textView4.setText("4."+sharedPreferences.getInt("score4",0));

    }

    private void chargerDefaultScoreMission() {
        //TODO:: Mettre a jour l'objet Mission avant de la mettre dans la Liste

        Mission m =new Mission(1,"ALLIEN",1,"La mission est de d'arrive au bouvevard avec les passagers ",50);
        m.setMeilleurScore(100);
        missionList.add(m);
        missionList.add(new Mission(2,"TAXIMAN",2,"la mission consiste a transporte des personnes à l'aeroport",100));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new ScoreMissionAdapter(missionList);
        rv.setAdapter(adapter);

        chargerDebloqMission();
    }

    /**
     * La methode devrait permettre de charger les missions qui sont soit dans le sharedPrefern,Local
     */
    private void chargerDebloqMission() {

    }
}
