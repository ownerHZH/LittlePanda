package com.jerry.littlepanda.mytool;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.domain.MeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyToolFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyToolFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyToolFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private List<MeEntity> items=new ArrayList<MeEntity>();
    private MeRecycleViewAdapter adapter;
    private static final int MAX = 9;

    //数据的Title
    public static final String DEVICE_INFO = "设备信息";
    public static final String JSON_INFO = "Json处理";
    public static final String RED_PACKAGE="抢红包";


    public MyToolFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyToolFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyToolFragment newInstance(String param1, String param2) {
        MyToolFragment fragment = new MyToolFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_tool, container, false);

        //xRefreshView = (XRefreshView) view.findViewById(R.id.xrefreshview);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_list);
        adapter=new MeRecycleViewAdapter(items,getActivity());
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManage = new GridLayoutManager(getActivity(), 3);
        /*
        layoutManage.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (items.get(position).getTitle().length()>MAX)
                    return 2;
                return 1;
            }
        });
        */
        recyclerView.setLayoutManager(layoutManage);

        items.add(new MeEntity(DEVICE_INFO,R.drawable.me_device_info));
        items.add(new MeEntity(JSON_INFO,R.drawable.json));
        items.add(new MeEntity(RED_PACKAGE,R.drawable.redpackage));
        //items.add(new MeEntity("设备信息1",R.drawable.pandalogo));
        //items.add(new MeEntity("设备信息1",R.drawable.pandalogo));
        //items.add(new MeEntity("设备信息1",R.drawable.pandalogo));
        adapter.notifyDataSetChanged();

        return view;
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
