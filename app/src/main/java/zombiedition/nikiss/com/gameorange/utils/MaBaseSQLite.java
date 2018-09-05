package zombiedition.nikiss.com.gameorange.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by issouf on 05/09/18.
 */


public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_MISSIONS = "table_missions";
    private static final String COL_ID = "ID";
    private static final String COL_TYPE_PLAYER = "typePlayer";
    private static final String COL_NAME_MISSION = "nameMission";
    private static final String COL_LEVEL_MISSION = "level";
    private static final String COL_DESC_MISSION = "description";
    private static final String COL_SCORE_POUR_DEBLOQ_SUIV = "scorePourDebloqSuiv";
    private static final String COL_MEILLEUR_SCORE = "meilleurScore";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_MISSIONS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TYPE_PLAYER + " VARCHAR(255), "
            + COL_LEVEL_MISSION + " INTEGER, "
            + COL_NAME_MISSION + " VARCHAR(255), "
            + COL_SCORE_POUR_DEBLOQ_SUIV + " INTEGER, "
            + COL_MEILLEUR_SCORE + " INTEGER, "
            + COL_DESC_MISSION + " TEXT NOT NULL);";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MISSIONS;

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_MISSIONS + ";");
        onCreate(db);
    }

}
