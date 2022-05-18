package hcmute.edu.vn.foody_10;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import hcmute.edu.vn.foody_10.Activity.MainActivity;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Database.CategoryQuery;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.ICategoryQuery;
import hcmute.edu.vn.foody_10.Model.CategoryModel;

public class CategorySQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private ICategoryQuery categoryQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        categoryQuery = CategoryQuery.getInstance();
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
        Assert.assertNotNull(categoryQuery);
    }

    @Test
    public void testCreateCategory() {
        CategoryModel categoryModel = new CategoryModel(null, "Đồ Uống", "DU");
        Long insert = categoryQuery.insert(categoryModel);

        Assert.assertNotNull(insert);
    }

    @Test
    public void testFindByCode() {
        CategoryModel testDb = categoryQuery.findByCode("DA");
        Assert.assertNotNull(testDb);
    }

    @Test
    public void testFindById() {
        Integer id = 2;
        CategoryModel categoryQueryById = categoryQuery.findById(id);
        Assert.assertNotNull(categoryQueryById);
    }


    @Test
    public void testCreateTableCategory() {
        database.QueryData("create table if not exists category(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name varchar(50), " +
                "code varchar(20)" +
                ")");
    }
}
