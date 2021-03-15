package com.example.wsr_iadrikhinskii;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PayFragment extends Fragment {
    TextView textMsg;
    final static String KEY_MSG_2 = "FRAGMENT_PAY_MSG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, null);
        textMsg = (TextView) view.findViewById(R.id.tvMessage);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String msg = bundle.getString(KEY_MSG_2);
            if (msg != null) {
                textMsg.setText(msg);
            }
        }
        return view;
    }

    public void setMsg(String msg) {
        textMsg.setText(msg);
    }
}