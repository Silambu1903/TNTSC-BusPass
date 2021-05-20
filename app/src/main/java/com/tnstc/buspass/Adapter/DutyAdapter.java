package com.tnstc.buspass.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.List;

public class DutyAdapter extends RecyclerView.Adapter<DutyAdapter.itemViewholder> {

    List<DutyEntity> dutyEntities;
    ItemClickListener itemClickListener;
    String dateChanged;

    public DutyAdapter(List<DutyEntity> dutyEntities, ItemClickListener itemClickListener) {
        this.dutyEntities = dutyEntities;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DutyAdapter.itemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.duty_list_item, parent, false);
        return new DutyAdapter.itemViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DutyAdapter.itemViewholder holder, int position) {
        String [] dateSplit = dutyEntities.get(position).getDutyDate().split("\\.");
        dateChanged = dateSplit[0]+dateSplit[1]+dateSplit[2]+dateSplit[3];
        holder.duty.setText(dutyEntities.get(position).getWorkDuty());
        holder.dutyDate.setText(dateSplit[0]+"-"+dateSplit[1]+"-"+dateSplit[2]);

    }

    @Override
    public int getItemCount() {
        return dutyEntities.size();
    }

    public class itemViewholder extends RecyclerView.ViewHolder {
        TextView duty, dutyDate;
        View delete;

        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            duty = itemView.findViewById(R.id.Duty);
            dutyDate = itemView.findViewById(R.id.Duty_date);
            delete = itemView.findViewById(R.id.duty_delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemDate(getAdapterPosition(),dutyEntities);
                }
            });
        }
    }
}
