package com.example.wsr_iadrikhinskii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CurrencyAPI api = CurrencyAPI.retrofit.create(CurrencyAPI.class);
        final Call<List<Currency>> call = api.getData("02/03/2021");

        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                final TextView textView = (TextView)findViewById(R.id.text_curusd);
                textView.setText(response.body().get(0).toString());
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });
    }
}