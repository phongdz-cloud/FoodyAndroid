package hcmute.edu.vn.foody_10.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import hcmute.edu.vn.foody_10.activities.MainActivity;
import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.common.Utils;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.signup.SignUpActivity;
import hcmute.edu.vn.foody_10.signup.User;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private final IUserQuery userQuery = UserQuery.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

    }

    public void tvSignUpClick(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    public void btnLoginClick(View view) {
        final String email = Objects.requireNonNull(etEmail.getText()).toString();
        final String password = Objects.requireNonNull(etPassword.getText()).toString();
        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
        } else if (password.isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
        } else {
            try {
                User user = userQuery.findByUserEmailAndPassword(email, password);
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Common.currentUser = user;
                    startActivity(intent);
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE);
                    Utils.setPreferences(Common.currentUser, sharedPreferences);
//                    saveDataUser(Common.currentUser);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.email_or_password_incorrect), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
            }

        }
    }
}