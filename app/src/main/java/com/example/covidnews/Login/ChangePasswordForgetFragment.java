package com.example.covidnews.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidnews.MainActivity;
import com.example.covidnews.R;

public class ChangePasswordForgetFragment extends Fragment{
    Button btn_save;
    EditText et_new_password, et_confirm_new_password;

    public ChangePasswordForgetFragment(){
    }

    public static ChangePasswordForgetFragment newInstance(String param1, String param2){
        ChangePasswordForgetFragment fragment = new ChangePasswordForgetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_change_password_forget, container, false);

        et_new_password = (EditText) view.findViewById(R.id.editText_input_new_password);
        et_confirm_new_password = (EditText) view.findViewById(R.id.editText_input_confirm_new_password);

        btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String new_password = et_new_password.getText().toString().trim();
                String confirm_new_password = et_confirm_new_password.getText().toString().trim();

                // check validate new password
                if (!checkValdidatePassword(new_password))
                    return;

                // check new password match confirm new password
                if (!checkMatchingPassword(new_password, confirm_new_password))
                    return;

                //change database

                //intent to hompage
                Toast.makeText(getContext().getApplicationContext(), "Change password success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
               //intent.putExtra("isLogined", true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private boolean checkMatchingPassword(String new_password, String confirm_new_password){
        if (new_password.equals(confirm_new_password))
            return true;

        Toast.makeText(getContext().getApplicationContext(), "Confirm password doesn't match", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean checkValdidatePassword(String new_password){
        if (new_password.length() < 6){
            Toast.makeText(getContext().getApplicationContext(), "Your new password must have at least 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}