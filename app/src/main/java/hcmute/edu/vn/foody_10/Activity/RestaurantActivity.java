package hcmute.edu.vn.foody_10.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Database.IRestaurantQuery;
import hcmute.edu.vn.foody_10.Database.RestaurantQuery;
import hcmute.edu.vn.foody_10.Adapter.FindFoodAdapter;
import hcmute.edu.vn.foody_10.Model.FoodModel;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView rvFoodOfRestaurant;
    private FindFoodAdapter findFoodAdapter;
    private List<FoodModel> foods;
    private IFoodQuery foodQuery;
    private IRestaurantQuery restaurantQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        foodQuery = FoodQuery.getInstance();
        restaurantQuery = RestaurantQuery.getInstance();
        rvFoodOfRestaurant = findViewById(R.id.rvFoodOfRestaurant);

        foods = new ArrayList<>();
        findFoodAdapter = new FindFoodAdapter(this, foods);

        rvFoodOfRestaurant.setLayoutManager(new LinearLayoutManager(this));
        rvFoodOfRestaurant.setAdapter(findFoodAdapter);

        loadFood();
    }


    private void loadFood() {
        foods.clear();
        final List<FoodModel> findAllFood = foodQuery.findAll();
        if (findAllFood != null) {
            foods.addAll(findAllFood);
            findFoodAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSupportActionModeFinished(@NonNull ActionMode mode) {
        super.onSupportActionModeFinished(mode);
        finish();
    }
}