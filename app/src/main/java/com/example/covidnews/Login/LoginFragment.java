package com.example.covidnews.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.covidnews.Activities.MainActivity;
import com.example.covidnews.Application;
import com.example.covidnews.Database.DatabaseHelper;
import com.example.covidnews.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private Button btn_login;
    private EditText et_phone_number, et_password;
    private TextView tv_forget_password, tv_sign_up;
    private LoginButton fbloginButton;
    private ImageButton ggloginButton;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private int GG_SIGN_IN_CODE = 1;
    DatabaseHelper db;
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

        db = new DatabaseHelper(getContext());
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);


        tv_sign_up = (TextView) view.findViewById(R.id.tv_sign_up);
        tv_sign_up.setOnClickListener(this);

        tv_forget_password = (TextView) view.findViewById(R.id.tv_forget_password);
        tv_forget_password.setOnClickListener(this);

        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);
        et_password = (EditText) view.findViewById(R.id.et_password);

        callbackManager = CallbackManager.Factory.create();

        fbloginButton = view.findViewById(R.id.fb_login_button);
        fbloginButton.setHeight(400);
        fbloginButton.setFragment(this);

        fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Application.setPreferences("login_type", "fb");
                Application.setPreferences("user_id", loginResult.getAccessToken().getUserId());
                Application.setPreferences("user_avt", loginResult.getAccessToken().getUserId() + "/picture?return_ssl_resources=1");

                Log.d("DBG", "Fb login");
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if(account != null){
            Application.setPreferences("login_type", "gg");
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        }

        ggloginButton = view.findViewById(R.id.gg_login_button);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
        ggloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, GG_SIGN_IN_CODE);
            }
        });

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
            /*Intent to Homepage (MainActivity)*/
            Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else {
            Toast.makeText(getActivity().getApplicationContext(),"Phone number or password is wrong!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkUser(String _phone, String _password){

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

        if (db.checkAccount(_phone, _password)){
            Toast.makeText(getContext(), "Login success!",Toast.LENGTH_SHORT).show();
            return  true;
        }
        else
            Toast.makeText(getContext(), "Something was wrong!",Toast.LENGTH_SHORT).show();

        /*if (_phone.equals("0123456789") && _password.equals("123456"))
            return true;*/
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GG_SIGN_IN_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            ggloginHandle(task);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void ggloginHandle(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Application.setPreferences("login_type", "gg");

            // Signed in successfully, show authenticated UI.
            Application.setPreferences("user_id", account.getId());
            Application.setPreferences("user_name", account.getDisplayName());
            Application.setPreferences("user_avt", account.getPhotoUrl().toString());
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());

        }
    }

}