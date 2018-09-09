package zombiedition.nikiss.com.gameorange.dto;

/**
 * Created by issouf on 02/09/18.
 */

public class Mission {
    private int id=0;
    private int typePlayer;
    private String nameMission;
    private int level=0;
    private String description="";
    private int meilleurScore=0;
    private int scorePourDebloqSuivant=0;

    public Mission() {

    }

    public Mission(int typePlayer, String nameMission, int level, String description) {
        this.typePlayer = typePlayer;
        this.nameMission = nameMission;
        this.level = level;
        this.description = description;
    }

    public Mission(int typePlayer, String nameMission, int level, String description, int scorePourDebloqSuivant) {
        this.typePlayer = typePlayer;
        this.nameMission = nameMission;
        this.level = level;
        this.description = description;
        this.scorePourDebloqSuivant = scorePourDebloqSuivant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public void setMeilleurScore(int meilleurScore) {
        this.meilleurScore = meilleurScore;
    }

    public int getScorePourDebloqSuivant() {
        return scorePourDebloqSuivant;
    }

    public void setScorePourDebloqSuivant(int scorePourDebloqSuivant) {
        this.scorePourDebloqSuivant = scorePourDebloqSuivant;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", typePlayer=" + typePlayer +
                ", nameMission='" + nameMission + '\'' +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", meilleurScore=" + meilleurScore +
                ", scorePourDebloqSuivant=" + scorePourDebloqSuivant +
                '}';
    }
}
