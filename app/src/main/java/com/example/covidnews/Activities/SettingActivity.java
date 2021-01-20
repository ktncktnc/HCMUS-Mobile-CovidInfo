package com.example.covidnews.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;

import com.example.covidnews.R;
import com.example.covidnews.fragments.SettingFragment;

public class SettingActivity extends AppCompatActivity {
    private String sms_phone = null;
    private String sms_content = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sms_phone = getIntent().getStringExtra("sms_phone");

        getSupportFragmentManager().beginTransaction().replace(R.id.setting_container, new SettingFragment()).commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
