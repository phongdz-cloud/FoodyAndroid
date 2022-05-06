package hcmute.edu.vn.foody_10;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import hcmute.edu.vn.foody_10.database.Database;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.signup.User;

public class UserSQLTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IUserQuery userQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, "foody_test1.sqlite", null, 1);
        userQuery = UserQuery.getInstance();
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
        Assert.assertNotNull(userQuery);
    }

    @Test
    public void testCreateUsers() {
        final String name = "john";
        final String email = "john@gmail.com";
        final String password = "123456";
        User user = new User(null, name, email, password, null);
        Long insertUser = userQuery.insert(user);
        Assert.assertNotNull(insertUser);
    }

    @Test
    public void testFindByIdUser() {
        User user = userQuery.findById(3);
        Assert.assertNotNull(user);
    }

    @Test
    public void testFindByEmailUser() {
        User user = userQuery.findByEmail("john1@gmail.com");
        Assert.assertNotNull(user);
    }

    @Test
    public void testFindByUserEmailAndPasswordUser() {
        final String email, password;
        email = "john@gmail.com";
        password = "1234567";
        User userEmailAndPassword = userQuery.findByUserEmailAndPassword(email, password);
        Assert.assertNotNull(userEmailAndPassword);
    }

    @Test
    public void testUpdatePasswordUser() {
        User user = userQuery.findById(3);
        Assert.assertNotNull(user);
        user.setPassword("1234567"); // 123456 -> 1234567
        Integer integer = userQuery.updatePassword(user);
        Assert.assertNotNull(integer);
    }

    @Test
    public void testUpdateOnlyPhoto() {
        User user = userQuery.findById(3);
        Assert.assertNotNull(user);
        user.setAvatar(null);
        Integer integer = userQuery.updateOnlyPhoto(user);
        Assert.assertNotNull(integer);
    }

    @Test
    public void testUpdatePhotoAndName() {
        User user = userQuery.findById(3);
        Assert.assertNotNull(user);
        user.setAvatar(null);
        user.setName("john test");
        Integer integer = userQuery.updatePhotoAndName(user);
        Assert.assertNotNull(integer);
    }

    @Test
    public void onCreate() {
    }
}