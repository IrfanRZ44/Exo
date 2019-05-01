package com.exomatik.irfanrz.exomatik.TambahAnggota;

import android.content.Context;

import com.exomatik.irfanrz.exomatik.AuthenticationUser.register.AddUserContract;
import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddAnggotaPresenter implements AddAnggotaContract.Presenter, AddAnggotaContract.OnAnggotaDatabaseListener{
    private AddAnggotaContract.View mView;
    private AddAnggotaInteractor mAddAnggotaInteractor;

    public AddAnggotaPresenter(AddAnggotaContract.View view) {
        this.mView = view;
        mAddAnggotaInteractor = new AddAnggotaInteractor(this);
    }

    @Override
    public void addAnggota(Context context, ModelDataAngkatan modelDataAngkatan) {
        mAddAnggotaInteractor.addAnggotaToDatabase(context, modelDataAngkatan);
    }

    @Override
    public void onSuccess(String message) {
        mView.onAddAnggotaSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onAddAnggotaFailure(message);
    }

}
