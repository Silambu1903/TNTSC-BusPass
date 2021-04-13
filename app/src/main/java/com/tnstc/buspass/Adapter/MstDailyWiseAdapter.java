package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.List;

public class MstDailyWiseAdapter extends RecyclerView.Adapter<MstDailyWiseAdapter.MstDailyViewHolder> {

    public List<MstEntity> mstEntityList ;
    private Context mContext;
    private ItemClickListener clickListener;

    public MstDailyWiseAdapter(List<MstEntity> mstEntityList, Context mContext, ItemClickListener clickListener) {
        this.mstEntityList = mstEntityList;
        this.mContext = mContext;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MstDailyWiseAdapter.MstDailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mst_list_item,parent,false);
        return new MstDailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MstDailyWiseAdapter.MstDailyViewHolder holder, int position) {
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(mContext);
        MstDao dao = db.mstDao();
        holder.date.setText(mstEntityList.get(position).date);
        holder.card.setText(mstEntityList.get(position).card+"");
        holder.key.setText(mstEntityList.get(position).key);
        holder.spare.setText(mstEntityList.get(position).spare+"");
        holder.opening.setText(mstEntityList.get(position).opening+"");
        holder.closing.setText(mstEntityList.get(position).closing+"");
        holder.totalcard.setText(mstEntityList.get(position).total+"");

    }

    @Override
    public int getItemCount() {
        return mstEntityList.size();
    }

    public static class MstDailyViewHolder extends RecyclerView.ViewHolder {
        EditText date,card,spare,key,opening,closing,totalcard;
        public MstDailyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            card = itemView.findViewById(R.id.item_card_200);
            spare = itemView.findViewById(R.id.item_spare);
            key = itemView.findViewById(R.id.item_key);
            opening = itemView.findViewById(R.id.item_opening);
            closing= itemView.findViewById(R.id.item_closing);
            totalcard=itemView.findViewById(R.id.item_total_card);

        }
    }
}
