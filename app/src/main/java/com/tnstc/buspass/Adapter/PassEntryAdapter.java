package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Model.PassEntry;
import com.tnstc.buspass.R;

import java.util.List;

public class PassEntryAdapter extends RecyclerView.Adapter<PassEntryAdapter.PassEntryViewHolder> {

    public List<PassEntry> passEntryList;
    private Context mContext;

    public PassEntryAdapter(List<PassEntry> passEntryList, Context mContext) {
        this.passEntryList = passEntryList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PassEntryAdapter.PassEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pass_entry_list, parent, false);
        return new PassEntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PassEntryAdapter.PassEntryViewHolder holder, int position) {
        PassEntry passEntry = this.passEntryList.get(position);
        PassEntity passEntity = passEntry.getPassEntryList();
        holder.txtSNo.setText(passEntity.sno);
        holder.txtID.setText(passEntity.iNo);
        holder.txtRepNo.setText(passEntity.repNo);
        holder.txtDate.setText(passEntity.date);
        holder.txtName.setText(passEntity.name);
        holder.txtForm.setText(passEntity.fromArea);
        holder.txtTo.setText(passEntity.toArea);
        holder.txtBusFare.setText(passEntity.busFare);
        holder.txtAmount.setText(passEntity.amount);
        holder.txtExpDel.setText(passEntity.expDel);
        holder.txtCelNo.setText(passEntity.cellNumber);


    }

    @Override
    public int getItemCount() {
        return passEntryList.size();
    }

    public class PassEntryViewHolder extends RecyclerView.ViewHolder {
        TextView txtSNo, txtID,txtRepNo,txtDate, txtName,txtForm,txtTo,txtBusFare,txtAmount,txtExpDel,txtCelNo;
        public PassEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSNo = itemView.findViewById(R.id.txtSNo);
            txtID = itemView.findViewById(R.id.txtIno);
            txtRepNo = itemView.findViewById(R.id.txtrepno);
            txtDate = itemView.findViewById(R.id.txtdate);
            txtName = itemView.findViewById(R.id.txtname);
            txtForm = itemView.findViewById(R.id.txtform);
            txtTo = itemView.findViewById(R.id.txtto);
            txtBusFare = itemView.findViewById(R.id.txtbusfare);
            txtAmount = itemView.findViewById(R.id.txt_total_amount);
            txtExpDel = itemView.findViewById(R.id.txt_expdel);
            txtCelNo = itemView.findViewById(R.id.txt_cellnumber);

        }
    }
}
