package hcmute.edu.vn.foody_10;

import android.view.View;
import android.widget.ImageView;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import hcmute.edu.vn.foody_10.Activity.MainActivity;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;

public class FoodSQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IFoodQuery foodQuery = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        foodQuery = FoodQuery.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void testLaunch() {
        View view = mainActivity.findViewById(R.id.vpMain);

        Assert.assertNotNull(view);
        Assert.assertNotNull(database);
        Assert.assertNotNull(foodQuery);
    }

    @Test
    public void testCreateFood() {
        ImageView imageView = new ImageView(mainActivity);
        imageView.setImageResource(R.drawable.chan_ga_thanh_map);

        FoodModel foodModel = new FoodModel();
        foodModel.setPhotoFood(Utils.convertImageViewToBytes(imageView));
        foodModel.setFoodName("Chân gà rút xương ngâm sả tắc nhỏ");
        foodModel.setFoodDescription("Hộp ~250gr chân gà rút xương kèm nước chấm siêu ngon");
        foodModel.setPrice(210000F);
        foodModel.setCategoryId(1);
        foodModel.setUserId(1);

        Long foodInsert = foodQuery.insert(foodModel);
        Assert.assertNotNull(foodInsert);
    }

    @Test
    public void testUpdateFood() {
        Integer foodId = 1;
        final FoodModel foodModel = foodQuery.findById(foodId);
        Assert.assertNotNull(foodModel);
        foodModel.setFoodName("Trà cam phúc long");
        foodModel.setFoodDescription("Trà cam phúc long HỒ CHÍ MINH");

        final Integer updateFood = foodQuery.update(foodModel);
        Assert.assertTrue(updateFood > 0);
    }



    @Test
    public void testFindByIdFood() {
        Integer id = 1;
        FoodModel foodQueryById = foodQuery.findById(id);
        Assert.assertNotNull(foodQueryById);
    }

    @Test
    public void testFindFoodsByCodeCategory() {
        final String code = "DU";
        List<FoodModel> foods = foodQuery.findFoodByCodeCategory(code);
        Assert.assertTrue(foods.size() > 0);
    }

    @Test
    public void testFindFoodByUserId() {
        final Integer userId = 1;
        final List<FoodModel> foods = foodQuery.findFoodByUser(userId);
        Assert.assertTrue(foods.size() > 0);
    }



    @Test
    public void testCreateTableFood() {
        String sql = "create table if not exists food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "photo_food blob, " +
                "food_name varchar(255), " +
                "food_description varchar(255), " +
                "price float, " +
                "user_id INTEGER," +
                "category_id INTEGER" +
                ")";

        database.QueryData("create table if not exists food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "photo_food blob, " +
                "food_name varchar(255), " +
                "food_description varchar(255), " +
                "price float, " +
                "user_id INTEGER," +
                "category_id INTEGER" +
                ")");


    }
}
