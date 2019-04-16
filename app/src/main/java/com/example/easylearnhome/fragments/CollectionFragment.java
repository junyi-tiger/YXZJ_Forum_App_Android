package com.example.easylearnhome.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easylearnhome.R;
import com.example.easylearnhome.adapters.PostAdapter;
import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.PostInfo;
import com.example.easylearnhome.utils.DataBaseUtil;
import com.example.easylearnhome.utils.DatabaseHelper;
import com.example.easylearnhome.utils.PostHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    public CollectionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(String param1, String param2) {
        CollectionFragment fragment = new CollectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab2, container, false);
        recyclerView = view.findViewById(R.id.tab2_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Long id = DataBaseUtil.getUid(new DatabaseHelper(getContext()).getReadableDatabase());
        new PostCollectionAsyncTask().execute(id);
    }

    class PostCollectionAsyncTask extends AsyncTask<Long,Void, List<PostInfo>>{

        @Override
        protected List<PostInfo> doInBackground(Long... longs) {
            List<PostInfo> infos = null;
            try{
                infos = getAllCollection(longs[0]);
            } catch (Exception e){
                e.printStackTrace();
            }
            return infos;
        }

        @Override
        protected void onPostExecute(List<PostInfo> infos) {
            super.onPostExecute(infos);
            if (infos!=null){
                recyclerView.setAdapter(new PostAdapter(infos));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
            }
        }
    }

    List<PostInfo> getAllCollection(Long id) throws Exception{
        return PostHelper.getAllCollection(id);
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
