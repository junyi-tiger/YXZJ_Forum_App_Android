package com.example.easylearnhome.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.easylearnhome.R;
import com.example.easylearnhome.adapters.PostAdapter;
import com.example.easylearnhome.entities.PostInfo;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.PostHelper;

import java.util.List;

public class MyPostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = findViewById(R.id.my_posts_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        Long id = DataBaseUtil.getUid(new DatabaseHelper(getApplicationContext()).getReadableDatabase());

        new PostMineAsyncTask().execute(id);
    }

    class PostMineAsyncTask extends AsyncTask<Long,Void, List<PostInfo>>{

        @Override
        protected List<PostInfo> doInBackground(Long... longs) {
            List<PostInfo> list = null;
            try{
                list = getAllMinePosts(longs[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<PostInfo> postInfos) {
            super.onPostExecute(postInfos);
            recyclerView.setAdapter(new PostAdapter(postInfos));
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),1));
        }
    }

    List<PostInfo> getAllMinePosts(Long id) throws Exception{
        return PostHelper.getAllPostsOfUser(id);
    }

}
