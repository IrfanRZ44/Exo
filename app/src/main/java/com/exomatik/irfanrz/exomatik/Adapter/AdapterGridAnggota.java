package com.exomatik.irfanrz.exomatik.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 03/11/2018.
 */

public class AdapterGridAnggota extends BaseAdapter {
    private Context context;
    private ArrayList<ModelDataAngkatan> dataOrang;

    public AdapterGridAnggota(Context context, ArrayList<ModelDataAngkatan> dataOrang) {
        this.context = context;
        this.dataOrang = dataOrang;
    }

    @Override
    public int getCount() {
        return dataOrang.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.grid_image_angkatan, null);

        ImageView img = (ImageView) v.findViewById(R.id.image_warna);
        TextView text = (TextView) v.findViewById(R.id.text_warna);

        Uri uri = Uri.parse(dataOrang.get(position).photo);
        Picasso.with(context).load(uri).into(img);
        text.setText(dataOrang.get(position).nama);

        return v;
    }
}
