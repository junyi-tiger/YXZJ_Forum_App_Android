package com.example.easylearnhome.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        EditText register_username = view.findViewById(R.id.register_username);//注册用户名
        EditText register_email = view.findViewById(R.id.register_email);
        EditText register_password = view.findViewById(R.id.register_password);
        EditText register_confirm_password = view.findViewById(R.id.register_confirm_password);
        RadioGroup register_sex = view.findViewById(R.id.register_sex);
        Button sign_up_button = view.findViewById(R.id.sign_up_button);
        TextView go_to_login = view.findViewById(R.id.go_to_login);
        sign_up_button.setOnClickListener(view1 -> {
            String username = register_username.getText().toString();
            String email = register_email.getText().toString();
            String password = register_password.getText().toString();
            String confirm_password = register_confirm_password.getText().toString();
            int sex=2;
            switch (register_sex.getCheckedRadioButtonId()){
                case R.id.register_sex_male:
                    sex = 1;
                    break;
                case R.id.register_sex_female:
                    sex = 0;
                    break;
                case R.id.register_sex_secrecy:
                    sex = 2;
                    break;
            }
//            Toast.makeText(getActivity().getApplicationContext(),"获取到用户名："+username+",邮箱："+email,Toast.LENGTH_SHORT).show();
            if (username.equals("")||password.equals("")||email.equals("")||confirm_password.equals("")){
                Toast.makeText(getActivity().getApplicationContext(),"值不能为空！",Toast.LENGTH_SHORT).show();
            } else if (password.compareTo(confirm_password)!=0){
                Toast.makeText(getActivity().getApplicationContext(),"两次输入的密码不一致，请重新输入！",Toast.LENGTH_SHORT).show();
            } else {
                new UserRegisterAsyncTask().execute(username,email,password,String.valueOf(sex));
            }
        });
        go_to_login.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.login_register_view,new LoginFragment()).commit();
        });
        return view;
    }

    class UserRegisterAsyncTask extends AsyncTask<String,Void, User>{

        @Override
        protected User doInBackground(String... strings) {
            User user = null;
            try {
                user = UserHelper.UserRegister(strings[0],strings[1],strings[2],Integer.parseInt(strings[3]));
            } catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user==null){
                Toast.makeText(getActivity().getApplicationContext(),"注册发生异常，可能相同的用户名或邮箱已被注册过，请修改后重试！",Toast.LENGTH_SHORT).show();
            } else {
                //注册成功，保存获取到的用户信息
                SQLiteDatabase sqLiteDatabase = new DatabaseHelper(getActivity()).getWritableDatabase();
                sqLiteDatabase.delete("user",null,null);//先删除原有的
                ContentValues contentValues = new ContentValues();
                contentValues.put("uid",user.getUId());
                contentValues.put("uname",user.getUName());
                contentValues.put("upassword",user.getUPassword());
                contentValues.put("uemail",user.getUEmail());
                contentValues.put("uhead",user.getUHead());
                contentValues.put("usex",user.getUSex());
                contentValues.put("urole",user.getURole());
                contentValues.put("ustate",user.getUState());
                contentValues.put("register_time",user.getRegister_time().toString());
                sqLiteDatabase.insert("user",null,contentValues);
                sqLiteDatabase.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
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
