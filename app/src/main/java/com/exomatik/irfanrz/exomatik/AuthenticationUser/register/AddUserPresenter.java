package com.exomatik.irfanrz.exomatik.AuthenticationUser.register;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddUserPresenter implements AddUserContract.Presenter, AddUserContract.OnUserDatabaseListener{
    private AddUserContract.View mView;
    private AddUserInteractor mAddUserInteractor;

    public AddUserPresenter(AddUserContract.View view) {
        this.mView = view;
        mAddUserInteractor = new AddUserInteractor(this);
    }

    @Override
    public void addUser(Context context, FirebaseUser firebaseUser, String nama) {
        mAddUserInteractor.addUserToDatabase(context, firebaseUser, nama);
    }

    @Override
    public void onSuccess(String message) {
        mView.onAddUserSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mView.onAddUserFailure(message);
    }

}
