package com.tokobarang.ApiLogin;

public class UtilsApi {

    public static final String BASE_URL_API = "https://exstoreitems.000webhostapp.com/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
