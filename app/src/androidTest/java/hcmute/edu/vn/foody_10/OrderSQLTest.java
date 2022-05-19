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
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Database.IOrderQuery;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.OrderQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.OrderModel;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class OrderSQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IOrderQuery orderQuery = null;

    private IUserQuery userQuery = null;

    private IFoodQuery foodQuery = null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        orderQuery = OrderQuery.getInstance();
        userQuery = UserQuery.getInstance();
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
        Assert.assertNotNull(orderQuery);
        Assert.assertNotNull(userQuery);
        Assert.assertNotNull(foodQuery);
    }


    @Test
    public void testCreateOrder() {
        Integer foodId = 1;
        Integer userId = 1;

        final FoodModel foodQueryById = foodQuery.findById(foodId);
        Assert.assertNotNull(foodQueryById);
        final UserModel userModel = userQuery.findById(userId);
        Assert.assertNotNull(userModel);
        OrderModel orderModel = new OrderModel();
        orderModel.setPhotoFood(foodQueryById.getPhotoFood());
        orderModel.setCount(1);
        orderModel.setFoodName(foodQueryById.getFoodName());
        orderModel.setFoodDescription(foodQueryById.getFoodDescription());
        orderModel.setPrice(foodQueryById.getPrice());
        orderModel.setProductId(foodQueryById.getCategoryId());
        orderModel.setUserId(userModel.getId());
        final Long orderInsert = orderQuery.insert(orderModel);
        Assert.assertNotNull(orderInsert);
    }

    @Test
    public void testFindByIdOrder() {
        final Integer orderId = 2;
        final OrderModel orderQueryById = orderQuery.findById(orderId);
        Assert.assertNotNull(orderQueryById);
    }

    @Test
    public void testFindByProductId() {
        final Integer productId = 1;
        final OrderModel byProductId = orderQuery.findByProductId(productId);
        Assert.assertNotNull(byProductId);
    }

    @Test
    public void deleteOrderById() {
        final Integer orderId = 1;
        final OrderModel orderQueryById = orderQuery.findById(orderId);
        Assert.assertNotNull(orderQueryById);
        final Integer integer = orderQuery.deleteOrder(orderQueryById.getId());
        Assert.assertNotNull(integer);
        final OrderModel deletedOrder = orderQuery.findById(integer);
        Assert.assertNull(deletedOrder);
    }

    @Test
    public void testOrderUpdateCount() {
        final Integer orderId = 1;
        final OrderModel orderQueryById = orderQuery.findById(orderId);
        Assert.assertNotNull(orderQueryById);
        orderQueryById.setCount(orderQueryById.getCount() + 1);
        final Integer updateCount = orderQuery.updateCount(orderQueryById);
        Assert.assertNotNull(updateCount);
    }


    @Test
    public void testFindAllOrder() {
        final List<OrderModel> orders = orderQuery.findAll();
        Assert.assertTrue(orders.size() > 0);
    }

    @Test
    public void testFindOrderByUserId() {
        final Integer userId = 1;
        final List<OrderModel> orders = orderQuery.findOrderByUserId(userId);
        Assert.assertTrue(orders.size() > 0);
    }

    @Test
    public void testFindOrderByProductIdAndUserId() {
        final Integer foodId = 1;
        final Integer userId = 1;

        final OrderModel orderModel = orderQuery.findByProductIdAndUserId(foodId, userId);
        Assert.assertNotNull(orderModel);
    }

    @Test
    public void testCreateTableOrder() {
        database.QueryData("create table if not exists orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "photo_food blob, " +
                "count INTEGER, " +
                "food_name varchar(255), " +
                "food_description varchar(255), " +
                "price float, " +
                "product_id INTEGER, " +
                "user_id INTEGER" +
                ")");
    }
}
