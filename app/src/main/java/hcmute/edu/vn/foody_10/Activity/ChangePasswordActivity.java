package hcmute.edu.vn.foody_10.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputEditText etPassword, etConfirmPassword;
    private final IUserQuery userQuery = UserQuery.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        binding();
    }

    public void btnChangePasswordClick(View view) {
        final String password = Objects.requireNonNull(etPassword.getText()).toString();
        final String confirmPassword = Objects.requireNonNull(etConfirmPassword.getText()).toString();
        if (password.isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
        } else if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError(getString(R.string.confirm_password));
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.password_mismatch));
        } else {
            try {
                Common.currentUserModel.setPassword(password);
                Integer isUpdateSuccess = userQuery.updatePassword(Common.currentUserModel);
                if (isUpdateSuccess > 0) {
                    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE);
                    Toast.makeText(this, getString(R.string.change_password_succesfully), Toast.LENGTH_SHORT).show();
                    Utils.setPreferences(Common.currentUserModel, sharedPreferences);
                    startActivity(new Intent(ChangePasswordActivity.this, ProfileActivity.class));
                    finish();
                }
            } catch (Exception ex) {
                Toast.makeText(this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void binding() {
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
    }


}