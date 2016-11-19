package com.blcr.vj.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.blcr.vj.R;
import com.blcr.vj.adapters.vjAdapter;
import com.blcr.vj.data.VJDataSource;
import com.blcr.vj.model.VJs;
//import com.blcr.vj.networking.vjNet;

import java.util.List;

/**
 * Created by ErickAlejandro on 08/11/2015.
 */
public class misVJS extends Fragment {
    ListView lstVjs;

    public misVJS() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgg_listavj, container, false);

        lstVjs = (ListView) rootView.findViewById(R.id.lstVjs);

        //new vjNet(getActivity()).execute("get");

        initCustomListView();

        //updatevjsListView();



        return rootView;
    }

    private void initCustomListView() {

        SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int idUsua = prefs.getInt("id", 0);

        List<VJs> vjs = new VJDataSource(getActivity()).getAllVJ(idUsua);

        vjAdapter ps = new vjAdapter(vjs);
        lstVjs.setAdapter(ps);
    }

    public void updatevjsListView() {
        SharedPreferences prefs = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        int idUsua = prefs.getInt("id", 0);

        List<VJs> juegos = new VJDataSource(getActivity()).getAllVJ(idUsua);

        ArrayAdapter<VJs> adapter = new ArrayAdapter<VJs>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                juegos);
        lstVjs.setAdapter(adapter);

    }

}
