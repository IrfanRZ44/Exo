package com.exomatik.irfanrz.exomatik.AuthenticationUser.register;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddUserContract {
    interface View {
        void onAddUserSuccess(String message);

        void onAddUserFailure(String message);
    }

    interface Presenter {
        void addUser(Context context, FirebaseUser firebaseUser, String nama);
    }

    interface Interactor {
        void addUserToDatabase(Context context, FirebaseUser firebaseUser, String nama);
    }

    interface OnUserDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
