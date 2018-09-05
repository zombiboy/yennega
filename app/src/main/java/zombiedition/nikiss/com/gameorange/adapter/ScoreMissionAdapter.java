package zombiedition.nikiss.com.gameorange.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.dto.Mission;

/**
 * Created by issouf on 02/09/18.
 */

public class ScoreMissionAdapter extends RecyclerView.Adapter<ScoreMissionAdapter.MyViewHolder> {

    private List<Mission> missionList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameMission;
        public TextView level;
        public TextView description;
        public TextView meilleurScore;

        public MyViewHolder(View view) {
            super(view);

            nameMission = (TextView) view.findViewById(R.id.txtNameMission);
            level = (TextView) view.findViewById(R.id.txtLevel);
            description = (TextView) view.findViewById(R.id.txtDescription);
            meilleurScore = (TextView) view.findViewById(R.id.txtMeilleurScore);
        }
    }

    public ScoreMissionAdapter(List<Mission> missionList) {
        this.missionList = missionList;
    }


    @Override
    public ScoreMissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liste_score_mission, parent, false);

        return new ScoreMissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScoreMissionAdapter.MyViewHolder holder, int position) {
        Mission mission = missionList.get(position);

        holder.nameMission.setText(mission.getNameMission());
        holder.level.setText("Mission "+mission.getLevel());
        holder.description.setText(mission.getDescription());
        holder.meilleurScore.setText("Meilleur Score : "+mission.getMeilleurScore());
        holder.itemView.setTag(mission);

    }

    @Override
    public int getItemCount() {
        return missionList.size();
    }
}
