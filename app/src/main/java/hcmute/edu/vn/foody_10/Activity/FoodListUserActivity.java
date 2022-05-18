package hcmute.edu.vn.foody_10.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Adapter.FoodOfUserAdapter;
import hcmute.edu.vn.foody_10.Model.FoodModel;

public class FoodListUserActivity extends AppCompatActivity {
    private TextView tvEmptyFoodList;
    private RecyclerView rcFoods;
    private FoodOfUserAdapter foodOfUserAdapter;
    private List<FoodModel> foods;
    private IFoodQuery foodQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        foodQuery = FoodQuery.getInstance();
        tvEmptyFoodList = findViewById(R.id.tvEmptyFoodList);
        rcFoods = findViewById(R.id.rcFoods);

        foods = new ArrayList<>();
        foodOfUserAdapter = new FoodOfUserAdapter(this, foods);

        rcFoods.setLayoutManager(new LinearLayoutManager(this));
        rcFoods.setAdapter(foodOfUserAdapter);

        if (foods.size() > 0) {
            tvEmptyFoodList.setVisibility(View.GONE);
        }
    }

    public void loadDataOfUser() {
        foods.clear();
        final List<FoodModel> foodsUser = foodQuery.findFoodByUser(Common.currentUserModel.getId());
        if (foodsUser != null)
            foods.addAll(foodsUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataOfUser();
        if (foods.size() > 0) {
            tvEmptyFoodList.setVisibility(View.GONE);
        }
        foodOfUserAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}