package com.example.easylearnhome.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnhome.R;
import com.example.easylearnhome.entities.User;
import com.example.easylearnhome.utils.UheadUtils;
import com.example.easylearnhome.utils.UserHelper;

import java.text.SimpleDateFormat;

public class UserInfoActivity extends AppCompatActivity {

    ImageView info_user_avater;
    TextView info_user_name;
    TextView info_user_email;
    TextView info_user_sex;
    TextView info_user_register_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        info_user_avater = findViewById(R.id.info_user_avater);
        info_user_name = findViewById(R.id.info_user_name);
        info_user_email = findViewById(R.id.info_user_email);
        info_user_sex = findViewById(R.id.info_user_sex);
        info_user_register_name = findViewById(R.id.info_user_register_time);

        //开启异步任务
        Long uid = getIntent().getLongExtra("uid",1L);
        new getUserInfoAsyncTask().execute(uid);
    }

    class getUserInfoAsyncTask extends AsyncTask<Long,Void, User>{

        @Override
        protected User doInBackground(Long... longs) {
            User user = null;
            try{
                user = UserHelper.getUser(longs[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            info_user_avater.setBackground(getResources().getDrawable(UheadUtils.getUhead_id(user.getUHead())));
            info_user_name.setText("用户名："+user.getUName());
            info_user_email.setText("Email："+user.getUEmail());
            info_user_sex.setText("性别："+ (user.getUSex()==1?"男":(user.getUSex()==0?"女":"保密")));
            info_user_register_name.setText("注册时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm").format(user.getRegister_time()));
        }
    }
}
