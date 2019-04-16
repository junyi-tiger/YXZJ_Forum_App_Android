package com.example.easylearnhome.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.R;
import com.example.easylearnhome.adapters.CommentAdapter;
import com.example.easylearnhome.entities.CommentInfo;
import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.PostInfo;
import com.example.easylearnhome.entities.User;
import com.example.easylearnhome.utils.CollectionHelper;
import com.example.easylearnhome.utils.CommentHelper;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.LikeHelper;
import com.example.easylearnhome.utils.PostHelper;
import com.example.easylearnhome.utils.UheadUtils;
import com.example.easylearnhome.utils.UserHelper;

import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.easylearnhome.utils.CommentHelper.getAllCommentOfPost;

public class PostDetailActivity extends AppCompatActivity {

    PostDetailActivity the;
    static PostInfo post_Info = null;

    FloatingActionButton fab_collect_post;

    RecyclerView recyclerView;
    ImageView imageView_author_avater;
    TextView post_detail_username;
    TextView post_detail_title;
    TextView post_detail_content;
    TextView post_detail_post_time;
    ImageView imageView_like_detail;
    TextView post_like_num_detail;
    ImageView imageView_comment_detail;
    TextView post_comment_num_detail;

    EditText new_comment_content;
    Button button_new_comment;

    Long pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        the = this;
        Intent intent = getIntent();
        pid = intent.getLongExtra("pid",1L);
        setContentView(R.layout.activity_post_detail);

