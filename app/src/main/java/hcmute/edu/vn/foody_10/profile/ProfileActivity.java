package hcmute.edu.vn.foody_10.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.common.Utils;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.login.LoginActivity;
import hcmute.edu.vn.foody_10.password.ChangePasswordActivity;
import hcmute.edu.vn.foody_10.signup.User;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextInputEditText etName, etEmail;
    private final IUserQuery userQuery = UserQuery.getInstance();
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding();


        ivProfile.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.REQUEST_CODE_FOLDER);
            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
            }
        });

        btnSave.setOnClickListener(view -> {
             Common.currentUser.setAvatar(Utils.convertImageViewToBytes(ivProfile));
            final String name = Objects.requireNonNull(etName.getText()).toString();
            try {
                User user = userQuery.findById(Common.currentUser.getId());
                if (!user.getName().equals(name)) {
                    updatePhotoAndName(user);
                } else {
                    updateOnlyPhoto(user);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(ProfileActivity.this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });

        dataProfile();
    }

    public void btnChangePasswordClick(View view) {
        startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            ivProfile.setImageBitmap(Utils.convertUriToBitmap(data, getContentResolver()));
            btnSave.performClick();
        }
    }

    private void updatePhotoAndName(User user) {
        try {
            if (user != null) {
                final String name = Objects.requireNonNull(etName.getText()).toString();
                user.setAvatar(Common.currentUser.getAvatar());
                user.setName(name);
                Integer updateUser = userQuery.updatePhotoAndName(user);
                if (updateUser > 0) {
                    Common.currentUser.setName(name);
                    Utils.setPreferences(Common.currentUser, getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE));
                    Toast.makeText(this, R.string.update_profile_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.update_profile_failed, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnuChangePic:
                ivProfile.performClick();
                break;
            case R.id.mnuRemovePic:
                ivProfile.setImageResource(R.drawable.default_profile);
                btnSave.performClick();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateOnlyPhoto(User user) {
        try {
            if (user != null) {
                user.setAvatar(Common.currentUser.getAvatar());
                Integer updateUser = userQuery.updateOnlyPhoto(user);
                if (updateUser > 0) {
                    Utils.setPreferences(Common.currentUser, getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE));
                    Toast.makeText(this, R.string.update_profile_successfully, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.update_profile_failed, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_FOLDER && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            startActivityForResult(intent, Constants.REQUEST_CODE_FOLDER);
        } else {
            Toast.makeText(this, R.string.permission_required, Toast.LENGTH_SHORT).show();
        }
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
        btnSave = findViewById(R.id.btnSave);
    }

}