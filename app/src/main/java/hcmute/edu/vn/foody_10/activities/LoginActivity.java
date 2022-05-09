package hcmute.edu.vn.foody_10.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.common.Utils;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.models.User;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private final IUserQuery userQuery = UserQuery.getInstance();
    private CheckBox cbRemember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_REMEMBER_CHECKED, MODE_PRIVATE);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);

        boolean isChecked = sharedPreferences.getBoolean(Constants.SHARED_PREFERENCE_REMEMBER_CHECKBOX, false);
        if (isChecked) {
            cbRemember.setChecked(true);
            etEmail.setText(sharedPreferences.getString(Constants.SHARED_PREFERENCE_REMEMBER_EMAIL, ""));
            etPassword.setText(sharedPreferences.getString(Constants.SHARED_PREFERENCE_REMEMBER_PASSWORD, ""));
        }
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

                    rememberMe(cbRemember.isChecked(), user.getEmail(), user.getPassword());
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.email_or_password_incorrect), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void rememberMe(boolean isChecked, String email, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_PREFERENCE_REMEMBER_CHECKED, MODE_PRIVATE).edit();
        if (isChecked) {
            editor.putBoolean(Constants.SHARED_PREFERENCE_REMEMBER_CHECKBOX, true);
            editor.putString(Constants.SHARED_PREFERENCE_REMEMBER_EMAIL, email);
            editor.putString(Constants.SHARED_PREFERENCE_REMEMBER_PASSWORD, password);
        } else {
            editor.putBoolean(Constants.SHARED_PREFERENCE_REMEMBER_CHECKBOX, false);
        }
        editor.apply();
    }
}