package com.exomatik.irfanrz.exomatik.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.Adapter.ViewPagerAdapter;
import com.exomatik.irfanrz.exomatik.Fragment.fragmentAnggota;
import com.exomatik.irfanrz.exomatik.Fragment.fragmentKegiatan;
import com.exomatik.irfanrz.exomatik.Fragment.fragmentPembelajaran;
import com.exomatik.irfanrz.exomatik.Fragment.fragmentPengaturan;
import com.exomatik.irfanrz.exomatik.Fragment.fragmentUang;
import com.exomatik.irfanrz.exomatik.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;
    private boolean exit = false;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tabKategori = (TabLayout) findViewById(R.id.tab_Kategori);
        viewKategori = (ViewPager) findViewById(R.id.view_Kategori);

        ViewPagerAdapter adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        adapterKategori.AddFragment(new fragmentKegiatan());
        adapterKategori.AddFragment(new fragmentPembelajaran());
        adapterKategori.AddFragment(new fragmentAnggota());
        adapterKategori.AddFragment(new fragmentUang());
        adapterKategori.AddFragment(new fragmentPengaturan());

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);

        tabKategori.getTabAt(0).setIcon(R.drawable.ic_kegiatan_white);
        tabKategori.getTabAt(1).setIcon(R.drawable.ic_pembelajaran_blue);
        tabKategori.getTabAt(2).setIcon(R.drawable.ic_anggota_blue);
        tabKategori.getTabAt(3).setIcon(R.drawable.ic_uang_blue);
        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_blue);

        tabKategori.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.ic_kegiatan_white);
                        tabKategori.getTabAt(1).setIcon(R.drawable.ic_pembelajaran_blue);
                        tabKategori.getTabAt(2).setIcon(R.drawable.ic_anggota_blue);
                        tabKategori.getTabAt(3).setIcon(R.drawable.ic_uang_blue);
                        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_blue);
                        break;
                    case 1:
                        tabKategori.getTabAt(0).setIcon(R.drawable.ic_kegiatan_blue);
                        tab.setIcon(R.drawable.ic_pembelajaran_white);
                        tabKategori.getTabAt(2).setIcon(R.drawable.ic_anggota_blue);
                        tabKategori.getTabAt(3).setIcon(R.drawable.ic_uang_blue);
                        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_blue);
                        break;
                    case 2:
                        tabKategori.getTabAt(0).setIcon(R.drawable.ic_kegiatan_blue);
                        tabKategori.getTabAt(1).setIcon(R.drawable.ic_pembelajaran_blue);
                        tab.setIcon(R.drawable.ic_anggota_white);
                        tabKategori.getTabAt(3).setIcon(R.drawable.ic_uang_blue);
                        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_blue);
                        break;
                    case 3:
                        tabKategori.getTabAt(0).setIcon(R.drawable.ic_kegiatan_blue);
                        tabKategori.getTabAt(1).setIcon(R.drawable.ic_pembelajaran_blue);
                        tabKategori.getTabAt(2).setIcon(R.drawable.ic_anggota_blue);
                        tab.setIcon(R.drawable.ic_uang_white);
                        tabKategori.getTabAt(4).setIcon(R.drawable.ic_setting_blue);
                        break;
                    case 4:
                        tabKategori.getTabAt(0).setIcon(R.drawable.ic_kegiatan_blue);
                        tabKategori.getTabAt(1).setIcon(R.drawable.ic_pembelajaran_blue);
                        tabKategori.getTabAt(2).setIcon(R.drawable.ic_anggota_blue);
                        tabKategori.getTabAt(3).setIcon(R.drawable.ic_uang_blue);
                        tab.setIcon(R.drawable.ic_setting_white);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (exit) {
            minimizeApp();
            return;
        } else {
            Toast toast = Toast.makeText(MainActivity.this, "Tekan Cepat 2 Kali untuk Keluar", Toast.LENGTH_SHORT);
            toast.show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
