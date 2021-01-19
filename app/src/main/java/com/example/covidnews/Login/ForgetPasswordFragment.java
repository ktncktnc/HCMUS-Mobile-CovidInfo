package com.example.covidnews.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidnews.R;

public class ForgetPasswordFragment extends Fragment{
    Button btn_send;
    EditText et_phone;

    public ForgetPasswordFragment(){
        // Required empty public constructor
    }


    public static ForgetPasswordFragment newInstance(){
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        et_phone = (EditText)view.findViewById(R.id.editText_input_phone);

        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String _phone = et_phone.getText().toString().trim();

                //check validation of email or phone
                if (!checkVailate(_phone))
                    return;

                //send code to user's mail or user's phone
                sendCode(_phone);

                //navigation to verification code
                Navigation.findNavController(v).navigate(R.id.action_forgetPasswordFragment_to_verificationCodeFragment);

            }
        });

        return view;
    }

    private void sendCode(String phone){

    }

    private boolean checkVailate(String _phone){
        Boolean check_phone = true;
        if (_phone.length() > 0){
            if (_phone.length() != 10 || _phone.charAt(0) != '0'){
                Toast.makeText(getContext().getApplicationContext(), "You phone is not correct", Toast.LENGTH_LONG).show();
                check_phone = false;
            }
        }
        return check_phone;
    }
}