package com.exomatik.irfanrz.exomatik.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exomatik.irfanrz.exomatik.DataModel.ModelDataAngkatan;
import com.exomatik.irfanrz.exomatik.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 17/09/2018.
 */

public class RecyclerAngkatan extends RecyclerView.Adapter<RecyclerAngkatan.bidangViewHolder> {
    private ArrayList<ModelDataAngkatan> dataList;
    private Context context;

    public RecyclerAngkatan(ArrayList<ModelDataAngkatan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public bidangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_anggota, parent, false);
        this.context = parent.getContext();
        return new bidangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(bidangViewHolder holder, int position) {
        Uri uri = Uri.parse(dataList.get(position).photo);
        Picasso.with(context).load(uri).into(holder.imageView);

        holder.txtNama.setText(dataList.get(position).nama);
        holder.txtStatus.setText("Anggota " + dataList.get(position).status);
        holder.txtAlamat.setText(dataList.get(position).alamat);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class bidangViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtStatus, txtAlamat;
        private ImageView imageView;

        public bidangViewHolder(View itemView) {
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.text_title);
            imageView = (ImageView) itemView.findViewById(R.id.listImage);
            txtStatus = (TextView) itemView.findViewById(R.id.text_status);
            txtAlamat = (TextView) itemView.findViewById(R.id.text_alamat);
        }
    }
}
