package com.example.wsr_iadrikhinskii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class Currencies extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<Valute> mValutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ValCurs valutes = null;
        try {
            valutes = (ValCurs) InternalStorage.readObject(getApplicationContext(),"VALUTES");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(valutes != null){
            mValutes = valutes.getValutes();
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);

            CurrencyAdapter adapter = new CurrencyAdapter(mValutes);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}