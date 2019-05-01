package com.exomatik.irfanrz.exomatik.AuthenticationUser.register;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface RegisterContract {
    interface View {
        void onRegistrationSuccess(FirebaseUser firebaseUser, String nama);

        void onRegistrationFailure(String message);
    }

    interface Presenter {
        void register(Activity activity, String email, String password, String nama);
    }

    interface Interactor {
        void performFirebaseRegistration(Activity activity, String email, String password, String nama);
    }

    interface OnRegistrationListener {
        void onSuccess(FirebaseUser firebaseUser, String nama);

        void onFailure(String message);
    }
}
