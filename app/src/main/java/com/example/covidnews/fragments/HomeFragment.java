package com.example.covidnews.fragments;

import android.content.Intent;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import com.example.covidnews.R;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private String sms = new String("Tôi cảm thấy không khỏe...");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        MaterialButton callButton = view.findViewById(R.id.infobar_buttons_call);
        MaterialButton sendsmsButton = view.findViewById(R.id.infobar_buttons_sendsms);
        RelativeLayout test = view.findViewById(R.id.test);
        TextView des = view.findViewById(R.id.test_text_des);
        des.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);

        test.setOnClickListener(this);
        callButton.setOnClickListener(this);
        sendsmsButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.infobar_buttons_call:{
                Intent intentDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "19009095"));
                getContext().startActivity(intentDial);
                break;
            }
            case R.id.infobar_buttons_sendsms:{
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ "0386886675"));
                smsIntent.putExtra("sms_body",sms);
                startActivity(smsIntent);
                break;
            }
            case R.id.test:{
                String url = "https://tokhaiyte.vn/";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getContext(), Uri.parse(url));
                break;
            }
        }
    }
}
