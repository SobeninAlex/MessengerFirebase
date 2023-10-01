package com.example.messengerfirebase.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private static final int VIEW_TYPE_MY_MESSAGE = 100;
    private static final int VIEW_TYPE_OTHER_MESSAGE = 101;

    private List<Message> messages = new ArrayList<>();

    private String currentUserID;

    public MessagesAdapter(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    //определяем ViewType который затем прилетит в onCreateViewHolder
    @Override
    public int getItemViewType(int position) {
        var message = messages.get(position);
        if (message.getSenderID().equals(currentUserID)) {
            return VIEW_TYPE_MY_MESSAGE;
        }
        else {
            return VIEW_TYPE_OTHER_MESSAGE;
        }
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resource = R.layout.my_message_item;
        if (viewType == VIEW_TYPE_OTHER_MESSAGE) {
            resource = R.layout.other_message_item;
        }
        var view = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        var message = messages.get(position);
        holder.textViewMessage.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessagesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }

}
