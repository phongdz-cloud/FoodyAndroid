package hcmute.edu.vn.foody_10.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;

public class FoodActivity extends AppCompatActivity {
    private ImageView imageView;
    private RadioButton rbCheckFood, rbCheckBeverage;
    private EditText etFoodName, etFoodDescription, etPrice;
    private Button btnSave, btnCancel;
    private IFoodQuery foodQuery;
    private FoodModel foodModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodModel = (FoodModel) getIntent().getSerializableExtra("food");
        foodQuery = FoodQuery.getInstance();
        binding();

        if (foodModel != null) {
            Glide.with(this)
                    .load(foodModel.getPhotoFood())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(imageView);
            etFoodName.setText(foodModel.getFoodName());
            etFoodDescription.setText(foodModel.getFoodDescription());
            etPrice.setText(String.valueOf(foodModel.getPrice()));
            if (foodModel.getCategoryId() == 1) {
                rbCheckFood.setChecked(true);
            } else {
                rbCheckBeverage.setChecked(true);
            }
        }

        imageView.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.REQUEST_CODE_FOLDER);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
            }
        });


        btnSave.setOnClickListener(view -> {
            saveOrUpdateFood();
        });

        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(FoodActivity.this, ProfileActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
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

    public void saveOrUpdateFood() {
        final String foodName = etFoodName.getText().toString();
        final String foodDescription = etFoodDescription.getText().toString();
        final String price = etPrice.getText().toString();
        if (!rbCheckFood.isChecked() && !rbCheckBeverage.isChecked()) {
            Toast.makeText(this, getString(R.string.no_choose_product_type), Toast.LENGTH_SHORT).show();
        } else if (foodName.isEmpty()) {
            etFoodName.setError(getString(R.string.enter_food_name));
        } else if (foodDescription.isEmpty()) {
            etFoodDescription.setError(getString(R.string.enter_food_description));
        } else if (price.isEmpty()) {
            etPrice.setError(getString(R.string.enter_food_price));
        } else {
            Integer categoryId = rbCheckFood.isChecked() ? 1 : 2;
            if (foodModel == null) {
                FoodModel addFood = new FoodModel(null, Utils.convertImageViewToBytes(imageView),
                        foodName, foodDescription, Float.parseFloat(price), Common.currentUserModel.getId(),
                        categoryId);
                final Long insertFood = foodQuery.insert(addFood);
                if (insertFood != null) {
                    Toast.makeText(this, R.string.insert_food_successfully, Toast.LENGTH_SHORT).show();
                }
            } else {
                FoodModel updateFood = new FoodModel(foodModel.getId(), Utils.convertImageViewToBytes(imageView),
                        foodName, foodDescription, Float.parseFloat(price), Common.currentUserModel.getId(),
                        categoryId);
                final Integer updateFoodModel = foodQuery.update(updateFood);
                if (updateFoodModel != null) {
                    Toast.makeText(this, getString(R.string.update_food_successfully), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void binding() {
        imageView = findViewById(R.id.imageView);
        rbCheckFood = findViewById(R.id.rbCheckFood);
        rbCheckBeverage = findViewById(R.id.rbCheckBeverage);
        etFoodName = findViewById(R.id.etFoodName);
        etFoodDescription = findViewById(R.id.etFoodDescription);
        etPrice = findViewById(R.id.etPrice);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}