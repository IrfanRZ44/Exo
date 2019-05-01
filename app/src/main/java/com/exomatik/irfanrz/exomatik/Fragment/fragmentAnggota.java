package com.exomatik.irfanrz.exomatik.Fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.Activity.TambahAnggota;
import com.exomatik.irfanrz.exomatik.Adapter.AdapterGridAnggota;
import com.exomatik.irfanrz.exomatik.CustomDialog.CustomDialogAnggota;
import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.R;
import com.exomatik.irfanrz.exomatik.RecyclerView.ItemClickSupport;
import com.exomatik.irfanrz.exomatik.RecyclerView.RecyclerAngkatan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;
import io.supercharge.shimmerlayout.ShimmerLayout;

/**
 * Created by IrfanRZ on 21/09/2018.
 */

public class fragmentAnggota extends Fragment implements ItemClickSupport.OnItemClickListener  {
    private View view;
    private Spinner spinnerAngkatan;
    private GridView gridAnggota;
    private RecyclerView rcAnggota;
    private ArrayList<String> listAngkatan = new ArrayList<String>();
    public static ArrayList<ModelDataAngkatan> dataAngkatan = new ArrayList<ModelDataAngkatan>();
    private RelativeLayout btnTambahAnggota, btnMenu;
    private ImageView imgTampil;
    FirebaseStorage storage;
    StorageReference storageRef;
    ArrayList<ModelDataAngkatan> data = new ArrayList<ModelDataAngkatan>();
    private ShimmerLayout shimmerLoad, shimmerLoad2;

