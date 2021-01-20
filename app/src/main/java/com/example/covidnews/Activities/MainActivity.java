package com.example.covidnews.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.covidnews.Application;
import com.example.covidnews.Login.LoginActivity;
import com.example.covidnews.R;
import com.example.covidnews.Fragments.HomeFragment;
import com.example.covidnews.Fragments.MapsFragment;
import com.example.covidnews.Fragments.NewsFragment;
import com.example.covidnews.Fragments.StatisticsFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationViewEx navigationView;
    private ViewPager2 viewPager2;
    private final int REQUEST_INTERNET_CODE = 10000;
    private NavigationView sideNav;
    private ImageView menuButton;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;
    private DrawerLayout drawerLayout;
    private ImageView avt;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuButton = findViewById(R.id.menubar_menu);
        menuButton.setOnClickListener(this);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this.getBaseContext(), googleSignInOptions);



        sideNav = findViewById(R.id.nvView);
        avt = sideNav.getHeaderView(0).findViewById(R.id.nav_header_imageView);
        name = sideNav.getHeaderView(0).findViewById(R.id.nav_header_textView);

        String nameStr = Application.getPrefranceData("user_name");
        String avtStr = Application.getPrefranceData("user_avt");

        if(nameStr == null || nameStr.length() == 0)
            nameStr = new String("Name");
        if(avtStr == null || avtStr.length() == 0)
            avtStr = new String("https://www.vinamilk.com.vn/sua-bot-nguoi-lon-vinamilk/wp-content/themes/suabotnguoilon/tpl/assets/img/profile/avt-default.jpg");

        name.setText(nameStr);
        Glide.with(this).load(avtStr).into(avt);

        sideNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_setting:{
                        Intent myIntent = new Intent(getBaseContext(), SettingActivity.class);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getBaseContext().startActivity(myIntent);
                        break;
                    }
                    case R.id.nav_aboutus:{
                        String url = "http://covidnewsapi.herokuapp.com/";
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        customTabsIntent.launchUrl(getBaseContext(), Uri.parse(url));
                        break;
                    }
                    case R.id.nav_logout:{
                        String type = Application.getPrefranceData("login_type");
                        if(type == "fb"){
                            LoginManager.getInstance().logOut();
                        }
                        else if(type == "gg"){
                            googleSignInClient.signOut();
                        }

                        Application.setPreferences("user_name", "");
                        Application.setPreferences("user_id", "");
                        Application.setPreferences("user_avt", "");
                        Application.setPreferences("login_type", "");

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;


                    }
                }
                return true;
            }
        });
        setViewPager();
        setBottomNavigationParams();
        permissionClaim();
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    private void setViewPager(){
        viewPager2 = findViewById(R.id.mainviewpager);
        mainViewPagerAdapter adapter = new mainViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Log.d("DBG", Integer.toString(position));
                if(position == 0){
                    navigationView.setSelectedItemId(R.id.bottombar_home);
                }
                else if(position == 1){
                    navigationView.setSelectedItemId(R.id.bottombar_statistics);
                }
                else if(position == 2) {
                    navigationView.setSelectedItemId(R.id.bottombar_news);
                }
                else
                    navigationView.setSelectedItemId(R.id.bottombar_tracing);
            }
        });


    }
    private void setBottomNavigationParams(){
        navigationView = findViewById(R.id.bottombar);
        navigationView.enableAnimation(false);
        navigationView.enableShiftingMode(false);
        navigationView.enableItemShiftingMode(false);
        navigationView.setItemHeight(80);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.bottombar_home: {
                         viewPager2.setCurrentItem(0);
                         break;
                     }
                     case R.id.bottombar_statistics: {
                         viewPager2.setCurrentItem(1);
                         break;
                     }
                     case R.id.bottombar_news: {
                         viewPager2.setCurrentItem(2);
                         break;
                     }
                     case R.id.bottombar_tracing: {
                         viewPager2.setCurrentItem(3);
                         break;
                     }
                 };
                 return true;
            };
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menubar_menu){
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class mainViewPagerAdapter extends FragmentStateAdapter{


        public mainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public mainViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public mainViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Log.d("DBG", "Position: " + Integer.toString(position));
            if(position == 0) {

                return new HomeFragment();
            }
            else if(position == 1){


                return new StatisticsFragment();
            }
            else if(position == 2)
                return new NewsFragment();
            else
                return new MapsFragment();
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    void permissionClaim(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){
            return;
        }
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){

        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case REQUEST_INTERNET_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "Oh noooo you denied my request :((", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Great!! Thank kiu for your support!!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}