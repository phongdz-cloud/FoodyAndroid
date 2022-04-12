package hcmute.edu.vn.foody_10.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_10.R;

public class LoginActivity extends AppCompatActivity {
    public static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    protected void onStart() {
        isLogin = true;
        super.onStart();
    }
}