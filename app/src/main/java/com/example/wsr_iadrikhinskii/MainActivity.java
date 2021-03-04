package com.example.wsr_iadrikhinskii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView date_textView = (TextView)findViewById(R.id.text_date);
        final TextView usd_textView = (TextView)findViewById(R.id.text_curusd);
        final TextView eur_textView = (TextView)findViewById(R.id.text_cureur);

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String todayString = formatter.format(todayDate);

        CurrencyAPI api = CurrencyAPI.retrofit.create(CurrencyAPI.class);

        final Call<ValCurs> call = api.getValutes(todayString);

        call.enqueue(new Callback<ValCurs>() {
            @Override
            public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {
                if (response.isSuccessful()) {
                    ValCurs valutes = response.body();

                    date_textView.setText(valutes.getDate());
                    for(Valute val:valutes.getValutes()){
                        if(val.getCharCode().equals("USD")){
                            usd_textView.setText(val.getPrice());
                        }
                        if(val.getCharCode().equals("EUR")){
                            eur_textView.setText(val.getPrice());
                        }
                    }
                }
                else{
                    int statusCode = response.code();

                    ResponseBody errorBody = response.errorBody();
                    try {
                        date_textView.setText(errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ValCurs> call, Throwable t) {
                date_textView.setText("Что-то пошло не так: " + t.getMessage());
            }
        });

    }
}