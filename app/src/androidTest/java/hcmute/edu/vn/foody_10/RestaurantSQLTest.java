package hcmute.edu.vn.foody_10;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import hcmute.edu.vn.foody_10.Activity.MainActivity;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.IRestaurantQuery;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.RestaurantQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class RestaurantSQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IRestaurantQuery restaurantQuery;

    private IUserQuery userQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        restaurantQuery = RestaurantQuery.getInstance();
        userQuery = UserQuery.getInstance();
    }

    @After
    public void testDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void testLaunch() {
        View view = mainActivity.findViewById(R.id.vpMain);

        Assert.assertNotNull(view);
        Assert.assertNotNull(database);
        Assert.assertNotNull(restaurantQuery);
    }

    @Test
    public void testFindAllRestaurant() {
        final List<RestaurantModel> restaurantModels = restaurantQuery.findAll();

        Assert.assertTrue(restaurantModels.size() > 0);
    }

    @Test
    public void testCreateRestaurant() {
        // 1: Admin (Thành Mập) , 2: Hoai Phong (Võ Văn Tần)
        final UserModel userModel = userQuery.findById(2);
        Assert.assertNotNull(userModel);

//        RestaurantModel restaurantModel = new RestaurantModel();
//        restaurantModel.setUserId(userModel.getId());
//        restaurantModel.setRestaurantPhoto(R.drawable.chan_ga_thanh_map);
//        restaurantModel.setName("Thành Mập - Chân Gà Rút Xương\nNgâm Sả Tắc - Bạch Đằng - Shop Online");
//        restaurantModel.setDescription("Shop Online - Món Việt");
//        restaurantModel.setDate_time("08:00 - 23:00");
//        restaurantModel.setRangePrice("65,000đ - 210,000đ");

        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setUserId(userModel.getId());
        restaurantModel.setRestaurantPhoto(R.drawable.dingtea);
        restaurantModel.setName("Ding Tea - Võ Văn Tần");
        restaurantModel.setDescription("CAFÉ/DESSERT");
        restaurantModel.setDate_time("08:30 - 22:30");
        restaurantModel.setRangePrice("29,000 - 65,000");

        final Long insertRestaurant = restaurantQuery.insert(restaurantModel);
        Assert.assertNotNull(insertRestaurant);

    }


    @Test
    public void testCreateTableRestaurant() {
        database.QueryData("create table if not exists restaurant(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "restaurant_photo INTEGER, " +
                "name varchar(255), " +
                "description varchar(255), " +
                "date_time varchar(255), " +
                "range_price varchar(255)" +
                ")");
    }
}
