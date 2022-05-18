package hcmute.edu.vn.foody_10.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.CreditCardType;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class SignUpActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword, etPhone, etAddress;
    private final IUserQuery userQuery = UserQuery.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        binding();

        getSupportActionBar().hide();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivProfile.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void pickImage(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constants.REQUEST_CODE_FOLDER);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
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

    public void buttonLoginClick(View view) {
        final String name, phone, address, email, password, confirmPassword;
        name = Objects.requireNonNull(etName.getText()).toString();
        email = Objects.requireNonNull(etEmail.getText()).toString();
        phone = Objects.requireNonNull(etPhone.getText()).toString();
        address = Objects.requireNonNull(etAddress.getText()).toString();
        password = Objects.requireNonNull(etPassword.getText()).toString();
        confirmPassword = Objects.requireNonNull(etConfirmPassword.getText()).toString();
        if (name.isEmpty()) {
            etName.setError(getString(R.string.enter_name));
        } else if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.enter_phone));
        } else if (userQuery.findByPhone(phone) != null) {
            etPhone.setError(getString(R.string.phone_number_already_exists));
        } else if (address.isEmpty()) {
            etAddress.setError(getString(R.string.enter_address));
        } else if (email.isEmpty()) {
            etEmail.setError(getString(R.string.enter_email));
        } else if (!emailValidator(email)) {
            etEmail.setError(getString(R.string.email_invalidate));
        } else if (password.isEmpty()) {
            etPassword.setError(getString(R.string.enter_password));
        } else if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError(getString(R.string.enter_password));
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.password_mismatch));
        } else {
            try {
                byte[] byteImages = Utils.convertImageViewToBytes(ivProfile);
                UserModel userModel = new UserModel(null, name, email, password, byteImages, phone, address, CreditCardType.EMPTY.name());
                userQuery.insert(userModel);
                Toast.makeText(this, getString(R.string.sign_up_successfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            } catch (Exception ex) {
                Toast.makeText(this, getString(R.string.sign_up_failed, ex.getMessage()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void binding() {
        ivProfile = findViewById(R.id.ivProfile);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
    }

    private boolean emailValidator(final String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


}