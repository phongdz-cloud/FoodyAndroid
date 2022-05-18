package hcmute.edu.vn.foody_10.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Database.IRestaurantQuery;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.RestaurantQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Adapter.FindFoodAdapter;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class DetailRestaurantActivity extends AppCompatActivity {
    private ImageView ivRestaurant;
    private TextView tvRestaurantName, tvDateTime, tvAddress, tvRestaurantCategory, tvRangePrice;
    private IUserQuery userQuery;
    private RecyclerView rvListFoodRestaurant;
    private FindFoodAdapter foodAdapter;
    private List<FoodModel> foods;
    private IFoodQuery foodQuery;
    private RestaurantModel restaurantModel;
    private IRestaurantQuery restaurantQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);
        binding();
        getSupportActionBar().hide();
        userQuery = UserQuery.getInstance();
        foodQuery = FoodQuery.getInstance();
        restaurantQuery = RestaurantQuery.getInstance();
        restaurantModel = (RestaurantModel) getIntent().getSerializableExtra("detailRestaurant");
        int userId = getIntent().getIntExtra("userId", 0);
        if (restaurantModel != null || userId != 0) {
            final UserModel user = userQuery
                    .findById(restaurantModel != null ? restaurantModel.getUserId() : userId);
            if (restaurantModel == null) {
                restaurantModel = restaurantQuery.findRestaurantByUserId(user.getId());
            }
            if (user != null) {
                Picasso.get()
                        .load(restaurantModel.getRestaurantPhoto())
                        .into(ivRestaurant);
                tvRestaurantName.setText(restaurantModel.getName());
                tvDateTime.setText(restaurantModel.getDate_time());

                tvAddress.setText(user.getAddress());
                tvRestaurantCategory.setText(restaurantModel.getDescription());
                tvRangePrice.setText(restaurantModel.getRangePrice());
            }
        }

        foods = new ArrayList<>();
        foodAdapter = new FindFoodAdapter(this, foods);

        // Làm tới đây
        rvListFoodRestaurant.setLayoutManager(new LinearLayoutManager(this));
        rvListFoodRestaurant.setAdapter(foodAdapter);

        loadDataFood();
    }

    private void loadDataFood() {
        final List<FoodModel> foodByUser = foodQuery.findFoodByUser(restaurantModel.getUserId());
        if (foodByUser != null) {
            foods.addAll(foodByUser);
            foodAdapter.notifyDataSetChanged();
        }
    }

    private void binding() {
        ivRestaurant = findViewById(R.id.ivRestaurant);
        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvAddress = findViewById(R.id.tvAddress);
        tvRestaurantCategory = findViewById(R.id.tvRestaurantCategory);
        tvRangePrice = findViewById(R.id.tvRangePrice);
        rvListFoodRestaurant = findViewById(R.id.rvListFoodRestaurant);
    }

}