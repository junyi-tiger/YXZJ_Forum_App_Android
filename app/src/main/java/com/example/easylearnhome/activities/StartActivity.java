package com.example.easylearnhome.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.easylearnhome.MainActivity;
import com.example.easylearnhome.R;
import com.example.easylearnhome.entities.User;
import com.example.easylearnhome.fragments.LoginFragment;
import com.example.easylearnhome.fragments.RegisterFragment;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.UserHelper;

import static com.example.easylearnhome.R.layout.activity_start;

public class StartActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{

    private StartActivity the;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_start);
        the = this;
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Log.d("database","开始查询user表中的uname，upassword");
        Cursor cursor = database.query("user",null,null,null,null,null,null);
        //是否为空
        if (cursor.moveToFirst()){
            String uname = cursor.getString(1);
            String upassword = cursor.getString(2);
            Log.d("database","查询到uname="+uname+",upassword="+upassword);
            new UserLoginAsyncTask().execute(uname,upassword);
        } else {
            Log.d("database","当前user表中没有数据");
            showViews();
        }
        cursor.close();
        database.close();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this,"onFragmentInteraction() executed!", Toast.LENGTH_LONG).show();
    }

    private void showViews(){
        //加载LoginFragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.login_register_view,new LoginFragment())
                .commit();
    }

    class UserLoginAsyncTask extends AsyncTask<String,Void, User>{

        @Override
        protected User doInBackground(String... strings) {
            User user = null;
            try{
                user = UserHelper.UserLogin(strings[0],strings[1]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            Log.d("databse","联网查询到user="+user);
            if (user == null){
                showViews();
            } else {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                Log.d("database","开始了intent，执行finish");
                the.finish();
            }
        }
    }
}
