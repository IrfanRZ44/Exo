package com.exomatik.irfanrz.exomatik.TambahAnggota;

import android.content.Context;

import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddAnggotaContract {
    interface View {
        void onAddAnggotaSuccess(String message);

        void onAddAnggotaFailure(String message);
    }

    interface Presenter {
        void addAnggota(Context context, ModelDataAngkatan modelDataAngkatan);
    }

    interface Interactor {
        void addAnggotaToDatabase(Context context, ModelDataAngkatan modelDataAngkatan);
    }

    interface OnAnggotaDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
