package com.example.wsr_iadrikhinskii;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    FragmentManager myFragmentManager;
    HomeFragment fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout)findViewById(R.id.container);

        final int[] ICONS = new int[]{
                R.drawable.home,
                R.drawable.pay,
                R.drawable.history,
                R.drawable.dialog};

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case (0):
                        myFragmentManager = getSupportFragmentManager();
                        myFragmentManager.beginTransaction()
                                .replace(R.id.container, HomeFragment.class, null)
                                .commit();
                        break;
                    case (1):
                        myFragmentManager = getSupportFragmentManager();
                        myFragmentManager.beginTransaction()
                                .replace(R.id.container, PayFragment.class, null)
                                .commit();
                        break;
                    case (2):
                        myFragmentManager = getSupportFragmentManager();
                        myFragmentManager.beginTransaction()
                                .replace(R.id.container, HistoryFragment.class, null)
                                .commit();
                        break;
                    case (3):
                        myFragmentManager = getSupportFragmentManager();
                        myFragmentManager.beginTransaction()
                                .replace(R.id.container, DialogFragment.class, null)
                                .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        myFragmentManager = getSupportFragmentManager();
        fragmentHome = new HomeFragment();

        if(savedInstanceState == null){
            FragmentTransaction fragmentTransaction = myFragmentManager
                    .beginTransaction();

            fragmentTransaction.add(R.id.container, fragmentHome, null);
            fragmentTransaction.commit();
        }
    }

    public void settings(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void goBack(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}