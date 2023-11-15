package com.example.mychatbot.activites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mychatbot.databinding.ChatItemBinding;
import com.example.mychatbot.model.Message;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    List<Message> messageList;

    public MessageListAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ChatItemBinding binding = ChatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (message.getRole() == Message.SENT_BY_USER) {
            holder.binding.leftChatView.setVisibility(View.GONE);
            holder.binding.rightChatView.setVisibility(View.VISIBLE);
            holder.binding.rightChatTextView.setText(message.getContent());
        } else {
            holder.binding.rightChatView.setVisibility(View.GONE);
            holder.binding.leftChatView.setVisibility(View.VISIBLE);
            holder.binding.leftChatTextView.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ChatItemBinding binding;

        public MyViewHolder(@NonNull ChatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
