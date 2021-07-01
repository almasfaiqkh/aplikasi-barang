package com.tokobarang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tokobarang.ApiBarang.RegisterAPI;
import com.tokobarang.ApiBarang.Value;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://exstoreitems.000webhostapp.com/";
    @BindView(R.id.editTextID)
    EditText editTextID;
    @BindView(R.id.editTextNama)
    EditText editTextNama;
    @BindView(R.id.editTextJumlah)
    EditText editTextJumlah;
    @BindView(R.id.editTextSatuan)
    EditText editTextSatuan;
    @BindView(R.id.editTextHarga)
    EditText editTextHarga;
    @BindView(R.id.editTextStatus)
    EditText editTextStatus;

    private RadioButton radioSexButton;
    private ProgressDialog progress;


    @OnClick(R.id.buttonDaftar)
    void daftar() {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String id = editTextID.getText().toString();
        String nama = editTextNama.getText().toString();
        String jumlah = editTextJumlah.getText().toString();
        String harga = editTextHarga.getText().toString();
        String satuan = editTextSatuan.getText().toString();
        String status = editTextStatus.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.daftar(id, nama, jumlah, harga, satuan, status);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, ViewActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}