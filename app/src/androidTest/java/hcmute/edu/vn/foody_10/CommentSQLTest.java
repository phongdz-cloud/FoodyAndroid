package hcmute.edu.vn.foody_10;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import hcmute.edu.vn.foody_10.activities.MainActivity;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.database.CommentQuery;
import hcmute.edu.vn.foody_10.database.Database;
import hcmute.edu.vn.foody_10.database.FoodQuery;
import hcmute.edu.vn.foody_10.database.ICommentQuery;
import hcmute.edu.vn.foody_10.database.IFoodQuery;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.models.CommentModel;
import hcmute.edu.vn.foody_10.models.FoodModel;
import hcmute.edu.vn.foody_10.models.User;

public class CommentSQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private ICommentQuery commentQuery;

    private IUserQuery userQuery;

    private IFoodQuery foodQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        commentQuery = CommentQuery.getInstance();
        userQuery = UserQuery.getInstance();
        foodQuery = FoodQuery.getInstance();
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
        Assert.assertNotNull(commentQuery);
        Assert.assertNotNull(userQuery);
        Assert.assertNotNull(foodQuery);
    }

    @Test
    public void testCreateComment() {
        Integer userId = 1;
        Integer foodId = 1;
        String msgTest = "Món này ngon lắm";
        Long dateTime = System.currentTimeMillis();

        final User user = userQuery.findById(userId);
        Assert.assertNotNull(user);
        final FoodModel foodQueryById = foodQuery.findById(foodId);
        Assert.assertNotNull(foodQueryById);

        CommentModel commentModel = new CommentModel(null, msgTest, dateTime, user.getId(),
                foodQueryById.getId());

        final Long commentInsert = commentQuery.insert(commentModel);

        Assert.assertNotNull(commentInsert);
    }

    @Test
    public void testFindByIdComment() {
        Integer commentId = 1;
        final CommentModel commentModel = commentQuery.findById(commentId);

        Assert.assertNotNull(commentModel);
    }

    @Test
    public void testDeleteComment(){
        Integer commentId = 1;
        final CommentModel commentModel = commentQuery.findById(commentId);
        Assert.assertNotNull(commentModel);
        final Integer deleteComment = commentQuery.delete(commentModel.getId());
        Assert.assertNotNull(deleteComment);

    }

    @Test
    public void testUpdateComment() {
        Integer commentId = 1;
        final CommentModel commentModel = commentQuery.findById(commentId);
        Assert.assertNotNull(commentModel);
        commentModel.setMessage("Test update comment");
        commentModel.setDateTime(System.currentTimeMillis());
        final Integer updateComment = commentQuery.update(commentModel);
        Assert.assertNotNull(updateComment);
    }

    @Test
    public void testFindCommentsByFood() {
        Integer foodId = 1;
        final List<CommentModel> commentByFood = commentQuery.findCommentByFood(foodId);
        Assert.assertTrue(commentByFood.size() > 0);
    }

    @Test
    public void testFindCommentByFoodAndUser() {
        Integer userId = 1;
        Integer foodId = 1;
        final List<CommentModel> comments = commentQuery.findCommentByUserAndFood(userId, foodId);
        Assert.assertTrue(comments.size() > 0);
    }


    @Test
    public void testCreateTableCategory() {
        database.QueryData("create table if not exists comment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "message varchar(255), " +
                "date_time INTEGER, " +
                "user_id INTEGER, " +
                "product_id INTEGER" +
                ")");
    }
}
