package zombiedition.nikiss.com.gameorange.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import zombiedition.nikiss.com.gameorange.MainActivity;
import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.adapter.MissionAdapter;
import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.RecyclerTouchListener;

import static zombiedition.nikiss.com.gameorange.utils.Constants.SELECT_LEVEL_GAME;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN_YELLOW;

public class Level extends AppCompatActivity {

    private RecyclerView rv;
    private List<Mission> missionList = new ArrayList<>();
    private MissionAdapter adapter;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        rv = (RecyclerView)findViewById(R.id.recycleview_select_mission);

        chargerDefaultMission();

        rv.addOnItemTouchListener(new RecyclerTouchListener(this,
                rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {


                intent = new Intent(getApplicationContext(), MainActivity.class);
                switch (position) {
                    case 0:
                        SELECT_LEVEL_GAME=TYPE_PLAYER_ALIEN;
                        break;
                    case 1:
                        SELECT_LEVEL_GAME=TYPE_PLAYER_ALIEN_YELLOW;
                        break;
                    default:  SELECT_LEVEL_GAME=TYPE_PLAYER_ALIEN;
                }

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



    }

    private void chargerDefaultMission() {

        missionList.add(new Mission(1,"ALLIEN",1,"La mission est de d'arrive au bouvevard avec les passagers "));
        missionList.add(new Mission(2,"TAXIMAN",2,"la mission consiste a transporte des personnes à l'aeroport"));
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MissionAdapter(missionList);
        rv.setAdapter(adapter);
    }


}
