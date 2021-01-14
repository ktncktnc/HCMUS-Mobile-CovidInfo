package com.example.covidnews.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidnews.R;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        MaterialButton callButton = view.findViewById(R.id.infobar_buttons_call);
        MaterialButton sendsmsButton = view.findViewById(R.id.infobar_buttons_sendsms);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Call clicked", Toast.LENGTH_SHORT);
            }
        });
        sendsmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Call clicked", Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

}
