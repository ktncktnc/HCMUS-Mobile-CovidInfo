package com.example.covidnews.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidnews.MainActivity;
import com.example.covidnews.R;

public class LoginFragment extends Fragment implements View.OnClickListener{
    Button btn_login;
    EditText et_phone_number, et_password;
    TextView tv_forget_password, tv_sign_up;
    public LoginFragment(){
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        tv_sign_up = (TextView) view.findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(this);

        tv_forget_password = (TextView) view.findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(this);

        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);
        et_password = (EditText) view.findViewById(R.id.et_password);

        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.btn_login:
                //check email and password
                loginHandle();
                break;

            case R.id.tv_sign_up:
                Toast.makeText(getActivity().getApplicationContext(),"Tính năng đang được phát triển!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_sighUpFragment);
                break;

            case R.id.tv_forget_password:
                Toast.makeText(getActivity().getApplicationContext(),"Tính năng đang được phát triển!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgetPasswordFragment);
                break;
        }
    }

    private void loginHandle(){
        String phone_number = et_phone_number.getText().toString();
        String password = et_password.getText().toString();

        if (checkUser(phone_number,password)){
            Toast.makeText(getContext().getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
            /*Intent to Homepage (MainActivity)*/
            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"Phone number or password is wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkUser(String phone_number, String password){
        if (phone_number.equals("0123456789") && password.equals(123456))
            return true;
        return false;
    }
}