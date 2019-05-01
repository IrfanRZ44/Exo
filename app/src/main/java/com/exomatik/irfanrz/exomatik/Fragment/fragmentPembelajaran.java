package com.exomatik.irfanrz.exomatik.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exomatik.irfanrz.exomatik.R;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentPembelajaran extends Fragment {
    private View view;

    public fragmentPembelajaran() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pembelajaran, container, false);


        return view;
    }

}
