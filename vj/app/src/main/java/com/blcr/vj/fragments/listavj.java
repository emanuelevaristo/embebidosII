package com.blcr.vj.fragments;

import android.app.Fragment;
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
import com.blcr.vj.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErickAlejandro on 24/10/2015.
 */
public class listavj  extends Fragment {

    ListView lstVjs;




    public static final String TAG = "listavj";

    public static listavj newInstance(Bundle arguments)
    {
        listavj listavjs = new listavj();
        if(arguments != null)
        {
            listavjs.setArguments(arguments);
        }
        return listavjs;
    }

    public listavj() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fgg_listavj, container, false);

        lstVjs = (ListView) rootView.findViewById(R.id.lstVjs);

        Bundle bundle = getArguments();
        int un = bundle.getInt("id");

        //new vjNet(getActivity()).execute("get");

        initCustomListView();

        return rootView;
    }

    private void initCustomListView() {

        List<VJs> vjs = new VJDataSource(getActivity()).getAllVJs();

        vjAdapter ps = new vjAdapter(vjs);
        lstVjs.setAdapter(ps);
    }

}
