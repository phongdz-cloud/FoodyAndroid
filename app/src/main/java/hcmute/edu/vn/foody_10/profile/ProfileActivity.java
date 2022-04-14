package hcmute.edu.vn.foody_10.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.login.LoginActivity;
import hcmute.edu.vn.foody_10.password.ChangePasswordActivity;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextInputEditText etName, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding();

        dataProfile();
    }

    public void btnChangePasswordClick(View view) {
        startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
    }

    public void btnLogoutClick(View view) {
        Common.currentUser = null;
        SharedPreferences userPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE);
        userPreferences.edit().clear().apply();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    private void dataProfile() {
        byte[] image = Common.currentUser.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        ivProfile.setImageBitmap(bitmap);
        etName.setText(Common.currentUser.getName());
        etEmail.setText(Common.currentUser.getEmail());
    }

    private void binding() {
        ivProfile = findViewById(R.id.ivProfile);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
    }

}