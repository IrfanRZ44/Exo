package com.exomatik.irfanrz.exomatik.AuthenticationUser.register;

import android.content.Context;
import android.support.annotation.NonNull;

import com.exomatik.irfanrz.exomatik.DataFirebase.SharedPrefUtil;
import com.exomatik.irfanrz.exomatik.DataFirebase.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by IrfanRZ on 06/09/2018.
 */

public class AddUserInteractor implements AddUserContract.Interactor  {
    private AddUserContract.OnUserDatabaseListener mOnUserDatabaseListener;
    private Context ctx;

    public AddUserInteractor(AddUserContract.OnUserDatabaseListener onUserDatabaseListener) {
        this.mOnUserDatabaseListener = onUserDatabaseListener;
    }

    @Override
    public void addUserToDatabase(final Context context, FirebaseUser firebaseUser, String nama) {
        ctx = context;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        User user = new User(firebaseUser.getUid(),
                firebaseUser.getEmail(),
                new SharedPrefUtil(context).getString("firebaseToken"), "user", nama);
        database.child("users")
                .child(firebaseUser.getUid())
                .setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cekTypeUser();

                        } else {
                            mOnUserDatabaseListener.onFailure("Unable to add");
                        }
                    }
                });
    }

    private void cekTypeUser() {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                while (dataSnapshots.hasNext()) {
                    DataSnapshot dataSnapshotChild = dataSnapshots.next();
                    User user = null;
                    user = dataSnapshotChild.getValue(User.class);

                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().toString().equals(user.uid)) {
                        new SharedPrefUtil(ctx).saveString("type", user.typeUser.toString());
                        mOnUserDatabaseListener.onSuccess("Succes");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
