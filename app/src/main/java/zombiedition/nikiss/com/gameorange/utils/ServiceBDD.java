package zombiedition.nikiss.com.gameorange.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import zombiedition.nikiss.com.gameorange.dto.Mission;

/**
 * Created by issouf on 05/09/18.
 */

public class ServiceBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "orangegamesnikiss.db";


    private static final String TABLE_MISSIONS = "table_missions";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TYPE_PLAYER = "typePlayer";
    private static final int NUM_COL_TYPE_PLAYER = 1;
    private static final String COL_NAME_MISSION = "nameMission";
    private static final int NUM_COL_NAME_MISSION = 2;
    private static final String COL_LEVEL_MISSION = "level";
    private static final int NUM_COL_LEVEL_MISSION = 3;
    private static final String COL_DESC_MISSION = "description";
    private static final int NUM_COL_DESC_MISSION= 3;
    private static final String COL_MEILLEUR_SCORE = "meilleurScore";
    private static final int NUM_COL_MEILLEUR_SCORE = 4;
    private static final String COL_SCORE_POUR_DEBLOQ_SUIV = "scorePourDebloqSuiv";
    private static final int NUM_COL_SCORE_POUR_DEBLOQ_SUIV = 5;



    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public ServiceBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertMission(Mission mission){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_DESC_MISSION, mission.getDescription());
        values.put(COL_NAME_MISSION, mission.getNameMission());
        values.put(COL_TYPE_PLAYER, mission.getTypePlayer());
        values.put(COL_LEVEL_MISSION, mission.getLevel());
        values.put(COL_MEILLEUR_SCORE, mission.getMeilleurScore());
        values.put(COL_SCORE_POUR_DEBLOQ_SUIV, mission.getScorePourDebloqSuivant());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_MISSIONS, null, values);
    }

    public void addAllMissions(ArrayList<Mission> allMissions) {
        bdd = maBaseSQLite.getWritableDatabase();
        bdd.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Mission mission : allMissions) {
                values.put(COL_DESC_MISSION, mission.getDescription());
                values.put(COL_NAME_MISSION, mission.getNameMission());
                values.put(COL_TYPE_PLAYER, mission.getTypePlayer());
                values.put(COL_LEVEL_MISSION, mission.getLevel());
                values.put(COL_MEILLEUR_SCORE, mission.getMeilleurScore());
                values.put(COL_SCORE_POUR_DEBLOQ_SUIV, mission.getScorePourDebloqSuivant());
                bdd.insert(TABLE_MISSIONS, null, values);
            }
            bdd.setTransactionSuccessful();
        } finally {
            bdd.endTransaction();
            bdd.close();
        }
    }

    public List<Mission> getAllMissions() {

        List<Mission> missionList = new ArrayList<>();
        bdd = maBaseSQLite.getWritableDatabase();
        bdd.beginTransaction();
        String coloumn[] = {COL_ID, COL_TYPE_PLAYER, COL_NAME_MISSION, COL_LEVEL_MISSION, COL_DESC_MISSION, COL_MEILLEUR_SCORE, COL_SCORE_POUR_DEBLOQ_SUIV};
        Cursor cursor = bdd.query(TABLE_MISSIONS, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            Mission mission = new Mission();
            mission.setId(cursor.getInt(0));
            mission.setTypePlayer(cursor.getInt(1));
            mission.setNameMission(cursor.getString(2));
            mission.setLevel(cursor.getInt(3));
            mission.setDescription(cursor.getString(4));
            mission.setMeilleurScore(cursor.getInt(5));
            mission.setScorePourDebloqSuivant(cursor.getInt(6));
            missionList.add(mission);
        }

        bdd.setTransactionSuccessful();
        bdd.endTransaction();
        cursor.close();
        bdd.close();
        return missionList;
    }


    /**
     * cette methode est appele pour initialiser la base de donner , avoir des donnens en
     * base .
     */
    public void ajouterDefaultMissions() {
        ArrayList<Mission> missionList = new ArrayList<>();

        missionList.add(new Mission(1,"ALLIEN",1,"La mission est de d'arrive au bouvevard avec les passagers "));
        missionList.add(new Mission(2,"ALLIEN 2",2,"la mission consiste a transporte des personnes à l'aeroport"));
        missionList.add(new Mission(3,"MOTO",3,"la mission velo drome seulement l'aeroport"));
        missionList.add(new Mission(4,"VOITURE",4,"la mission consiste a transporte des personnes oui à l'aeroport"));

        this.addAllMissions(missionList);

    }


}
