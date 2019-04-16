package com.example.easylearnhome.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.MainActivity;
import com.example.easylearnhome.R;
import com.example.easylearnhome.activities.PostDetailActivity;
import com.example.easylearnhome.activities.UserInfoActivity;
import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.PostInfo;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.LikeHelper;
import com.example.easylearnhome.utils.UheadUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.myHolder> {

    private List<PostInfo> posts;
    /**
     * 使用内部myHolder类来进行视图缓存
     */
    class myHolder extends RecyclerView.ViewHolder{
        TextView post_title;
        TextView post_content;
        TextView post_username;//用户名
        ImageView post_user_avater;//头像
        TextView post_time;//发表时间
        TextView post_like_num;
        TextView post_comment_num;
        ImageView imageView_like;
        ImageView imageView_comment;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            post_title = itemView.findViewById(R.id.post_title);
            post_content = itemView.findViewById(R.id.post_content);
            post_username = itemView.findViewById(R.id.post_username);
            post_user_avater = itemView.findViewById(R.id.post_user_avater);
            post_time = itemView.findViewById(R.id.post_time);
            post_like_num = itemView.findViewById(R.id.post_like_num);
            post_comment_num = itemView.findViewById(R.id.post_comment_num);
            imageView_like = itemView.findViewById(R.id.imageView_like);
            imageView_comment = itemView.findViewById(R.id.imageView_comment);
        }
    }

    public PostAdapter(List<PostInfo> posts){
        this.posts = posts;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder myHolder, int i) {
        PostInfo post = posts.get(i);
        myHolder.post_user_avater.setBackground(myHolder.itemView.getResources().getDrawable(UheadUtils.getUhead_id(post.getUserHead())));
        myHolder.post_username.setText(post.getUserName());
        myHolder.post_time.setText("发表于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(post.getPostTime()));
        myHolder.post_title.setText(post.getTitle());
        myHolder.post_content.setText(post.getContent());
        myHolder.post_like_num.setText(String.valueOf(post.getLikeNum()));
        myHolder.post_comment_num.setText(String.valueOf(post.getCommentNum()));

        myHolder.post_user_avater.setOnClickListener(view -> {
            Intent intent = new Intent(myHolder.itemView.getContext(), UserInfoActivity.class);
            intent.putExtra("uid",post.getUid());
            myHolder.itemView.getContext().startActivity(intent);
        });
        myHolder.post_username.setOnClickListener(view->{
            Intent intent = new Intent(myHolder.itemView.getContext(), UserInfoActivity.class);
            intent.putExtra("uid",post.getUid());
            myHolder.itemView.getContext().startActivity(intent);
        });
        myHolder.post_title.setOnClickListener((view -> {
//            Toast.makeText(myHolder.itemView.getContext(),"点击了标题！"+myHolder.post_title.getText(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myHolder.itemView.getContext(), PostDetailActivity.class);
            intent.putExtra("pid",post.getPid());
            myHolder.itemView.getContext().startActivity(intent);
        }));
        myHolder.post_content.setOnClickListener(view ->{
//            Toast.makeText(myHolder.itemView.getContext(),"点击了帖子内容！"+myHolder.post_content.getText(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myHolder.itemView.getContext(), PostDetailActivity.class);
            intent.putExtra("pid",post.getPid());
            myHolder.itemView.getContext().startActivity(intent);
        });
        myHolder.imageView_like.setOnClickListener(view -> {
            if (myHolder.imageView_like.getBackground().getConstantState().equals(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_fill).getConstantState())){
                new LikePostAsyncTask().execute(DataBaseUtil.getUid(new DatabaseHelper(myHolder.imageView_like.getContext()).getReadableDatabase()),post.getPid());
                //更新ui
                myHolder.imageView_like.setBackground(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_selected));
                myHolder.post_like_num.setText(String.format(Locale.getDefault(),"%d",Integer.parseInt(myHolder.post_like_num.getText().toString())+1));
            } else {
                new DeleteLikePostAsyncTask().execute(DataBaseUtil.getUid(new DatabaseHelper(myHolder.imageView_like.getContext()).getReadableDatabase()),post.getPid());
                //更新ui
                myHolder.imageView_like.setBackground(myHolder.itemView.getResources().getDrawable(R.drawable.ic_icon_likegood_fill));
                myHolder.post_like_num.setText(String.format(Locale.getDefault(),"%d",Integer.parseInt(myHolder.post_like_num.getText().toString())-1));
            }
        });
        myHolder.imageView_comment.setOnClickListener(view -> {
            Intent intent = new Intent(myHolder.itemView.getContext(), PostDetailActivity.class);
            intent.putExtra("pid",post.getPid());
            myHolder.itemView.getContext().startActivity(intent);
        });
    }

    /**
     * 点赞任务
     */
    class LikePostAsyncTask extends AsyncTask<Long,Void,Void>{

        @Override
        protected Void doInBackground(Long... longs) {
            try {
                LikeHelper.newLikePost(longs[0],longs[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    class DeleteLikePostAsyncTask extends AsyncTask<Long,Void,Void>{

        @Override
        protected Void doInBackground(Long... longs) {
            try {
                LikeHelper.deleteLikePost(longs[0],longs[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (posts!=null)
            return posts.size();
        return 0;
    }
}
