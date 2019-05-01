package com.exomatik.irfanrz.exomatik.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.Activity.MainActivity;
import com.exomatik.irfanrz.exomatik.Activity.MapsActivity;
import com.exomatik.irfanrz.exomatik.AuthenticationUser.login.loginUser;
import com.exomatik.irfanrz.exomatik.AuthenticationUser.register.registerUser;
import com.exomatik.irfanrz.exomatik.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentPengaturan extends Fragment {
    private View view;
    private RelativeLayout rlAuth, rlTerms, rlLokasi;
    private Button btnLogin, btnLogout, btnRegister;

    public fragmentPengaturan() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        rlAuth = (RelativeLayout) view.findViewById(R.id.relativeAuth);
        rlTerms = (RelativeLayout) view.findViewById(R.id.about_terms);
        rlLokasi = (RelativeLayout) view.findViewById(R.id.via_lokasi);
        btnLogout = (Button) view.findViewById(R.id.menuLogout);
        btnLogin = (Button) view.findViewById(R.id.menuLogin);
        btnRegister = (Button) view.findViewById(R.id.menuRegister);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            btnLogout.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
        }
        else {
            btnLogout.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), registerUser.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), loginUser.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        rlTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mohon maaf, menu ini belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        rlLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapsActivity.class));
                getActivity().finish();
            }
        });



        return view;
    }

}
