package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Model.PassEntry;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class PassEntryAdapter extends RecyclerView.Adapter<PassEntryAdapter.PassEntryViewHolder> {

    public List<PassEntity> passEntityList;

    private Context mContext;
    List<Integer> list = new ArrayList<>();
    private ItemClickListener clickListener;

    public PassEntryAdapter(Context mContext, List<PassEntity> passEntityList, ItemClickListener clickListener) {
        this.mContext = mContext;
        this.passEntityList = passEntityList;
        this.clickListener = clickListener;
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
        holder.txtnewOld.setText(passEntityList.get(position).newOld);
        holder.txtDate.setText(passEntityList.get(position).date);
        holder.txtName.setText(passEntityList.get(position).name);
        holder.txtForm.setText(passEntityList.get(position).fromArea);
        holder.txtTo.setText(passEntityList.get(position).toArea);
        holder.txtBusFare.setText(passEntityList.get(position).busFare + "");
        holder.txtAmount.setText(passEntityList.get(position).amount + "");
        holder.txtExpDel.setText(passEntityList.get(position).expDel);
        holder.txtCelNo.setText(passEntityList.get(position).amount + "");

        holder.txtID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TnstcBusPassDB db = TnstcBusPassDB.getDatabase(mContext);
                PassDao dao =db.passDao();
                dao.updateino(Integer.parseInt(holder.txtID.getText().toString()),
                        Integer.parseInt(holder.txtRepNo.getText().toString()),holder.txtnewOld.getText().toString(),holder.txtDate.getText().toString(),
                        holder.txtName.getText().toString(),holder.txtForm.getText().toString(),holder.txtTo.getText().toString(),
                        Integer.parseInt(holder.txtBusFare.getText().toString()), Integer.parseInt(holder.txtAmount.getText().toString()),
                                holder.txtCelNo.getText().toString(),holder.txtCelNo.getText().toString(),
                        passEntityList.get(position).getSno());


            }
        });
    }

    public int getItemCount() {
        return passEntityList.size();
    }


    public class PassEntryViewHolder extends RecyclerView.ViewHolder {
        TextView txtSNo;
        EditText txtID, txtRepNo,txtnewOld, txtDate, txtName, txtForm, txtTo, txtBusFare, txtAmount, txtExpDel, txtCelNo;

        public PassEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSNo = itemView.findViewById(R.id.item_txtSNo);
            txtID = itemView.findViewById(R.id.item_txtIno);
            txtRepNo = itemView.findViewById(R.id.item_txtrepno);
            txtnewOld = itemView.findViewById(R.id.item_txtnewOld);
            txtDate = itemView.findViewById(R.id.item_txtdate);
            txtName = itemView.findViewById(R.id.item_txtname);
            txtForm = itemView.findViewById(R.id.item_txtform);
            txtTo = itemView.findViewById(R.id.item_txtto);
            txtBusFare = itemView.findViewById(R.id.item_txtbusfare);
            txtAmount = itemView.findViewById(R.id.item_txttotalamount);
            txtExpDel = itemView.findViewById(R.id.item_txt_expdel);
            txtCelNo = itemView.findViewById(R.id.item_txt_cellnumber);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickListener.OnItemLongClick(view, getAdapterPosition());
                    return true;
                }
            });


        }
    }
}
