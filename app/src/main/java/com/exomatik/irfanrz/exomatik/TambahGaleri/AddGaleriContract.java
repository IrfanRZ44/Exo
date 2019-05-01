package com.exomatik.irfanrz.exomatik.TambahGaleri;

import android.content.Context;

import com.exomatik.irfanrz.exomatik.DataModel.ModelImage;
import com.exomatik.irfanrz.exomatik.DataModel.ModelKegiatan;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public interface AddGaleriContract {
    interface View {
        void onAddGaleriSuccess(String message);

        void onAddGaleriFailure(String message);
    }

    interface Presenter {
        void addGaleri(Context context, ModelImage modelImage);
    }

    interface Interactor {
        void addGaleriToDatabase(Context context, ModelImage modelImage);
    }

    interface OnGaleriDatabaseListener {
        void onSuccess(String message);

        void onFailure(String message);
    }
}
