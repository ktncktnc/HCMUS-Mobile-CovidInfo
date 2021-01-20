package com.example.covidnews.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidnews.R;
import com.example.covidnews.Fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {
    private String sms_phone = null;
    private String sms_content = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getSupportFragmentManager().beginTransaction().replace(R.id.setting_container, new SettingFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
