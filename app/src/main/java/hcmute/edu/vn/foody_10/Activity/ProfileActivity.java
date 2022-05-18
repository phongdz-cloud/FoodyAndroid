package hcmute.edu.vn.foody_10.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextInputEditText etName, etEmail, etPhone, etAddress;
    private final IUserQuery userQuery = UserQuery.getInstance();
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        binding();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivProfile.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.REQUEST_CODE_FOLDER);
            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
            }
        });

        btnSave.setOnClickListener(view -> {
            Common.currentUserModel.setAvatar(Utils.convertImageViewToBytes(ivProfile));
            final String name = Objects.requireNonNull(etName.getText()).toString();
            final String phone = Objects.requireNonNull(etPhone.getText()).toString();
            final String address = Objects.requireNonNull(etAddress.getText()).toString();
            try {
                UserModel userModel = userQuery.findById(Common.currentUserModel.getId());
                if (!userModel.getName().equals(name) || !userModel.getPhone().equals(phone) || !userModel.getAddress().equals(address)) {
                    updatePhotoAndInfoUser(userModel);
                } else {
                    updateOnlyPhoto(userModel);
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
        }
    }

    private void updatePhotoAndInfoUser(UserModel userModel) {
        try {
            if (userModel != null) {
                final String name = Objects.requireNonNull(etName.getText()).toString();
                final String phone = Objects.requireNonNull(etPhone.getText()).toString();
                final String address = Objects.requireNonNull(etAddress.getText()).toString();
                userModel.setAvatar(Common.currentUserModel.getAvatar());
                if (!userModel.getName().equals(name)) {
                    userModel.setName(name);
                }
                if (!userModel.getPhone().equals(phone)) {
                    if (userQuery.findByPhone(phone) != null) {
                        etPhone.setError(getString(R.string.phone_number_already_exists));
                        return;
                    } else {
                        userModel.setPhone(phone);
                    }
                }
                if (!userModel.getAddress().equals(address)) {
                    userModel.setAddress(address);
                }
                Integer updateUser = userQuery.updatePhotoAndInfo(userModel);
                if (updateUser > 0) {
                    Common.currentUserModel = userModel;
                    Utils.setPreferences(Common.currentUserModel, getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE));
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
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnuListFood:
                startActivity(new Intent(ProfileActivity.this, FoodListUserActivity.class));
                break;
            case R.id.mnuAddFood:
                startActivity(new Intent(ProfileActivity.this, FoodActivity.class));
                break;
            case R.id.mnuPayments:
                startActivity(new Intent(ProfileActivity.this, CreditCardActivity.class));
                break;
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

    private void updateOnlyPhoto(UserModel userModel) {
        try {
            if (userModel != null) {
                userModel.setAvatar(Common.currentUserModel.getAvatar());
                Integer updateUser = userQuery.updateOnlyPhoto(userModel);
                if (updateUser > 0) {
                    Utils.setPreferences(Common.currentUserModel, getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE));
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
        Common.currentUserModel = null;
        SharedPreferences userPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE);
        userPreferences.edit().clear().apply();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataProfile();
    }

    private void dataProfile() {
        Glide.with(this)
                .load(Common.currentUserModel.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(ivProfile);
        etName.setText(Common.currentUserModel.getName());
        etEmail.setText(Common.currentUserModel.getEmail());
        etPhone.setText(Common.currentUserModel.getPhone());
        etAddress.setText(Common.currentUserModel.getAddress());
    }

    private void binding() {
        ivProfile = findViewById(R.id.ivProfile);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        btnSave = findViewById(R.id.btnSave);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}