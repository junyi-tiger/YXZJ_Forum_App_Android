package com.example.easylearnhome.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easylearnhome.R;
import com.example.easylearnhome.entities.ReplyInfo;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.myHolder>{

    List<ReplyInfo> replyInfos;

    public ReplyAdapter(List<ReplyInfo> infos){
        replyInfos = infos;
    }
    @NonNull
    @Override
    public ReplyAdapter.myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reply_item, viewGroup, false);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.myHolder myHolder, int i) {
        ReplyInfo replyInfo = replyInfos.get(i);
        myHolder.reply_username.setText(replyInfo.getUname());
        myHolder.reply_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(replyInfo.getReply_time()));
        myHolder.reply_Content.setText(replyInfo.getRcontent());
    }

    @Override
    public int getItemCount() {
        if (replyInfos!=null)
            return replyInfos.size();
        return 0;
    }

    class myHolder extends RecyclerView.ViewHolder{
        TextView reply_username;
        TextView reply_Content;
        TextView reply_time;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            reply_username = itemView.findViewById(R.id.reply_username);
            reply_Content = itemView.findViewById(R.id.reply_content);
            reply_time = itemView.findViewById(R.id.reply_time);
        }
    }
}
