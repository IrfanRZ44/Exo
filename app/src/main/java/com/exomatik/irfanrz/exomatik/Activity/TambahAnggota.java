package com.exomatik.irfanrz.exomatik.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.R;
import com.exomatik.irfanrz.exomatik.TambahAnggota.AddAnggotaContract;
import com.exomatik.irfanrz.exomatik.TambahAnggota.AddAnggotaPresenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TambahAnggota extends AppCompatActivity implements AddAnggotaContract.View {
    private Button btnTambah, btnTextFoto;
    private ImageView back, imgFoto;
    private EditText etNama, etAlamat, etKontak, etSkill;
    private TextView textTitle;
    private RadioGroup radioGroup;
    private static int PICK_IMAGE = 100;
    private Uri imageUri;
    private RelativeLayout rlFoto;
    private ProgressDialog progressDialog = null;
    private ArrayList<String> listAngkatan = new ArrayList<String>();
    private Spinner spinnerAngkatan;
    private int angkatan;
    private AddAnggotaPresenter mAddAnggotaPresenter;
    FirebaseStorage storage;
    StorageReference storageRef, imageRef;
    UploadTask uploadTask;
    public static ModelDataAngkatan dataEdit;
    public static int adminType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_anggota);

        btnTambah = (Button) findViewById(R.id.tambahKegiatan);
        btnTextFoto = (Button) findViewById(R.id.btn_foto_anggota);
        back = (ImageView) findViewById(R.id.back);
        etNama = (EditText) findViewById(R.id.galeriNama);
        etAlamat = (EditText) findViewById(R.id.kegiatanLokasi);
        etKontak = (EditText) findViewById(R.id.kegiatanRincian);
        etSkill = (EditText) findViewById(R.id.anggotaSkill);
        radioGroup = (RadioGroup) findViewById(R.id.radioStatus);
        imgFoto = (ImageView) findViewById(R.id.img_foto);
        rlFoto = (RelativeLayout) findViewById(R.id.rl_foto);
        spinnerAngkatan = (Spinner) findViewById(R.id.spinner_anggota);
        textTitle = (TextView) findViewById(R.id.text_title_anggota);

        //accessing the firebase storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAddAnggotaPresenter = new AddAnggotaPresenter(this);

        imgFoto.setVisibility(View.GONE);
        rlFoto.setVisibility(View.VISIBLE);
        getAngkatan();
        angkatan = 11;
        spinnerAngkatan.setSelection(angkatan);

        if (adminType == 1) {

        } else if (adminType == 2) {
            etNama.setText(dataEdit.nama);
            etSkill.setText(dataEdit.skill);
            etKontak.setText(dataEdit.hp);
            etAlamat.setText(dataEdit.alamat);
            if (dataEdit.status.equals("aktif")) {
                radioGroup.check(R.id.rb1);
            } else if (dataEdit.status.equals("kehormatan")) {
                radioGroup.check(R.id.rb2);
            }
            angkatan = dataEdit.tahunAngkatan - 1;
            spinnerAngkatan.setSelection(angkatan);
            Uri uri = Uri.parse(dataEdit.photo);
            imageUri = Uri.parse(dataEdit.photo);

            Picasso.with(this).load(uri).into(imgFoto);
            imgFoto.setVisibility(View.GONE);
            rlFoto.setVisibility(View.VISIBLE);
            btnTextFoto.setText("Ganti Foto");
            btnTambah.setText("Edit");
            textTitle.setText("Edit Anggota");

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(dataEdit.photo);
            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adminType == 1) {
                    tambah(v);
                } else if (adminType == 2) {
                    DatabaseReference db_node = FirebaseDatabase.getInstance().getReference().child("anggota")
                            .child("angkatan" + dataEdit.tahunAngkatan).child(dataEdit.nama);
                    db_node.removeValue();
                    tambah(v);
                }
            }
        });

        rlFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahAnggota.this, MainActivity.class));
                finish();
            }
        });

        spinnerAngkatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                angkatan = spinnerSeleksi(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int spinnerSeleksi(int posisi) {
        int angkat = 0;
        switch (posisi) {
            case 0:
                angkat = 1;
                break;
            case 1:
                angkat = 2;
                break;
            case 2:
                angkat = 3;
                break;
            case 3:
                angkat = 4;
                break;
            case 4:
                angkat = 5;
                break;
            case 5:
                angkat = 6;
                break;
            case 6:
                angkat = 7;
                break;
            case 7:
                angkat = 8;
                break;
            case 8:
                angkat = 9;
                break;
            case 9:
                angkat = 10;
                break;
            case 10:
                angkat = 11;
                break;
            case 11:
                angkat = 12;
                break;

        }
        return angkat;
    }

    private void foto() {
        progressDialog = new ProgressDialog(TambahAnggota.this);
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setTitle("Proses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void tambah(View v) {
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String kontak = etKontak.getText().toString();
        String skill = etSkill.getText().toString();

        int check = radioGroup.getCheckedRadioButtonId();

        if ((nama.isEmpty()) || (alamat.isEmpty()) || (kontak.isEmpty()) || (skill.isEmpty()) ||
                (imgFoto.getVisibility() == View.GONE) || (check == -1)) {
            Snackbar.make(v, "Mohon, untuk melengkapi data dengan valid", Snackbar.LENGTH_LONG).show();
        } else {
            //creating and showing progress dialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Uploading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            RadioButton rb = (RadioButton) findViewById(check);
            upload(v, nama, alamat, kontak, skill, rb.getText().toString());
        }
    }

    private void getAngkatan() {
        listAngkatan.add(getString(R.string.angkatan1));
        listAngkatan.add(getString(R.string.angkatan2));
        listAngkatan.add(getString(R.string.angkatan3));
        listAngkatan.add(getString(R.string.angkatan4));
        listAngkatan.add(getString(R.string.angkatan5));
        listAngkatan.add(getString(R.string.angkatan6));
        listAngkatan.add(getString(R.string.angkatan7));
        listAngkatan.add(getString(R.string.angkatan8));
        listAngkatan.add(getString(R.string.angkatan9));
        listAngkatan.add(getString(R.string.angkatan10));
        listAngkatan.add(getString(R.string.angkatan11));
        listAngkatan.add(getString(R.string.angkatan12));


        ArrayAdapter<String> dataAngkatan = new ArrayAdapter<String>(TambahAnggota.this,
                R.layout.spinner_text, listAngkatan);

        spinnerAngkatan.setAdapter(dataAngkatan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imgFoto.setVisibility(View.VISIBLE);
            rlFoto.setVisibility(View.GONE);
            Picasso.with(this).load(imageUri).into(imgFoto);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TambahAnggota.this, MainActivity.class));
        finish();
    }

    @Override
    public void onAddAnggotaSuccess(String message) {
        Toast.makeText(this, "Berhasil Menyimpan Data ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(TambahAnggota.this, MainActivity.class));
        finish();
        progressDialog.dismiss();

    }

    @Override
    public void onAddAnggotaFailure(String message) {
        Toast.makeText(this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    public void upload(View view, final String nama, final String alamat, final String kontak, final String skill, final String status) {
        imageRef = storageRef.child(imageUri.getLastPathSegment());
        uploadTask = imageRef.putFile(imageUri);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                //sets and increments value of progressbar
                progressDialog.incrementProgressBy((int) progress);

            }
        });

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(TambahAnggota.this, "Error in uploading Image!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String uri = downloadUrl.toString();
                Toast.makeText(TambahAnggota.this, "Success Upload Foto", Toast.LENGTH_SHORT).show();

                ModelDataAngkatan modelDataAngkatan = new ModelDataAngkatan(nama, alamat, kontak, angkatan
                        , angkatan, uri, skill, status);
                mAddAnggotaPresenter.addAnggota(TambahAnggota.this, modelDataAngkatan);
            }
        });
    }
}
