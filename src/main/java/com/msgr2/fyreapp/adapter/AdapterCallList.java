package com.msgr2.fyreapp.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.model.ModelCallList;

import java.util.List;

public class AdapterCallList extends RecyclerView.Adapter<AdapterCallList.Holder> {
    private List<ModelCallList> list;
    private Context context;

    public AdapterCallList(List<ModelCallList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_call_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        ModelCallList model = list.get(position);

        holder.tv_contactName.setText(model.getContactName());
        holder.tv_callTime.setText(model.getCallTime());
        if (model.getCallType().equals("missed")) {
            holder.ic_callType.setImageResource(R.drawable.ic_baseline_call_missed_outgoing_17);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.ic_callType.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_red_dark));
            }
        } else if (model.getCallType().equals("income")) {

            holder.ic_callType.setImageResource(R.drawable.ic_baseline_call_received_17);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.ic_callType.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_orange_dark));
            }

        } else {
            holder.ic_callType.setImageResource(R.drawable.ic_baseline_call_made_17);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.ic_callType.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_green_dark));
            }
        }
        if (!model.getUrlContactPhoto().equals("")) {
            Glide.with(context).load(model.getUrlContactPhoto()).into(holder.contact_profilePhoto);
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tv_contactName;
        private TextView tv_callTime;
        private CircularImageView contact_profilePhoto;
        private ImageView ic_callType;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_contactName = itemView.findViewById(R.id.call_list_contact_name);
            tv_callTime = itemView.findViewById(R.id.call_time);
            contact_profilePhoto = itemView.findViewById(R.id.call_list_profilePhoto);
            ic_callType = itemView.findViewById(R.id.ic_call_type);

        }
    }
}
