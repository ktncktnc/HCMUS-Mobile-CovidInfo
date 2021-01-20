package com.example.covidnews.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragment;

import com.example.covidnews.R;
import com.example.covidnews.fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportFragmentManager().beginTransaction().replace(R.id.setting_container, new SettingFragment()).commit();
    }
}
