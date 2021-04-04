package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.R;

import java.util.List;

public class PassMonthWiseListAdapter extends RecyclerView.Adapter<PassMonthWiseListAdapter.PassMonthWiseViewHolder> {
    public List<PassEntity> passEntityList;
    private Context mContext;

    public PassMonthWiseListAdapter(List<PassEntity> passEntityList, Context mContext) {
        this.passEntityList = passEntityList;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public PassMonthWiseListAdapter.PassMonthWiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monthwise_list, parent, false);
        return new PassMonthWiseListAdapter.PassMonthWiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassMonthWiseListAdapter.PassMonthWiseViewHolder holder, int position) {
        holder.txtSNo.setText(passEntityList.get(position).sno + "");
        holder.txtID.setText(passEntityList.get(position).iNo + "");
        holder.txtRepNo.setText(passEntityList.get(position).repNo + "");
        holder.txtnewOld.setText(passEntityList.get(position).newOld);
        holder.txtDate.setText(passEntityList.get(position).date);
        holder.txtName.setText(passEntityList.get(position).name);
        holder.txtForm.setText(passEntityList.get(position).fromArea);
        holder.txtTo.setText(passEntityList.get(position).toArea);
        holder.txtBusFare.setText(passEntityList.get(position).busFare + "");
        holder.txtAmount.setText(passEntityList.get(position).amount + "");
        holder.txtExpDel.setText(passEntityList.get(position).expDel);
        holder.txtCelNo.setText(passEntityList.get(position).amount + "");
    }

    @Override
    public int getItemCount() {
        return passEntityList.size();
    }

    public class PassMonthWiseViewHolder extends RecyclerView.ViewHolder {
        TextView txtSNo,txtID, txtRepNo, txtnewOld, txtDate, txtName, txtForm, txtTo, txtBusFare, txtAmount, txtExpDel, txtCelNo;

        public PassMonthWiseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSNo = itemView.findViewById(R.id.item_month_wise_txtSNo);
            txtID = itemView.findViewById(R.id.item_month_wise_txtIno);
            txtRepNo = itemView.findViewById(R.id.item_month_wise_txtrepno);
            txtnewOld = itemView.findViewById(R.id.item_month_wise_txtnewOld);
            txtDate = itemView.findViewById(R.id.item_month_wise_txtdate);
            txtName = itemView.findViewById(R.id.item_month_wise_txtname);
            txtForm = itemView.findViewById(R.id.item_month_wise_txtform);
            txtTo = itemView.findViewById(R.id.item_month_wise_txtto);
            txtBusFare = itemView.findViewById(R.id.item_month_wise_txtbusfare);
            txtAmount = itemView.findViewById(R.id.item_txt_month_wise_totalamount);
            txtExpDel = itemView.findViewById(R.id.item_month_wise_txt_expdel);
            txtCelNo = itemView.findViewById(R.id.item_month_wise_txt_cellnumber);
        }
    }
}
