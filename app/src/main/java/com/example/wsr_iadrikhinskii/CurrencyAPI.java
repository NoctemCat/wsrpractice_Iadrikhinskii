package com.example.wsr_iadrikhinskii;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface CurrencyAPI {

    @GET("scripts/XML_daily.asp")
    Call<ValCurs> getValutes(@Query("date_req") String date);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.cbr.ru/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
}
