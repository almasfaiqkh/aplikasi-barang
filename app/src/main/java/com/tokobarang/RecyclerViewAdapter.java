package com.tokobarang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tokobarang.ApiBarang.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewID.setText(result.getKode_barang());
        holder.textViewNama.setText(result.getNama_barang());
        holder.textViewJumlah.setText(result.getJumlah_barang());
        holder.textViewHarga.setText(result.getHarga_barang());
        holder.textViewSatuan.setText(result.getSatuan_barang());
        holder.textViewStatus.setText(result.getStatus_barang());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textID) TextView textViewID;
        @BindView(R.id.textNama) TextView textViewNama;
        @BindView(R.id.textJumlah) TextView textViewJumlah;
        @BindView(R.id.textSatuan) TextView textViewSatuan;
        @BindView(R.id.textHarga) TextView textViewHarga;
        @BindView(R.id.textStatus) TextView textViewStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String id = textViewID.getText().toString();
            String nama = textViewNama.getText().toString();
            String jumlah = textViewJumlah.getText().toString();
            String satuan = textViewSatuan.getText().toString();
            String harga = textViewHarga.getText().toString();
            String status = textViewStatus.getText().toString();

            Intent i = new Intent(context, UpdateActivity.class);
            i.putExtra("kode_barang", id);
            i.putExtra("nama_barang", nama);
            i.putExtra("jumlah_barang", jumlah);
            i.putExtra("satuan_barang", satuan);
            i.putExtra("harga_barang", harga);
            i.putExtra("status_barang", status);
            context.startActivity(i);
        }
    }
}