        fab_collect_post = findViewById(R.id.fab_collect_post);
        fab_collect_post.setOnClickListener(view -> {
            Long uid = DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase());
            new CollectPostAsyncTask().execute(uid,pid);
        });

        //初始化控件
        imageView_author_avater = findViewById(R.id.imageView_author_avater);
        post_detail_username = findViewById(R.id.post_detail_username);
        post_detail_title = findViewById(R.id.post_detail_title);
        post_detail_content = findViewById(R.id.post_detail_content);
        post_detail_post_time = findViewById(R.id.post_detail_post_time);
        imageView_like_detail = findViewById(R.id.imageView_like_detail);
        post_like_num_detail = findViewById(R.id.post_like_num_detail);
        imageView_comment_detail = findViewById(R.id.imageView_comment_detail);
        post_comment_num_detail = findViewById(R.id.post_comment_num_detail);

        new_comment_content = findViewById(R.id.new_comment_content);
        button_new_comment = findViewById(R.id.button_new_comment);


        imageView_like_detail.setOnClickListener(view -> {
            if (imageView_like_detail.getBackground().getConstantState().equals(imageView_like_detail.getResources().getDrawable(R.drawable.ic_icon_likegood_fill).getConstantState())){
                new LikePostAsyncTask().execute(post_Info.getUid(),pid);
                imageView_like_detail.setBackground(the.getResources().getDrawable(R.drawable.ic_icon_likegood_selected));
                if (post_Info!=null)post_like_num_detail.setText(String.format("%d",post_Info.getLikeNum()+1));
            } else {
                new DeleteLikePostAsyncTask().execute(post_Info.getUid(),pid);
                imageView_like_detail.setBackground(the.getResources().getDrawable(R.drawable.ic_icon_likegood_fill));
                if (post_Info!=null)post_like_num_detail.setText(String.format("%d",post_Info.getLikeNum()));
            }
        });
        imageView_comment_detail.setOnClickListener(view -> {
//            Toast.makeText(getApplicationContext(),"评论帖子",Toast.LENGTH_SHORT).show();
            new_comment_content.requestFocus();
        });

        //为发表评论按钮设置监听器
        button_new_comment.setOnClickListener(view -> {
            String content = new_comment_content.getText().toString();
            if (content==null||content.equals("")){
                Toast.makeText(getApplicationContext(),"请输入帖子内容！",Toast.LENGTH_SHORT).show();
                return;
            }else {
                new Thread(()->{
                    try{
                        CommentHelper.newComment(DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase()),pid,content);
                        RefreshUI();
                    } catch (Exception e){
                    }
                }).start();
                new_comment_content.setText("");
            }
        });

        recyclerView = findViewById(R.id.comments_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        new getPostInfoAsyncTask().execute(pid);
        new getAllCommentsAsyncTask().execute(pid);
        new QueryCollectionAsyncTask().execute(DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase()),pid);
    }

    void RefreshUI(){
        new getPostInfoAsyncTask().execute(pid);
        new getAllCommentsAsyncTask().execute(pid);
    }


    //查询是否收藏了
    class QueryCollectionAsyncTask extends AsyncTask<Long,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Long... longs) {
            try{
                boolean is = CollectionHelper.hasCollection(longs[0],longs[1]);
                return is;
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                //更新ui
                fab_collect_post.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            }
        }
    }

    //新增帖子收藏
    class CollectPostAsyncTask extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... longs) {
            if (fab_collect_post.getSupportBackgroundTintList().getDefaultColor()==getResources().getColor(R.color.colorWhite)){
                fab_collect_post.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                //执行收藏任务
                try {
                    CollectionHelper.newCollection(longs[0], longs[1]);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                fab_collect_post.setSupportBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
                //执行取消收藏任务
                try{
                    CollectionHelper.deleteCollection(longs[0],longs[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    //帖子点赞
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

    //取消帖子点赞
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

    //获取帖子信息
    class getPostInfoAsyncTask extends AsyncTask<Long, Void, PostInfo>{
        @Override
        protected PostInfo doInBackground(Long... longs) {
            PostInfo postInfo = null;
            try{
                postInfo = getPostInfo(longs[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return postInfo;
        }

        @Override
        protected void onPostExecute(PostInfo postInfo) {
            super.onPostExecute(postInfo);
            post_Info = postInfo;
            //设置点击事件
            imageView_author_avater.setOnClickListener(view -> {
                Intent intent1 = new Intent(getApplicationContext(),UserInfoActivity.class);
                intent1.putExtra("uid",post_Info.getUid());
                startActivity(intent1);
            });
            post_detail_username.setOnClickListener(view -> {
                Intent intent1 = new Intent(getApplicationContext(),UserInfoActivity.class);
                intent1.putExtra("uid",post_Info.getUid());
                startActivity(intent1);
            });

            //获取到了postinfo，更新UI
            if (postInfo.getUserHead()!=null)
                imageView_author_avater.setBackground(getResources().getDrawable(UheadUtils.getUhead_id(postInfo.getUserHead())));
            post_detail_username.setText(postInfo.getUserName());
            post_detail_title.setText(postInfo.getTitle());
            post_detail_content.setText(postInfo.getContent());
            post_detail_post_time.setText("发表于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(postInfo.getPostTime()));
            post_like_num_detail.setText(String.valueOf(postInfo.getLikeNum()));
            post_comment_num_detail.setText(String.valueOf(postInfo.getCommentNum()));
        }
    }

    //获取所有评论信息
    class getAllCommentsAsyncTask extends AsyncTask<Long,Void, List<CommentInfo>>{
        @Override
        protected List<CommentInfo> doInBackground(Long... longs) {
            List<CommentInfo> list = null;
            try{
                list = getAllComments(longs[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<CommentInfo> comments) {
            super.onPostExecute(comments);
            if (comments!=null){
                recyclerView.setAdapter(new CommentAdapter(comments));
                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
            }
        }
    }

    /**
     * 获取帖子评论信息
     * @param pid
     * @return
     * @throws Exception
     */
    List<CommentInfo> getAllComments(Long pid) throws Exception{
        return getAllCommentOfPost(pid);
    }

    /**
     * 获取帖子信息
     * @param pid
     * @return
     * @throws Exception
     */
    PostInfo getPostInfo(Long pid) throws Exception{
        Post post = PostHelper.getPost(pid);
        User user = UserHelper.getUser(post.getUId());
        PostInfo postInfo = new PostInfo(post,user);
        return postInfo;
    }
}
