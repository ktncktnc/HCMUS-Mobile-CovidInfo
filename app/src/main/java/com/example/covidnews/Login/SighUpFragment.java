package com.example.covidnews.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.covidnews.Activities.MainActivity;
import com.example.covidnews.Application;
import com.example.covidnews.Database.DatabaseHelper;
import com.example.covidnews.R;

public class SighUpFragment extends Fragment{
    Button btn_register;
    EditText et_phone, et_name, et_password, et_confirm_password;
    ImageButton backButton;
    DatabaseHelper db;
    public SighUpFragment(){
        // Required empty public constructor
    }


    public static SighUpFragment newInstance(){
        SighUpFragment fragment = new SighUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sigh_up, container, false);

        et_name = (EditText)view.findViewById(R.id.editText_input_full_name);
        et_phone = (EditText)view.findViewById(R.id.editText_input_phone_number);
        et_password = (EditText)view.findViewById(R.id.editText_input_password);
        backButton = view.findViewById(R.id.back_button);
        et_confirm_password = (EditText)view.findViewById(R.id.editText_input_confirm_password);
        db = new DatabaseHelper(getContext());

        btn_register = (Button)view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpHandle();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void signUpHandle(){
        String _name = et_name.getText().toString().trim();
        String _phone = et_phone.getText().toString().trim();
        String _password = et_password.getText().toString().trim();
        String _confirmPassword = et_confirm_password.getText().toString().trim();

        if (!checkValidate(_phone, _password, _confirmPassword))
            return;

        //add database
        if (db.isPhoneNumberExists(_phone)){
            if (db.insert(_name,_phone,_password))
                Toast.makeText(getContext(), "Register success!",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Register fail!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Register fail! Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }
        Application.setPreferences("user_name", et_name.getText().toString());
        Toast.makeText(getContext().getApplicationContext(), "Sign in success", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    private boolean checkValidate(String _phone, String _password, String _confirmPassword){
        //check phone input
        if (_phone.equals("")) {
            Toast.makeText(getContext().getApplicationContext(), "You must type phone", Toast.LENGTH_LONG).show();
            return false;
        }

        if (_phone.length() != 10) {
            Toast.makeText(getContext().getApplicationContext(), "Phone number must have 10 numbers", Toast.LENGTH_LONG).show();
            return false;
        }


        //length of password is more 6 characters
        if (_password.length() < 6){
            Toast.makeText(getContext().getApplicationContext(), "Password must have at least 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        //check password match confirm password
        if (!_password.equals(_confirmPassword)){
            Toast.makeText(getContext().getApplicationContext(), "Confirm password doesn't match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}