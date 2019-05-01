package com.exomatik.irfanrz.exomatik.TambahGaleri;

import android.content.Context;
import android.support.annotation.NonNull;

import com.exomatik.irfanrz.exomatik.DataModel.ModelImage;
import com.exomatik.irfanrz.exomatik.DataModel.ModelKegiatan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddGaleriInteractor implements AddGaleriContract.Interactor  {
    private AddGaleriContract.OnGaleriDatabaseListener mOnGaleriDatabaseListener;
    private Context ctx;

    public AddGaleriInteractor(AddGaleriContract.OnGaleriDatabaseListener onGaleriDatabaseListener) {
        this.mOnGaleriDatabaseListener = onGaleriDatabaseListener;
    }

    @Override
    public void addGaleriToDatabase(final Context context, ModelImage modelGaleri) {
        ctx = context;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ModelImage data = new ModelImage(modelGaleri.image, modelGaleri.nama, modelGaleri.idKegiatan
                , modelGaleri.namaKegiatan
        );
        database.child("Galeri")
                .child(data.namaKegiatan)
                .child(data.nama)
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mOnGaleriDatabaseListener.onSuccess("Succes");
                        } else {
                            mOnGaleriDatabaseListener.onFailure("Unable to add");
                        }
                    }
                });
    }
}
