package com.exomatik.irfanrz.exomatik.TambahKegiatan;

import android.content.Context;

import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.DataModel.ModelKegiatan;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddKegiatanContract {
    interface View {
        void onAddKegiatanSuccess(String message);

        void onAddKegiatanFailure(String message);
    }

    interface Presenter {
        void addKegiatan(Context context, ModelKegiatan modelKegiatan);
    }

    interface Interactor {
        void addKegiatanToDatabase(Context context, ModelKegiatan modelKegiatan);
    }

    interface OnKegiatanDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
