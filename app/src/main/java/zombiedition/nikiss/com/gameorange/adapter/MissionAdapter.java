package zombiedition.nikiss.com.gameorange.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zombiedition.nikiss.com.gameorange.R;
import zombiedition.nikiss.com.gameorange.dto.Mission;

/**
 * Created by issouf on 02/09/18.
 */

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MyViewHolder> {

    private List<Mission> missionList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameMission;
        public TextView level;
        public TextView description;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);

            nameMission = (TextView) view.findViewById(R.id.txtNameMission);
            level = (TextView) view.findViewById(R.id.txtLevel);
            description = (TextView) view.findViewById(R.id.txtDescription);
            img = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public MissionAdapter(List<Mission> missionList) {
        this.missionList = missionList;
    }


    @Override
    public MissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liste_mission, parent, false);

        return new MissionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MissionAdapter.MyViewHolder holder, int position) {
        Mission mission = missionList.get(position);

        holder.nameMission.setText(mission.getNameMission());
        holder.level.setText("Mission "+mission.getLevel());
        holder.description.setText(mission.getDescription());
        holder.itemView.setTag(mission);

        switch (mission.getLevel()) {
            case 1:
                holder.img.setBackgroundResource(R.drawable.alienblue);
                break;

            case 2:
                holder.img.setBackgroundResource(R.drawable.alienyellow);
                break;

            case 3:
                holder.img.setBackgroundResource(R.drawable.moto_img);
                break;
            case 4:
                holder.img.setBackgroundResource(R.drawable.taxi_img);
                break;

            default:
                holder.img.setImageResource(R.drawable.if_car_yellow);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return missionList.size();
    }
}
