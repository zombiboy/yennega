package zombiedition.nikiss.com.gameorange;

import java.util.HashMap;

import zombiedition.nikiss.com.gameorange.dto.ModelePlayer;

import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_ALIEN_YELLOW;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_MOTO;
import static zombiedition.nikiss.com.gameorange.utils.Constants.TYPE_PLAYER_VOITURE;

/**
 *
 * Created by issouf on 02/09/18.
 * Dans cette classe on ajoutera tous les types de personnage qu'on aura besoin dans notre application
 */

public class ModelPlayerManager {

    private HashMap<Integer, ModelePlayer> playerHashMap;

    public ModelPlayerManager() {

        playerHashMap= new HashMap<>();

        ModelePlayer allien = new ModelePlayer(R.drawable.alienblue,R.drawable.alienblue_walk1,R.drawable.alienblue_walk2);
        ModelePlayer allienYellow = new ModelePlayer(R.drawable.alienyellow,R.drawable.alienyellow_walk1,R.drawable.alienyellow_walk2);
        ModelePlayer motoPlayer = new ModelePlayer(R.drawable.moto_walk,R.drawable.moto_walk1,R.drawable.moto_walk2);
        ModelePlayer voiturePlayer = new ModelePlayer(R.drawable.taxi_walk,R.drawable.taxi_walk1,R.drawable.taxi_walk2);


        playerHashMap.put(TYPE_PLAYER_ALIEN,allien);
        playerHashMap.put(TYPE_PLAYER_ALIEN_YELLOW,allienYellow);
        playerHashMap.put(TYPE_PLAYER_MOTO,motoPlayer);
        playerHashMap.put(TYPE_PLAYER_VOITURE,voiturePlayer);

    }

    public HashMap<Integer, ModelePlayer> getPlayerHashMap() {
        return playerHashMap;
    }
}
