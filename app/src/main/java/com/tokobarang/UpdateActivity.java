package com.tokobarang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class UpdateActivity extends AppCompatActivity {

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
    @BindView(R.id.buttonUbah)
    Button btnUbah;

    SharedPrefManager sharedPrefManager;
    private ProgressDialog progress;

    @OnClick(R.id.buttonUbah)
    void ubah() {
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()) {

            //membuat progress dialog
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Loading ...");
            progress.show();

            //mengambil data dari edittext
            String id = editTextID.getText().toString();
            String nama = editTextNama.getText().toString();
            String jumlah = editTextJumlah.getText().toString();
            String satuan = editTextSatuan.getText().toString();
            String harga = editTextHarga.getText().toString();
            String status = editTextStatus.getText().toString();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RegisterAPI api = retrofit.create(RegisterAPI.class);
            Call<Value> call = api.ubah(id, nama, jumlah, harga, satuan, status);
            call.enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    String value = response.body().getValue();
                    String message = response.body().getMessage();
                    progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            new AlertDialog.Builder(UpdateActivity.this)
                    .setTitle("PERINGATAN!")
                    .setMessage("Anda harus login terlebih dahulu untuk menggunakan fitur ini!")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data");

        Intent intent = getIntent();
        String id = intent.getStringExtra("kode_barang");
        String nama = intent.getStringExtra("nama_barang");
        String jumlah = intent.getStringExtra("jumlah_barang");
        String satuan = intent.getStringExtra("satuan_barang");
        String harga = intent.getStringExtra("harga_barang");
        String status = intent.getStringExtra("status_barang");

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getSPSudahLogin()) {
            btnUbah.setVisibility(View.VISIBLE);
        } else {
            btnUbah.setVisibility(View.GONE);
            editTextNama.setFocusableInTouchMode(false);
            editTextJumlah.setFocusableInTouchMode(false);
            editTextSatuan.setFocusableInTouchMode(false);
            editTextHarga.setFocusableInTouchMode(false);
            editTextStatus.setFocusableInTouchMode(false);
        }

        editTextID.setText(id);
        editTextNama.setText(nama);
        editTextJumlah.setText(jumlah);
        editTextSatuan.setText(satuan);
        editTextHarga.setText(harga);
        editTextStatus.setText(status);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete:
                if (sharedPrefManager.getSPSudahLogin()) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateActivity.this);
                    alertDialogBuilder.setTitle("Peringatan");
                    alertDialogBuilder
                            .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                            .setCancelable(false)
                            .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int kode_barang) {

                                    String npm = editTextID.getText().toString();
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl(URL)
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();
                                    RegisterAPI api = retrofit.create(RegisterAPI.class);
                                    Call<Value> call = api.hapus(npm);
                                    call.enqueue(new Callback<Value>() {
                                        @Override
                                        public void onResponse(Call<Value> call, Response<Value> response) {
                                            String value = response.body().getValue();
                                            String message = response.body().getMessage();
                                            if (value.equals("1")) {
                                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(UpdateActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Value> call, Throwable t) {
                                            t.printStackTrace();
                                            Toast.makeText(UpdateActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    new AlertDialog.Builder(UpdateActivity.this)
                            .setTitle("PERINGATAN!")
                            .setMessage("Anda harus login terlebih dahulu untuk menggunakan fitur ini!")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_delete);
        if (sharedPrefManager.getSPSudahLogin()) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }
}