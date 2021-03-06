package com.exomatik.irfanrz.exomatik.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.exomatik.irfanrz.exomatik.Activity.GaleriKegiatan;
import com.exomatik.irfanrz.exomatik.Activity.TambahKegiatan;
import com.exomatik.irfanrz.exomatik.DataModel.ModelKegiatan;
import com.exomatik.irfanrz.exomatik.R;
import com.exomatik.irfanrz.exomatik.RecyclerView.ItemClickSupport;
import com.exomatik.irfanrz.exomatik.RecyclerView.RecyclerKegiatan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import io.supercharge.shimmerlayout.ShimmerLayout;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentKegiatan extends Fragment implements ItemClickSupport.OnItemClickListener {
    private View view;
    private ArrayList<ModelKegiatan> dataKegiatan;
    private RecyclerView rcKegiatan;
    private Button btnTambahKegiatan;
    private ShimmerLayout shimmerLoad;

    public fragmentKegiatan() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        rcKegiatan = (RecyclerView) view.findViewById(R.id.rc_kegiatan);
        btnTambahKegiatan = (Button) view.findViewById(R.id.btn_tambah_kegiatan);
        shimmerLoad = (ShimmerLayout) view.findViewById(R.id.shimmer_text);
        shimmerLoad.startShimmerAnimation();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            btnTambahKegiatan.setVisibility(View.VISIBLE);
        } else {
            btnTambahKegiatan.setVisibility(View.GONE);
        }

        getData();

        ItemClickSupport.addTo(rcKegiatan)
                .setOnItemClickListener(this);

        btnTambahKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TambahKegiatan.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void getData() {

        FirebaseDatabase.getInstance().getReference().child("Kegiatan").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            }

            public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
                dataKegiatan = new ArrayList<ModelKegiatan>();
                Iterator localIterator1 = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator1.hasNext()) {
                    DataSnapshot localDataSnapshot = (DataSnapshot) localIterator1.next();
                    ModelKegiatan data = (ModelKegiatan) ((DataSnapshot) localDataSnapshot).getValue(ModelKegiatan.class);
                    dataKegiatan.add(new ModelKegiatan(data.namaKegiatan, data.rincianKegiatan, data.lokasiKegiatan, data.tanggalKegiatan
                            , data.idKegiatan, data.gambarKegiatan
                    ));
                }
                shimmerLoad.setVisibility(View.GONE);
                shimmerLoad.stopShimmerAnimation();
                RecyclerKegiatan adapterAgenda = new RecyclerKegiatan(dataKegiatan);
                RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rcKegiatan.setLayoutManager(layoutAgenda);
                rcKegiatan.setNestedScrollingEnabled(false);
                rcKegiatan.setAdapter(adapterAgenda);

            }
        });
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        GaleriKegiatan.dataKegiatan = new ModelKegiatan(dataKegiatan.get(position).namaKegiatan, dataKegiatan.get(position).rincianKegiatan
                , dataKegiatan.get(position).lokasiKegiatan, dataKegiatan.get(position).tanggalKegiatan, dataKegiatan.get(position).idKegiatan
                , dataKegiatan.get(position).gambarKegiatan
        );
        Intent intent = new Intent(getActivity(), GaleriKegiatan.class);
        startActivity(intent);
    }
}
