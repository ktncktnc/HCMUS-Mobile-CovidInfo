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

public class VerificationCodeFragment extends Fragment{
    Button btn_verify;
    EditText et_code;
    public VerificationCodeFragment(){
        // Required empty public constructor
    }


    public static VerificationCodeFragment newInstance(){
        VerificationCodeFragment fragment = new VerificationCodeFragment();
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
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);

        et_code = (EditText)view.findViewById(R.id.editText_input_code_verification);

        btn_verify = (Button)view.findViewById(R.id.btn_verify_code);
        btn_verify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String _code = et_code.getText().toString();

                if (!checkCodeValidation(_code)){
                    Toast.makeText(getContext().getApplicationContext(), "Your code is not correct", Toast.LENGTH_LONG).show();
                    return;
                }

                //navigation to change password fragment
                Navigation.findNavController(v).navigate(R.id.action_verificationCodeFragment_to_changePasswordForgetFragment);
            }
        });
        return view;
    }

    private boolean checkCodeValidation(String code){
        if (code.equals("123456"))
            return true;
        return false;
    }
}