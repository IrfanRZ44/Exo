package com.exomatik.irfanrz.exomatik.DataModel;

import android.net.Uri;

/**
 * Created by IrfanRZ on 17/11/2018.
 */

public class ModelDataAngkatan {
    public String nama;
    public String alamat;
    public String hp;
    public String skill;
    public int idAnggota;
    public int tahunAngkatan;
    public String photo;
    public String status;

    public ModelDataAngkatan() {
    }

    public ModelDataAngkatan(String nama, String alamat, String hp, int idAnggota, int tahunAngkatan, String photo, String skill, String status) {
        this.nama = nama;
        this.alamat = alamat;
        this.hp = hp;
        this.idAnggota = idAnggota;
        this.tahunAngkatan = tahunAngkatan;
        this.photo = photo;
        this.skill = skill;
        this.status = status;
    }
}
