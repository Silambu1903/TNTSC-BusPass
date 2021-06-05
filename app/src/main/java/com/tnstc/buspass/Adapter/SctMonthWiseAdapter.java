package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.SctOpeningClosing;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.List;

public class SctMonthWiseAdapter extends RecyclerView.Adapter<SctMonthWiseAdapter.itemViewHolder> {


    public List<SctOpeningClosing> SctOpeningClosing;
    ItemClickListener itemClickListener;

    public SctMonthWiseAdapter(List<SctOpeningClosing> sctOpeningClosing, ItemClickListener itemClickListener) {
        SctOpeningClosing = sctOpeningClosing;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SctMonthWiseAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sct_month_list_item, parent, false);
        return new SctMonthWiseAdapter.itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SctMonthWiseAdapter.itemViewHolder holder, int position) {
        holder.mstMonthCard.setText(SctOpeningClosing.get(position).sctCard+"");
        holder.mstMonthKey.setText(SctOpeningClosing.get(position).sctKey+"");
        holder.mstMonthOpening.setText(SctOpeningClosing.get(position).sctOpening+"");
        holder.mstMonthClosing.setText(SctOpeningClosing.get(position).sctClosing+"");
        holder.mstTotalCard.setText(SctOpeningClosing.get(position).sctTotal+"");
        holder.getOpen.setText(SctOpeningClosing.get(position).sctOpenMax+"");
        holder.getClose.setText(SctOpeningClosing.get(position).sctCloseMax+"");
        holder.getTotalCard.setText(SctOpeningClosing.get(position).sctTotalCard+"");
        holder.getTotalAmount.setText(SctOpeningClosing.get(position).sctTotalAmount+"");
        holder.balOpen.setText(SctOpeningClosing.get(position).sctBalOpen+"");
        holder.balClose.setText(SctOpeningClosing.get(position).sctBalClose+"");
        holder.balCard.setText(SctOpeningClosing.get(position).sctBalCard+"");
        holder.balTotalScale.setText(SctOpeningClosing.get(position).sctBalTotalCard+"");
    }

    @Override
    public int getItemCount() {
        return SctOpeningClosing.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView mstMonthCard, mstMonthKey, mstMonthOpening, mstMonthClosing, mstTotalCard,
                getOpen, getClose, getTotalCard, getTotalAmount, balOpen, balClose, balCard, balTotalScale;
        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.con_sct);
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
