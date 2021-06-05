package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.List;

public class MstMonthWiseAdapter extends RecyclerView.Adapter<MstMonthWiseAdapter.MstMonthViewHolder> {

    public List<MstOpeningClosing> mstOpeningClosings;
    public ItemClickListener itemClickListener;


    public MstMonthWiseAdapter(List<MstOpeningClosing> mstOpeningClosings, ItemClickListener itemClickListener) {
        this.mstOpeningClosings = mstOpeningClosings;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MstMonthWiseAdapter.MstMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mst_month_list_item, parent, false);
        return new MstMonthWiseAdapter.MstMonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MstMonthWiseAdapter.MstMonthViewHolder holder, int position) {
        holder.mstMonthCard.setText(mstOpeningClosings.get(position).mstCard + "");
        holder.mstMonthKey.setText(mstOpeningClosings.get(position).mstKey + "");
        holder.mstMonthOpening.setText(mstOpeningClosings.get(position).mstOpening + "");
        holder.mstMonthClosing.setText(mstOpeningClosings.get(position).mstClosing + "");
        holder.mstTotalCard.setText(mstOpeningClosings.get(position).mstTotal + "");
        holder.getOpen.setText(mstOpeningClosings.get(position).mstOpenMax + "");
        holder.getClose.setText(mstOpeningClosings.get(position).mstCloseMax + "");
        holder.getTotalCard.setText(mstOpeningClosings.get(position).mstTotalCard + "");
        holder.getTotalAmount.setText(mstOpeningClosings.get(position).mstTotalAmount + "");
        holder.balOpen.setText(mstOpeningClosings.get(position).mstBalOpen + "");
        holder.balClose.setText(mstOpeningClosings.get(position).mstBalClose + "");
        holder.balCard.setText(mstOpeningClosings.get(position).mstBalCard + "");
        holder.balTotalScale.setText(mstOpeningClosings.get(position).mstBalTotalCard + "");
    }

    @Override
    public int getItemCount() {
        return mstOpeningClosings.size();
    }

    public  class MstMonthViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView mstMonthCard, mstMonthKey, mstMonthOpening, mstMonthClosing, mstTotalCard,
                getOpen, getClose, getTotalCard, getTotalAmount, balOpen, balClose, balCard, balTotalScale;

        public MstMonthViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.con_mst);
            mstMonthCard = itemView.findViewById(R.id.month_mst_card_200);
            mstMonthKey = itemView.findViewById(R.id.month_mst_key_200);
            mstMonthOpening = itemView.findViewById(R.id.month_mst_open_200);
            mstMonthClosing = itemView.findViewById(R.id.month_mst_close_200);
            mstTotalCard = itemView.findViewById(R.id.month_mst_total_200);
            getOpen = itemView.findViewById(R.id.month_mst_scales_total_open_200);
            getClose = itemView.findViewById(R.id.month_mst_scales_total_close_200);
            getTotalCard = itemView.findViewById(R.id.month_mst_scales_total_scales_200);
            getTotalAmount = itemView.findViewById(R.id.month_mst_scales_total_scales_amount_200);
            balOpen = itemView.findViewById(R.id.month_mst_scales_bal_open_200);
            balClose = itemView.findViewById(R.id.month_mst_scales_bal_close_200);
            balCard = itemView.findViewById(R.id.month_mst_scales_bal_card_200);
            balTotalScale = itemView.findViewById(R.id.month_mst_scales_bal_total_200);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemLongClick(v,getAdapterPosition(),constraintLayout);
                }
            });
        }
    }
}
