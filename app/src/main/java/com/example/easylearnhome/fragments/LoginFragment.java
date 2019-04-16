package com.example.easylearnhome.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnhome.MainActivity;
import com.example.easylearnhome.R;
import com.example.easylearnhome.entities.User;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.UserHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EditText login_username_or_email = view.findViewById(R.id.login_username_or_email);//登录用户名/邮箱
        EditText login_password = view.findViewById(R.id.login_password);//登录密码
        Button sign_in_button = view.findViewById(R.id.sign_in_button);//登录按钮
        TextView go_to_register = view.findViewById(R.id.go_to_register);
        sign_in_button.setOnClickListener((view1 -> {
            String username_or_email = login_username_or_email.getText().toString();
            String password = login_password.getText().toString();
            if (username_or_email==""||username_or_email.equals("")||password==""||password.equals("")){
                Toast.makeText(getActivity().getApplicationContext(),"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
            }else {
                new UserLoginAsyncTask().execute(username_or_email,password);
            }

        }));
        go_to_register.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_register_view,new RegisterFragment()).commit();
        });
        return view;
    }

    class UserLoginAsyncTask extends AsyncTask<String,Void, User>{

        @Override
        protected User doInBackground(String... strings) {
            User user = null;
            try {
                user = UserHelper.UserLogin(strings[0],strings[1]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user==null){
                Toast.makeText(getActivity().getApplicationContext(),"请检查您的输入是否有误！",Toast.LENGTH_SHORT).show();
            } else {
                //登录成功，保存获取到的用户信息
                SQLiteDatabase sqLiteDatabase = new DatabaseHelper(getActivity()).getWritableDatabase();
                Log.d("database","登录成功！保存用户信息到数据库...");
                sqLiteDatabase.delete("user",null,null);//先删除原有的
                Log.d("database","已经删除原有信息");
                ContentValues contentValues = new ContentValues();
                contentValues.put("uid",user.getUId().toString());
                contentValues.put("uname",user.getUName());
                contentValues.put("upassword",user.getUPassword());
                contentValues.put("uemail",user.getUEmail());
                contentValues.put("uhead",user.getUHead());
                contentValues.put("usex",user.getUSex());
                contentValues.put("urole",user.getURole());
                contentValues.put("ustate",user.getUState());
                contentValues.put("register_time",user.getRegister_time().toString());
                sqLiteDatabase.insert("user",null,contentValues);
                Log.d("database","已经执行完信息插入");
                sqLiteDatabase.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();//结束登录的activity
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
