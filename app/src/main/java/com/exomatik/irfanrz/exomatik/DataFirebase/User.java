package com.exomatik.irfanrz.exomatik.DataFirebase;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

@IgnoreExtraProperties
public class User {
    public String uid;
    public String email;
    public String nama;
    public String firebaseToken;
    public String typeUser;

    public User() {
    }

    public User(String uid, String email, String firebaseToken, String typeUser, String nama) {
        this.uid = uid;
        this.email = email;
        this.firebaseToken = firebaseToken;
        this.typeUser = typeUser;
        this.nama = nama;

    }
}
