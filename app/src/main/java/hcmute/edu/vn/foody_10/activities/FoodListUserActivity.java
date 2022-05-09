package hcmute.edu.vn.foody_10.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.foods.FoodOfUserAdapter;
import hcmute.edu.vn.foody_10.models.FoodModel;

public class FoodListUserActivity extends AppCompatActivity {
    TextView tvEmptyFoodList;
    RecyclerView rcFoods;
    FoodOfUserAdapter foodOfUserAdapter;
    List<FoodModel> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_user);
        tvEmptyFoodList = findViewById(R.id.tvEmptyFoodList);
        rcFoods = findViewById(R.id.rcFoods);

        foods = new ArrayList<>();
        foodOfUserAdapter = new FoodOfUserAdapter(this, foods);

        rcFoods.setLayoutManager(new LinearLayoutManager(this));
        rcFoods.setAdapter(foodOfUserAdapter);

        if (foods.size() > 0) {
            tvEmptyFoodList.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}