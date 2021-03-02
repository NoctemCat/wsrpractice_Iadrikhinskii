package com.example.wsr_iadrikhinskii;

import java.util.List;
import java.util.logging.XMLFormatter;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

interface CurrencyAPI {
    @Headers({"Accept: application/json"})
    @GET("scripts/XML_daily?date_req={date}")
    Call<List<Currency>> getData(@Query("date") String date);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.cbr.ru/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
}
