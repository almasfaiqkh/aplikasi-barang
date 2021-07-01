package com.tokobarang.ApiBarang;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterAPI {

    @FormUrlEncoded
    @POST("insert.php")
    Call<Value> daftar(@Field("kode_barang") String id,
                       @Field("nama_barang") String nama,
                       @Field("jumlah_barang") String jumlah,
                       @Field("harga_barang") String harga,
                       @Field("satuan_barang") String satuan,
                       @Field("status_barang") String status);

    @FormUrlEncoded
    @POST("update.php")
    Call<Value> ubah(@Field("kode_barang") String id,
                     @Field("nama_barang") String nama,
                     @Field("jumlah_barang") String jumlah,
                     @Field("harga_barang") String harga,
                     @Field("satuan_barang") String satuan,
                     @Field("status_barang") String status);

    @FormUrlEncoded
    @POST("delete.php")
    Call<Value> hapus(@Field("kode_barang") String id);

    @FormUrlEncoded
    @POST("search.php")
    Call<Value> search(@Field("search") String search);

    @GET("view.php")
    Call<Value> view();
}