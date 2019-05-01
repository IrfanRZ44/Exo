package com.exomatik.irfanrz.exomatik.CustomDialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.Activity.TambahAnggota;
import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class CustomDialogAnggota extends DialogFragment {
    TextView textNama, textAlamat, textKontak, textSkill, textStatus;
    Button customDialog_Dismiss, buttonEdit, buttonDelete;
    ImageView imageView;
    public static ModelDataAngkatan dataOrang;


    public static CustomDialogAnggota newInstance() {
        return new CustomDialogAnggota();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_anggota, container, false);

        textNama = (TextView) dialogView.findViewById(R.id.text_nama);
        customDialog_Dismiss = (Button) dialogView.findViewById(R.id.dialog_dismiss);
        imageView = (ImageView) dialogView.findViewById(R.id.img_dialog_fragment);
        textAlamat = (TextView) dialogView.findViewById(R.id.text_alamat);
        textKontak = (TextView) dialogView.findViewById(R.id.text_kontak);
        textSkill = (TextView) dialogView.findViewById(R.id.text_skill);
        textStatus = (TextView) dialogView.findViewById(R.id.text_status);
        buttonEdit = (Button) dialogView.findViewById(R.id.dialog_edit);
        buttonDelete = (Button) dialogView.findViewById(R.id.dialog_delete);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            buttonEdit.setVisibility(View.VISIBLE);
            buttonDelete.setVisibility(View.VISIBLE);
        }
        else {
            buttonEdit.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        }

        textNama.setText(dataOrang.nama);
        textKontak.setText(dataOrang.hp);
        textAlamat.setText(dataOrang.alamat);
        textSkill.setText(dataOrang.skill);
        textStatus.setText("Anggota " + dataOrang.status);

        Uri uri = Uri.parse(dataOrang.photo);
        Picasso.with(getContext()).load(uri).into(imageView);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick(v);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahAnggota.dataEdit = new ModelDataAngkatan(dataOrang.nama, dataOrang.alamat, dataOrang.hp,
                                dataOrang.idAnggota, dataOrang.tahunAngkatan, dataOrang.photo, dataOrang.skill,
                                dataOrang.status);
                TambahAnggota.adminType = 2;
                startActivity(new Intent(getActivity(), TambahAnggota.class));
                getActivity().finish();
            }
        });

        customDialog_Dismiss.setOnClickListener(customDialog_DismissOnClickListener);

        return dialogView;
    }

    public void onDeleteClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Deleting", Toast.LENGTH_SHORT).show();
                DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("anggota")
                        .child("angkatan" + dataOrang.tahunAngkatan).child(dataOrang.nama);
                db_node.removeValue();
                StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(dataOrang.photo);
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(getContext(), "Succes Deleting Photo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Toast.makeText(getContext(), "Failed Deleting Photo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }


    private Button.OnClickListener customDialog_DismissOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            dismiss();
        }
    };

}