    public fragmentAnggota() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_anggota, container, false);

        spinnerAngkatan = (Spinner) view.findViewById(R.id.spinner_angkatan);
        gridAnggota = (GridView) view.findViewById(R.id.grid_anggota);
        btnTambahAnggota = (RelativeLayout) view.findViewById(R.id.btn_tambah_anggota);
        btnMenu = (RelativeLayout) view.findViewById(R.id.btn_tampil);
        rcAnggota = (RecyclerView) view.findViewById(R.id.rc_anggota);
        imgTampil = (ImageView) view.findViewById(R.id.tampil);
        shimmerLoad = (ShimmerLayout) view.findViewById(R.id.shimmer_text);
        shimmerLoad2 = (ShimmerLayout) view.findViewById(R.id.shimmer_text2);
        shimmerLoad.startShimmerAnimation();
        shimmerLoad2.startShimmerAnimation();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            btnTambahAnggota.setVisibility(View.VISIBLE);
        } else {
            btnTambahAnggota.setVisibility(View.GONE);
        }

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        getAngkatan();
        setSpinnerAngkatan();
        getDataAngkatan(0);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridAnggota.getVisibility() == View.VISIBLE){
                    imgTampil.setImageResource(R.drawable.ic_list);
                    gridAnggota.setVisibility(View.GONE);
                    rcAnggota.setVisibility(View.VISIBLE);
                }
                else {
                    imgTampil.setImageResource(R.drawable.ic_grid);
                    gridAnggota.setVisibility(View.VISIBLE);
                    rcAnggota.setVisibility(View.GONE);
                }
            }
        });

        spinnerAngkatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rcAnggota.setVisibility(View.GONE);
                shimmerLoad.startShimmerAnimation();
                shimmerLoad.setVisibility(View.VISIBLE);
                shimmerLoad2.startShimmerAnimation();
                shimmerLoad2.setVisibility(View.VISIBLE);

                int angkatan = spinnerSeleksi(position);
                getDataAngkatan(angkatan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTambahAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahAnggota.adminType = 1;
                Intent intent = new Intent(getActivity(), TambahAnggota.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private int spinnerSeleksi(int posisi) {
        int angkat = 0;
        switch (posisi) {
            case 0:
                angkat = 12;
                break;
            case 1:
                angkat = 11;
                break;
            case 2:
                angkat = 10;
                break;
            case 3:
                angkat = 9;
                break;
            case 4:
                angkat = 8;
                break;
            case 5:
                angkat = 7;
                break;
            case 6:
                angkat = 6;
                break;
            case 7:
                angkat = 5;
                break;
            case 8:
                angkat = 4;
                break;
            case 9:
                angkat = 3;
                break;
            case 10:
                angkat = 2;
                break;
            case 11:
                angkat = 1;
                break;

        }
        return angkat;
    }

    private void setDataAngkatan(int angkatan, ArrayList<ModelDataAngkatan> dataOrang) {
        final ArrayList<ModelDataAngkatan> data = new ArrayList<ModelDataAngkatan>();
        for (int a = 0; a < dataOrang.size(); a++) {
            if (dataOrang.get(a).tahunAngkatan == angkatan) {
                data.add(new ModelDataAngkatan(dataOrang.get(a).nama, dataOrang.get(a).alamat, dataOrang.get(a).hp,
                        dataOrang.get(a).idAnggota, dataOrang.get(a).tahunAngkatan, dataOrang.get(a).photo,
                        dataOrang.get(a).skill, dataOrang.get(a).status
                ));
            }
        }

        this.data = data;

        if (imgTampil.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_list_white).getConstantState()){
            rcAnggota.setVisibility(View.VISIBLE);
        }
        else if (imgTampil.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_grid_white).getConstantState()){
            gridAnggota.setVisibility(View.VISIBLE);
        }

        shimmerLoad.setVisibility(View.GONE);
        shimmerLoad.stopShimmerAnimation();
        shimmerLoad2.setVisibility(View.GONE);
        shimmerLoad2.stopShimmerAnimation();
        gridAnggota.setAdapter(new AdapterGridAnggota(getActivity(), data));

        RecyclerAngkatan adapterAgenda = new RecyclerAngkatan(data);
        RecyclerView.LayoutManager layoutAgenda = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcAnggota.setLayoutManager(layoutAgenda);
        rcAnggota.setNestedScrollingEnabled(false);
        rcAnggota.setAdapter(adapterAgenda);

        ItemClickSupport.addTo(rcAnggota)
                .setOnItemClickListener(this);

        gridAnggota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDialogAnggota.dataOrang = new ModelDataAngkatan(data.get(position).nama, data.get(position).alamat,
                        data.get(position).hp, data.get(position).idAnggota, dataAngkatan.get(position).tahunAngkatan,
                        data.get(position).photo, data.get(position).skill, data.get(position).status
                );
                DialogFragment newFragment = CustomDialogAnggota
                        .newInstance();

                newFragment.setCancelable(false);

                newFragment.show(getActivity().getFragmentManager(), "dialog");
            }
        });
    }

    private void getDataAngkatan(final int angkatan) {
        FirebaseDatabase.getInstance().getReference().child("anggota").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onCancelled(DatabaseError paramAnonymousDatabaseError) {
            }
            public void onDataChange(DataSnapshot paramAnonymousDataSnapshot) {
                dataAngkatan = new ArrayList<ModelDataAngkatan>();
                Iterator localIterator1 = paramAnonymousDataSnapshot.getChildren().iterator();
                while (localIterator1.hasNext()) {
                    DataSnapshot localDataSnapshot = (DataSnapshot) localIterator1.next();
                    Iterator local = localDataSnapshot.getChildren().iterator();
                    while (local.hasNext()){
                        DataSnapshot dataDS = (DataSnapshot) local.next();
                        ModelDataAngkatan data = (ModelDataAngkatan) ((DataSnapshot) dataDS).getValue(ModelDataAngkatan.class);
                        if (data.tahunAngkatan == angkatan) {
                            dataAngkatan.add(new ModelDataAngkatan(data.nama, data.alamat, data.hp, data.idAnggota, data.tahunAngkatan
                                    , data.photo, data.skill, data.status
                            ));
                        }
                    }
                }
                setDataAngkatan(angkatan, dataAngkatan);
            }
        });

    }

    private void getAngkatan() {
        listAngkatan.add(getString(R.string.angkatan12));
        listAngkatan.add(getString(R.string.angkatan11));
        listAngkatan.add(getString(R.string.angkatan10));
        listAngkatan.add(getString(R.string.angkatan9));
        listAngkatan.add(getString(R.string.angkatan8));
        listAngkatan.add(getString(R.string.angkatan7));
        listAngkatan.add(getString(R.string.angkatan6));
        listAngkatan.add(getString(R.string.angkatan5));
        listAngkatan.add(getString(R.string.angkatan4));
        listAngkatan.add(getString(R.string.angkatan3));
        listAngkatan.add(getString(R.string.angkatan2));
        listAngkatan.add(getString(R.string.angkatan1));
    }

    private void setSpinnerAngkatan() {
        ArrayAdapter<String> dataAngkatan = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_text, listAngkatan);

        spinnerAngkatan.setAdapter(dataAngkatan);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        CustomDialogAnggota.dataOrang = new ModelDataAngkatan(data.get(position).nama, data.get(position).alamat,
                        data.get(position).hp, data.get(position).idAnggota, dataAngkatan.get(position).tahunAngkatan,
                        data.get(position).photo, data.get(position).skill, data.get(position).status
                );
        DialogFragment newFragment = CustomDialogAnggota
                .newInstance();

        newFragment.setCancelable(false);

        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }
}
