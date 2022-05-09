package hcmute.edu.vn.foody_10.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_10.R;

public class FoodActivity extends AppCompatActivity {
    private ImageView imageView;
    private RadioGroup rgCheckFood;
    private RadioButton rbCheckFood, rbCheckBeverage;
    private EditText etFoodName, etFoodDescription, etPrice;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }

    private void binding() {
        imageView = findViewById(R.id.imageView);
        rgCheckFood = findViewById(R.id.rgCheckFood);
        rbCheckFood = findViewById(R.id.rbCheckFood);
        rbCheckBeverage = findViewById(R.id.rbCheckBeverage);
        etFoodName = findViewById(R.id.etFoodName);
        etFoodDescription = findViewById(R.id.etFoodDescription);
        etPrice = findViewById(R.id.etPrice);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}