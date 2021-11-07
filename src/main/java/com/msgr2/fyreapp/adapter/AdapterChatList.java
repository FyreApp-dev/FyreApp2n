package com.msgr2.fyreapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.msgr2.fyreapp.R;
import com.msgr2.fyreapp.model.ModelChatList;

import java.util.List;

public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.Holder> {

    private List<ModelChatList> list;
    private Context context;

    public AdapterChatList(List<ModelChatList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        ModelChatList model = list.get(position);

        holder.tv_contactName.setText(model.getContactName());
        holder.tv_lastMessage.setText(model.getLastMessage());
        holder.tv_date.setText(model.getDate());
        if(!model.getUrlProfilePhoto().equals("")) {
            Glide.with(context).load(model.getUrlProfilePhoto()).into(holder.profilePhoto);
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
        private TextView tv_lastMessage;
        private TextView tv_date;
        private CircularImageView profilePhoto;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv_contactName = itemView.findViewById(R.id.contact_name);
            tv_lastMessage = itemView.findViewById(R.id.last_message);
            tv_date = itemView.findViewById(R.id.last_message_date);
            profilePhoto = itemView.findViewById(R.id.chat_list_profilePhoto);

        }
    }
}
