package zombiedition.nikiss.com.gameorange.dto;

/**
 * Created by issouf on 02/09/18.
 */

public class Mission {
    private int typePlayer;
    private String nameMission;
    private int level;
    private String description;

    public Mission() {

    }

    public Mission(int typePlayer, String nameMission, int level, String description) {
        this.typePlayer = typePlayer;
        this.nameMission = nameMission;
        this.level = level;
        this.description = description;
    }

    public String getNameMission() {
        return nameMission;
    }

    public void setNameMission(String nameMission) {
        this.nameMission = nameMission;
    }

    public int getTypePlayer() {
        return typePlayer;
    }

    public void setTypePlayer(int typePlayer) {
        this.typePlayer = typePlayer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
