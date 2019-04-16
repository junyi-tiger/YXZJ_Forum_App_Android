package com.example.easylearnhome.adapters;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.R;
import com.example.easylearnhome.activities.CommentDetailActivity;
import com.example.easylearnhome.activities.UserInfoActivity;
import com.example.easylearnhome.entities.CommentInfo;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.LikeHelper;
import com.example.easylearnhome.utils.UheadUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.myHolder> {

    private List<CommentInfo> comments;
    /**
     * 使用内部myHolder类来进行视图缓存
     */
    class myHolder extends RecyclerView.ViewHolder{
        ImageView comment_avater;
        TextView comment_username;
        TextView comment_time;
        TextView comment_content;
        ImageView imageView_comment_like;
        TextView comment_like_num;
        ImageView imageView_comment_reply;
        TextView comment_replies_num;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            comment_avater = itemView.findViewById(R.id.comment_avater);
            comment_username = itemView.findViewById(R.id.comment_username);
            comment_time = itemView.findViewById(R.id.comment_time);
            comment_content = itemView.findViewById(R.id.comment_content);
            imageView_comment_like = itemView.findViewById(R.id.imageView_comment_like);
            comment_like_num = itemView.findViewById(R.id.comment_like_num);
            imageView_comment_reply = itemView.findViewById(R.id.imageView_comment_reply);
            comment_replies_num = itemView.findViewById(R.id.comment_replies_num);
        }
    }

    public CommentAdapter(List<CommentInfo> comments){
        this.comments = comments;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_item, viewGroup, false);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        CommentInfo commentInfo = comments.get(i);
        if (commentInfo.getUHead()!=null)
            myHolder.comment_avater.setBackground(myHolder.itemView.getContext().getResources().getDrawable(UheadUtils.getUhead_id(commentInfo.getUHead())));
        myHolder.comment_username.setText(commentInfo.getUName());
        myHolder.comment_time.setText("发表于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(commentInfo.getComment_time()));
        myHolder.comment_content.setText(commentInfo.getCContent());
        myHolder.comment_like_num.setText(String.valueOf(commentInfo.getCLike_num()));
        myHolder.comment_replies_num.setText(String.valueOf(commentInfo.getC_reply_num()));
        myHolder.comment_content.setOnClickListener(view -> {
            Intent intent = new Intent(myHolder.itemView.getContext(), CommentDetailActivity.class);
            intent.putExtra("cid",commentInfo.getCID());
            myHolder.itemView.getContext().startActivity(intent);
        });

        myHolder.imageView_comment_reply.setOnClickListener(view -> {
            Intent intent = new Intent(myHolder.itemView.getContext(), CommentDetailActivity.class);
            intent.putExtra("cid",commentInfo.getCID());
            myHolder.itemView.getContext().startActivity(intent);
        });

        myHolder.comment_avater.setOnClickListener(view -> {
            Intent intent = new Intent(myHolder.itemView.getContext(), UserInfoActivity.class);
            intent.putExtra("uid",commentInfo.getUID());
            myHolder.itemView.getContext().startActivity(intent);
        });
        myHolder.comment_username.setOnClickListener((view -> {
//            Toast.makeText(myHolder.itemView.getContext(),"点击了用户名！"+myHolder.comment_username.getText(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myHolder.itemView.getContext(), UserInfoActivity.class);
            intent.putExtra("uid",commentInfo.getUID());
            myHolder.itemView.getContext().startActivity(intent);
        }));
        myHolder.comment_content.setOnClickListener(view ->{
//            Toast.makeText(myHolder.itemView.getContext(),"点击了评论内容！"+myHolder.comment_content.getText(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myHolder.itemView.getContext(),CommentDetailActivity.class);
            intent.putExtra("cid",commentInfo.getCID());
            myHolder.itemView.getContext().startActivity(intent);
        });
        myHolder.imageView_comment_like.setOnClickListener(view -> {
//            Toast.makeText(myHolder.itemView.getContext(),"点击了点赞按钮！评论id："+commentInfo.getCID(),Toast.LENGTH_SHORT).show();
            if (myHolder.imageView_comment_like.getBackground().getConstantState().equals(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_fill).getConstantState())){
                new LikeCommentAsyncTask().execute(DataBaseUtil.getUid(new DatabaseHelper(myHolder.imageView_comment_like.getContext()).getReadableDatabase()),commentInfo.getPID());
                //更新ui
                myHolder.imageView_comment_like.setBackground(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_selected));
                myHolder.comment_like_num.setText(String.format(Locale.getDefault(),"%d",Integer.parseInt(myHolder.comment_like_num.getText().toString())+1));
            } else {
                new DeleteLikeCommentAsyncTask().execute(DataBaseUtil.getUid(new DatabaseHelper(myHolder.imageView_comment_like.getContext()).getReadableDatabase()),commentInfo.getPID());
                //更新ui
                myHolder.imageView_comment_like.setBackground(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_fill));
                myHolder.comment_like_num.setText(String.format(Locale.getDefault(),"%d",Integer.parseInt(myHolder.comment_like_num.getText().toString())-1));
            }
        });
        myHolder.imageView_comment_reply.setOnClickListener(view -> {
//            Toast.makeText(myHolder.itemView.getContext(),"点击了回复按钮！帖子id："+commentInfo.getCID(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myHolder.itemView.getContext(),CommentDetailActivity.class);
            intent.putExtra("cid",commentInfo.getCID());
            myHolder.itemView.getContext().startActivity(intent);
        });
    }

    //评论的点赞任务
    class LikeCommentAsyncTask extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... longs) {
            try {
                LikeHelper.newLikeComment(longs[0],longs[1]);
            } catch (Exception e){
            }
            return null;
        }
    }

    //取消评论的点赞任务
    class DeleteLikeCommentAsyncTask extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... longs) {
            try{
                LikeHelper.deleteLikeComment(longs[0],longs[1]);
            } catch (Exception e){
            }
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (comments!=null)
            return comments.size();
        return 0;
    }
}
