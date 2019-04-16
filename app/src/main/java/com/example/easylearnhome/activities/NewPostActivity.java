package com.example.easylearnhome.activities;

import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easylearnhome.R;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.PostHelper;

public class NewPostActivity extends AppCompatActivity {

    NewPostActivity the;

    EditText new_post_title;
    EditText new_post_content;
    Button button_new_post;
    TextInputLayout textInputLayout_title;
    TextInputLayout textInputLayout_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        the = this;
        new_post_title = findViewById(R.id.new_post_title);
        new_post_content = findViewById(R.id.new_post_content);
        textInputLayout_title = findViewById(R.id.textInputLayout_title);
        textInputLayout_content = findViewById(R.id.textInputLayout_content);
        button_new_post = findViewById(R.id.button_new_post);

        new_post_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence==""||charSequence.length()==0){
                    textInputLayout_title.setError("标题不能为空！");
                    textInputLayout_title.setErrorEnabled(true);
                }else {
                    textInputLayout_title.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        new_post_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence==""||charSequence.length()==0){
                    textInputLayout_content.setError("内容不能为空！");
                    textInputLayout_content.setErrorEnabled(true);
                }else {
                    textInputLayout_content.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        button_new_post.setOnClickListener(view -> {
            String title = new_post_title.getText().toString();
            String content = new_post_content.getText().toString();
            if (title.equals("")||content.equals("")) {
                Toast.makeText(getApplicationContext(),"请输入完整后再发表！",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"发表新帖子！",Toast.LENGTH_SHORT).show();
                Long uid = DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase());
                new NewPostAsyncTask().execute(uid.toString(),title,content);
            }
        });
    }

    class NewPostAsyncTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            Long uid = Long.parseLong(strings[0]);
            String title = strings[1];
            String content = strings[2];
            try{
                PostHelper.newPost(uid,title,content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            the.finish();
        }
    }
}
