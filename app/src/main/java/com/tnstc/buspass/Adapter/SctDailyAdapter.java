package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.SctEntity;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.List;

public class SctDailyAdapter extends RecyclerView.Adapter<SctDailyAdapter.SctDailyitemholder> {

    public List<SctEntity> sctEntityList;
    private Context mContext;
    private ItemClickListener clickListener;


    public SctDailyAdapter(List<SctEntity> sctEntityList, Context mContext, ItemClickListener clickListener) {
        this.sctEntityList = sctEntityList;
        this.mContext = mContext;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SctDailyAdapter.SctDailyitemholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sct_list_item,parent,false);
        return new SctDailyAdapter.SctDailyitemholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SctDailyAdapter.SctDailyitemholder holder, int position) {
        holder.date.setText(sctEntityList.get(position).date);
        holder.card.setText(sctEntityList.get(position).card+"");
        holder.key.setText(sctEntityList.get(position).key);
        holder.spare.setText(sctEntityList.get(position).spare+"");
        holder.opening.setText(sctEntityList.get(position).opening+"");
        holder.closing.setText(sctEntityList.get(position).closing+"");
        holder.totalcard.setText(sctEntityList.get(position).total+"");
        holder.amount.setText(sctEntityList.get(position).sctTotalAmount+"");

    }

    @Override
    public int getItemCount() {
        return sctEntityList.size();
    }

    public class SctDailyitemholder extends RecyclerView.ViewHolder {
        EditText date,card,spare,key,opening,closing,totalcard,amount;
        public SctDailyitemholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            card = itemView.findViewById(R.id.item_card_200);
            spare = itemView.findViewById(R.id.item_spare);
            key = itemView.findViewById(R.id.item_key);
            opening = itemView.findViewById(R.id.item_opening);
            closing= itemView.findViewById(R.id.item_closing);
            totalcard=itemView.findViewById(R.id.item_total_card);
            amount = itemView.findViewById(R.id.item_total);

        }
    }
}
