package com.example.easylearnhome.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.R;
import com.example.easylearnhome.adapters.ReplyAdapter;
import com.example.easylearnhome.entities.Comment;
import com.example.easylearnhome.entities.CommentInfo;
import com.example.easylearnhome.entities.ReplyInfo;
import com.example.easylearnhome.entities.User;
import com.example.easylearnhome.utils.CommentHelper;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.LikeHelper;
import com.example.easylearnhome.utils.ReplyHelper;
import com.example.easylearnhome.utils.UheadUtils;
import com.example.easylearnhome.utils.UserHelper;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommentDetailActivity extends AppCompatActivity {

    CommentDetailActivity the;

    List<ReplyInfo> replyInfoList;
    CommentInfo commentInfo;

    ImageView comment_avater_detail;
    TextView comment_username_detail;
    TextView comment_time_detail;
    TextView comment_content_detail;
    ImageView imageView_comment_like_detail;
    TextView comment_like_num_detail;
    ImageView imageView_comment_reply_detail;
    TextView comment_replies_num_Detail;
    RecyclerView replies_to_comment_recyclerView;
    TextView reply_new_content;
    Button reply_new_button;

    static Long cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        the = this;
        Intent intent = getIntent();
        cid = intent.getLongExtra("cid",1L);
        setContentView(R.layout.activity_comment_detail);
        comment_avater_detail = findViewById(R.id.comment_avater_detail);
        comment_username_detail = findViewById(R.id.comment_username_detail);
        comment_time_detail = findViewById(R.id.comment_time_detail);
        comment_content_detail = findViewById(R.id.comment_content_detail);
        imageView_comment_like_detail = findViewById(R.id.imageView_comment_like_detail);
        comment_like_num_detail = findViewById(R.id.comment_like_num_detail);
        imageView_comment_reply_detail = findViewById(R.id.imageView_comment_reply_detail);
        comment_replies_num_Detail = findViewById(R.id.comment_replies_num_Detail);
        replies_to_comment_recyclerView = findViewById(R.id.replies_to_comment_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        replies_to_comment_recyclerView.setLayoutManager(layoutManager);
        reply_new_content = findViewById(R.id.reply_new_content);
        reply_new_button = findViewById(R.id.reply_new_button);

        initEvents();

        new GetCommentInfoAsyncTask().execute(cid);
        new GetAllReplieAsyncTask().execute(cid);
    }
    //初始化监听器
    void initEvents(){
        //点赞与取消点赞
        imageView_comment_like_detail.setOnClickListener(view -> {
            if (imageView_comment_like_detail.getBackground().getConstantState().equals(imageView_comment_like_detail.getResources().getDrawable(R.drawable.ic_icon_likegood_fill).getConstantState())){
                new LikeCommentAsyncTask().execute(commentInfo.getUID(),cid);
                imageView_comment_like_detail.setBackground(the.getResources().getDrawable(R.drawable.ic_icon_likegood_selected));
                if (commentInfo!=null)comment_like_num_detail.setText(String.format("%d",commentInfo.getCLike_num()+1));
            } else {
                new DeleteLikeCommentAsyncTask().execute(commentInfo.getUID(),cid);
                imageView_comment_like_detail.setBackground(the.getResources().getDrawable(R.drawable.ic_icon_likegood_fill));
                if (commentInfo!=null)comment_replies_num_Detail.setText(String.format("%d",commentInfo.getC_reply_num()));
            }
        });

        reply_new_button.setOnClickListener(view -> {
            String content = reply_new_content.getText().toString();
            if (content==null||content.equals("")){
                Toast.makeText(getApplicationContext(),"请输入内容！",Toast.LENGTH_SHORT).show();
            } else {
                Long uid = DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase());
                new newReplyAsyncTask().execute(commentInfo.getCID().toString(),uid.toString(),content);
                reply_new_content.setText("");
                new GetCommentInfoAsyncTask().execute(cid);
                new GetAllReplieAsyncTask().execute(cid);
            }
        });
    }

    //发表新回复
    class newReplyAsyncTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {
            try{
                Long cid = Long.parseLong(strings[0]);
                Long uid = Long.parseLong(strings[1]);
                String content = strings[2];
                ReplyHelper.newReply_to_Comment(cid,uid,content);
            } catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
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


    //获取评论信息
    class GetCommentInfoAsyncTask extends AsyncTask<Long,Void, CommentInfo>{
        @Override
        protected CommentInfo doInBackground(Long... longs) {
            try{
                CommentInfo info = getCommentInfo(longs[0]);
                return info;
            } catch (Exception e){
            }
            return null;
        }
        @Override
        protected void onPostExecute(CommentInfo commentInfo_) {
            super.onPostExecute(commentInfo);
            commentInfo = commentInfo_;
            if (commentInfo==null)return;
            comment_avater_detail.setBackground(getResources().getDrawable(UheadUtils.getUhead_id(commentInfo.getUHead())));
            comment_username_detail.setText(commentInfo.getUName());
            comment_content_detail.setText(commentInfo.getCContent());
            comment_like_num_detail.setText(String.format("%d",commentInfo.getCLike_num()));
            comment_replies_num_Detail.setText(String.format("%d",commentInfo.getC_reply_num()));
            comment_time_detail.setText("发表于"+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(commentInfo.getComment_time()));
        }
    }

    //获取所有回复信息
    class GetAllReplieAsyncTask extends AsyncTask<Long,Void, List<ReplyInfo>>{
        @Override
        protected List<ReplyInfo> doInBackground(Long... longs) {
            List<ReplyInfo> list;
            try{
                list = ReplyHelper.getAllRepliesOfComment(longs[0]);
                return list;
            } catch (Exception e){
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<ReplyInfo> replyInfos) {
            super.onPostExecute(replyInfos);
            replyInfoList = replyInfos;
            if (replyInfos!=null){
                replies_to_comment_recyclerView.setAdapter(new ReplyAdapter(replyInfos));
                replies_to_comment_recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
            }
        }
    }

    //获取评论信息
    CommentInfo getCommentInfo(Long cid) throws Exception{
        Comment comment = CommentHelper.getComment(cid);
        User user = UserHelper.getUser(comment.getUID());
        CommentInfo info = new CommentInfo(comment,user);
        return info;
    }
}
