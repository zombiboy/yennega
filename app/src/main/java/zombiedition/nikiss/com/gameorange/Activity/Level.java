package zombiedition.nikiss.com.gameorange.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import zombiedition.nikiss.com.gameorange.MainActivity;
import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.adapter.MissionAdapter;
import zombiedition.nikiss.com.gameorange.dto.Mission;
import zombiedition.nikiss.com.gameorange.utils.RecyclerTouchListener;
import zombiedition.nikiss.com.gameorange.utils.ServiceBDD;

import static zombiedition.nikiss.com.gameorange.utils.Constants.SELECT_LEVEL_GAME;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN_YELLOW;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_MOTO;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_VOITURE;

public class Level extends AppCompatActivity {

    private RecyclerView rv;
    private List<Mission> missionList = new ArrayList<>();
    private MissionAdapter adapter;
    private ServiceBDD serviceBDD;
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        rv = (RecyclerView)findViewById(R.id.recycleview_select_mission);
        serviceBDD = new ServiceBDD(this);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MissionAdapter(missionList);
        rv.setAdapter(adapter);

        /**
         * vas charger les donnes depuis la base de donnnees du telephone
         */
        chargerLocalMission();

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
                    case 2:
                        SELECT_LEVEL_GAME=TYPE_PLAYER_MOTO;
                        break;
                    case 3:
                        SELECT_LEVEL_GAME=TYPE_PLAYER_VOITURE;
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



    /**
     * La methode devrai faire une requete pour avoir la liste des mission debloquer et les
     * charges dans la liste . la source peut etre SharedPredferences , LocalStorage
     *
     * la methode suivant permet de charger des missions depuis la base de donnees
     * au cas ou ils n'ya pas de donnees dans la base ils insere deux missions par defaut dans la base
     * et charge les deux missions. dans le cas contraire ils charge tous les jeux dans la base de donnes
     */
    private void chargerLocalMission() {

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

    //TODO:prevoir une methode pour telecharger des news Missions

    public void telechargerEtSaveMissions() {


        //Création d'un mission ou recuperation a partir d'un api
        Mission mission = new  Mission(1,"DEBLOK",1,"La mission est de d'arrive au bouvevard avec les passagers ");

        //On ouvre la base de données pour écrire dedans
        serviceBDD.open();
        //On insère la mission que l'on vient de créer
        serviceBDD.insertMission(mission);

        //Mettre a jour la Liste
        missionList.clear();
        missionList.addAll(serviceBDD.getAllMissions());
        serviceBDD.close();

    }




}
