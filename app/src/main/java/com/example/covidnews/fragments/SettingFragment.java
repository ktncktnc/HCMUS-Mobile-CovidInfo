package com.example.covidnews.fragments;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.covidnews.Application;
import com.example.covidnews.R;

public class SettingFragment extends PreferenceFragmentCompat {
    Preference sms_content;
    Preference sms_phone;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);
        sms_content = findPreference("key_sms_content");
        sms_phone = findPreference("key_sms_phone");
        sms_phone.setSummary(Application.getPrefranceData("sms_phone"));
        sms_content.setSummary(Application.getPrefranceData("sms_content"));
        sms_phone.setOnPreferenceChangeListener(listener);
        sms_content.setOnPreferenceChangeListener(listener) ;

    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            if(preference instanceof EditTextPreference){
                if(preference.getKey().equals("key_sms_phone")){
                    //preference.setSummary(value);
                    Application.setPreferences("sms_phone", value);
                    Application.setPreferencesBoolean("sms_changed", true);
                    preference.setSummary(value);
                    Log.d("DBG", "phone");

                }
                else if(preference.getKey().equals("key_sms_content")){
                    //preference.setSummary(value);
                    Application.setPreferences("sms_content", value);
                    Application.setPreferencesBoolean("sms_changed", true);
                    preference.setSummary(value);
                    Log.d("DBG", "phonedsds");
                }
            }
            return true;
        }
    };
}
