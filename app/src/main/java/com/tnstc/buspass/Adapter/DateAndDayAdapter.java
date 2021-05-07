package com.tnstc.buspass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintsChangedListener;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tnstc.buspass.Model.DateAndDay;
import com.tnstc.buspass.Others.ApplicationClass;
import com.tnstc.buspass.R;
import com.tnstc.buspass.callback.ItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class DateAndDayAdapter extends RecyclerView.Adapter<DateAndDayAdapter.itemViewHolder> {
    List<String> CurrentDateAndDay;
    ItemClickListener callback;
    int selectedPosition = 0;
    private Context context;
    int color = 0;
    String currentDate;

    public DateAndDayAdapter(List<String> currentDateAndDay, ItemClickListener callback) {
        CurrentDateAndDay = currentDateAndDay;
        this.callback = callback;
    }

    @NonNull
    @Override
    public DateAndDayAdapter.itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        context = parent.getContext();
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAndDayAdapter.itemViewHolder holder, int position) {
        selectedPosition = position;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy.EE");
        Date date = new Date();
        currentDate = simpleDateFormat.format(date);
        if (CurrentDateAndDay.get(position).equals(currentDate)) {
            holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.yellow_boder));
        }
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.setBackground(context.getResources().getDrawable(R.drawable.yellow_boder));
            }
        });
        String[] handleData = CurrentDateAndDay.get(position).split("\\.");
        holder.date.setText(handleData[0]);
        holder.day.setText(handleData[3]);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return CurrentDateAndDay.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        TextView date, day;
        ConstraintLayout constraintLayout;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_item);
            constraintLayout = itemView.findViewById(R.id.constraintLayout_date_day);
            day = itemView.findViewById(R.id.day_item);


        }
    }

}
