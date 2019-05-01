package com.exomatik.irfanrz.exomatik.TambahAnggota;

import android.content.Context;
import android.support.annotation.NonNull;

import com.exomatik.irfanrz.exomatik.AuthenticationUser.register.AddUserContract;
import com.exomatik.irfanrz.exomatik.DataFirebase.SharedPrefUtil;
import com.exomatik.irfanrz.exomatik.DataFirebase.User;
import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddAnggotaInteractor implements AddAnggotaContract.Interactor  {
    private AddAnggotaContract.OnAnggotaDatabaseListener mOnAnggotaDatabaseListener;
    private Context ctx;

    public AddAnggotaInteractor(AddAnggotaContract.OnAnggotaDatabaseListener onAnggotaDatabaseListener) {
        this.mOnAnggotaDatabaseListener = onAnggotaDatabaseListener;
    }

    @Override
    public void addAnggotaToDatabase(final Context context, ModelDataAngkatan modelDataAngkatan) {
        String angkatan = "angkatan"+modelDataAngkatan.tahunAngkatan;
        ctx = context;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ModelDataAngkatan data = new ModelDataAngkatan(modelDataAngkatan.nama, modelDataAngkatan.alamat
                , modelDataAngkatan.hp, modelDataAngkatan.idAnggota, modelDataAngkatan.tahunAngkatan
                , modelDataAngkatan.photo, modelDataAngkatan.skill, modelDataAngkatan.status
        );
        database.child("anggota")
                .child(angkatan)
                .child(data.nama)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnAnggotaDatabaseListener.onSuccess("Succes");

                        } else {
                            mOnAnggotaDatabaseListener.onFailure("Unable to add");
                        }
                    }
                });
    }
}
