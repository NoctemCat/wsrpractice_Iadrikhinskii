package com.example.wsr_iadrikhinskii;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
/*
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
*/
    }

    public void click_ATM(View view){
        Intent intent = new Intent(this, bankomats.class);
        startActivity(intent);
    }

    public void click_Currency(View view){

    }

    public void click_autorization(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        builder.setTitle("Авторизация");
        builder.setCancelable(false);

        RelativeLayout layout = new RelativeLayout(this);
        layout.setGravity(Gravity.CENTER);
        LinearLayout lin = new LinearLayout(this);
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.setGravity(Gravity.CENTER);

        TextView v = new TextView(this);
        v.setText("Введите ваш логин или пароль");
        v.setGravity(Gravity.CENTER_HORIZONTAL);
        v.setTextSize(18);
        v.setTextColor(getColor(R.color.grey));
        lin.addView(v);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText login = new EditText(this);
        login.setLayoutParams(lpView);
        login.setText("");
        login.setTextColor(getColor(R.color.white));
        login.setHint("Логин");
        login.setHintTextColor(getColor(R.color.grey));
        lin.addView(login);

        EditText pass = new EditText(this);
        pass.setLayoutParams(lpView);
        pass.setText("");
        pass.setTextColor(getColor(R.color.white));
        pass.setHint("Пароль");
        pass.setHintTextColor(getColor(R.color.grey));
        pass.setInputType(129);
        lin.addView(pass);

        layout.addView(lin);
        builder.setView(layout);

        builder.setNegativeButton("Отмена", (dialog, which) -> {
            Log.v("cross", "fail " + login.getText() + " " + pass.getText());
        });
        builder.setPositiveButton("Войти", (dialog, which) -> {
            Log.v("cross", "pass " + login.getText() + " " + pass.getText());
        });

        //builder.setView(but.getView());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}