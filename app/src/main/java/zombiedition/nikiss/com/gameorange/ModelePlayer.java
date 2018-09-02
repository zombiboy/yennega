package zombiedition.nikiss.com.gameorange;

/**
 * Created by issouf on 02/09/18.
 */

public class ModelePlayer {
    private int idleImg;
    private int walk1;
    private int walk2;

    public ModelePlayer() {
    }

    public ModelePlayer(int idleImg, int walk1, int walk2) {
        this.idleImg = idleImg;
        this.walk1 = walk1;
        this.walk2 = walk2;
    }

    public int getIdleImg() {
        return idleImg;
    }

    public int getWalk1() {
        return walk1;
    }

    public int getWalk2() {
        return walk2;
    }
}
