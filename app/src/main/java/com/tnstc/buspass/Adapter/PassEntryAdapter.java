package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.R;

import java.util.List;

public class PassEntryAdapter extends RecyclerView.Adapter<PassEntryAdapter.PassEntryViewHolder> {

    public List<PassEntity> passEntityList;
    private Context mContext;

    public PassEntryAdapter(Context mContext, List<PassEntity> passEntityList) {
        this.mContext = mContext;
        this.passEntityList = passEntityList;
    }


    @NonNull
    @Override
    public PassEntryAdapter.PassEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pass_entry_list, parent, false);
        return new PassEntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PassEntryAdapter.PassEntryViewHolder holder, int position) {

        holder.txtSNo.setText(passEntityList.get(position).sno + "");
        holder.txtID.setText(passEntityList.get(position).iNo + "");
        holder.txtRepNo.setText(passEntityList.get(position).repNo + "");
        holder.txtDate.setText(passEntityList.get(position).date);
        holder.txtName.setText(passEntityList.get(position).name);
        holder.txtForm.setText(passEntityList.get(position).fromArea);
        holder.txtTo.setText(passEntityList.get(position).toArea);
        holder.txtBusFare.setText(passEntityList.get(position).busFare + "");
        holder.txtAmount.setText(passEntityList.get(position).amount + "");
        holder.txtExpDel.setText(passEntityList.get(position).expDel);
        holder.txtCelNo.setText(passEntityList.get(position).cellNumber);
    }

    @Override
    public int getItemCount() {
        return passEntityList.size();
    }


    public class PassEntryViewHolder extends RecyclerView.ViewHolder {
        TextView txtSNo, txtID, txtRepNo, txtDate, txtName, txtForm, txtTo, txtBusFare, txtAmount, txtExpDel, txtCelNo;

        public PassEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSNo = itemView.findViewById(R.id.item_txtSNo);
            txtID = itemView.findViewById(R.id.item_txtIno);
            txtRepNo = itemView.findViewById(R.id.item_txtrepno);
            txtDate = itemView.findViewById(R.id.item_txtdate);
            txtName = itemView.findViewById(R.id.item_txtname);
            txtForm = itemView.findViewById(R.id.item_txtform);
            txtTo = itemView.findViewById(R.id.item_txtto);
            txtBusFare = itemView.findViewById(R.id.item_txtbusfare);
            txtAmount = itemView.findViewById(R.id.item_txttotalamount);
            txtExpDel = itemView.findViewById(R.id.item_txt_expdel);
            txtCelNo = itemView.findViewById(R.id.item_txt_cellnumber);

        }
    }
}
