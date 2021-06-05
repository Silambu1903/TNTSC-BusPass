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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.TnstcBusPassDB;
import com.tnstc.buspass.Model.PassEntry;
import com.tnstc.buspass.Others.MultiTextWatcher;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;
import com.tnstc.buspass.callback.TextWatcherWithInstance;

import java.util.ArrayList;
import java.util.List;

public class PassEntryAdapter extends RecyclerView.Adapter<PassEntryAdapter.PassEntryViewHolder> {

    public List<PassEntity> passEntityList;

    private Context mContext;
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
        TnstcBusPassDB db = TnstcBusPassDB.getDatabase(mContext);
        PassDao dao = db.passDao();
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
        holder.txtCelNo.setText(passEntityList.get(position).cellNumber + "");


        holder.txtRepNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtRepNo.getText().toString().equals("")) {
                    dao.updaterRepNo(Integer.parseInt(holder.txtRepNo.getText().toString()),
                            passEntityList.get(position).getSno());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtnewOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtnewOld.getText().toString().equals("")) {
                    dao.updateNewOld(holder.txtnewOld.getText().toString(),
                            passEntityList.get(position).getSno());
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtDate.getText().toString().equals("")) {
                    dao.updateDate(holder.txtDate.getText().toString(),
                            passEntityList.get(position).getSno());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtName.getText().toString().equals("")) {
                    dao.updateName(holder.txtName.getText().toString(),
                            passEntityList.get(position).getSno());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtForm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtForm.getText().toString().equals("")) {
                    dao.updatefrom(holder.txtForm.getText().toString(),
                            passEntityList.get(position).getSno());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dao.updateTo(holder.txtTo.getText().toString(),
                        passEntityList.get(position).getSno());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtBusFare.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtBusFare.getText().toString().equals("")) {
                    dao.busFare(Integer.parseInt(holder.txtBusFare.getText().toString()),
                            passEntityList.get(position).getSno());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtAmount.getText().toString().equals("")) {
                    dao.amount(Integer.parseInt(holder.txtAmount.getText().toString()),
                            passEntityList.get(position).getSno());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtExpDel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.txtExpDel.getText().toString().equals("")) {
                    dao.expDel(holder.txtExpDel.getText().toString(),
                            passEntityList.get(position).getSno());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.txtCelNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (holder.txtCelNo.getText().toString().equals("")) {
                    dao.cellNumber(holder.txtCelNo.getText().toString(),
                            passEntityList.get(position).getSno());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public int getItemCount() {
        return passEntityList.size();
    }


    public class PassEntryViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView txtSNo,txtID;
        EditText  txtRepNo, txtnewOld, txtDate, txtName, txtForm, txtTo, txtBusFare, txtAmount, txtExpDel, txtCelNo;

        public PassEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constaintpass);
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
                    clickListener.OnItemLongClick(view, getAdapterPosition(),constraintLayout);
                    return true;
                }
            });


        }
    }
}
