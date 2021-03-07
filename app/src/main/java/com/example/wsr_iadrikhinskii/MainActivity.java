package com.example.wsr_iadrikhinskii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ValCurs valutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView date_textView = (TextView)findViewById(R.id.text_date);
        final TextView usd_textView = (TextView)findViewById(R.id.text_curusd);
        final TextView eur_textView = (TextView)findViewById(R.id.text_cureur);

        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String todayString = formatter.format(todayDate);

        valutes = null;
        try {
            valutes = (ValCurs) InternalStorage.readObject(getApplicationContext(),"VALUTES");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(valutes != null){
            SimpleDateFormat formatterData = new SimpleDateFormat("dd.MM.yyyy");
            cal.add(Calendar.DATE, -1);
            String yesterdayString = formatterData.format(cal.getTime());

            if(!yesterdayString.equals(valutes.getDate())){
                InternalStorage.deleteFiles(getApplicationContext());
                Log.i("cross", "Files deleted, cause the date in the cache is different");
                valutes = null;
            }
        }

        if(valutes == null){
            CurrencyAPI api = CurrencyAPI.retrofit.create(CurrencyAPI.class);
            final Call<ValCurs> call = api.getValutes(todayString);

            call.enqueue(new Callback<ValCurs>() {
                @Override
                public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {
                    if (response.isSuccessful()) {
                        Log.i("cross","Downloaded information");
                        FrameLayout lay = (FrameLayout)findViewById(R.id.frame_currency);
                        lay.setClickable(true);
                        valutes = response.body();

                        try {
                            InternalStorage.writeObject(getApplicationContext(), "VALUTES", valutes);
                            Log.i("cross","Wrote to cache");
                        }
                        catch (IOException e) {
                            Log.e("Err", e.getMessage());
                        }

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
        else {
            Log.i("cross","Got from cache");
            FrameLayout lay = (FrameLayout)findViewById(R.id.frame_currency);
            lay.setClickable(true);
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
    }

    public void click_ATM(View view){

    }

    public void click_Currency(View view){
        Intent intent = new Intent(this, Currencies.class);
        startActivity(intent);
    }

    public void click_autorization(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
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
            Log.i("cross", "fail " + login.getText() + " " + pass.getText());
        });
        builder.setPositiveButton("Войти", (dialog, which) -> {
            Log.i("cross", "pass " + login.getText() + " " + pass.getText());
        });

        //builder.setView(but.getView());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}