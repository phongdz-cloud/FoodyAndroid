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
import hcmute.edu.vn.foody_10.Common.CreditCardType;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class UserModelSQLTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IUserQuery userQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
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
        final String phone = "0375489103";
        final String address = "Bà Rịa Vũng Tàu";
        UserModel userModel = new UserModel(null, name, email, password, null, phone, address, CreditCardType.EMPTY.name());
        Long insertUser = userQuery.insert(userModel);
        Assert.assertNotNull(insertUser);
    }

    @Test
    public void testFindByIdUser() {
        UserModel userModel = userQuery.findById(1);
        Assert.assertNotNull(userModel);
    }

    @Test
    public void testFindByPhone() {
        UserModel userModel = userQuery.findByPhone("0375489103");
        Assert.assertNotNull(userModel);
    }

    @Test
    public void testFindByEmailUser() {
        UserModel userModel = userQuery.findByEmail("john1@gmail.com");
        Assert.assertNotNull(userModel);
    }

    @Test
    public void testFindByUserEmailAndPasswordUser() {
        final String email, password;
        email = "john@gmail.com";
        password = "1234567";
        UserModel userModelEmailAndPassword = userQuery.findByUserEmailAndPassword(email, password);
        Assert.assertNotNull(userModelEmailAndPassword);
    }

    @Test
    public void testUpdatePasswordUser() {
        UserModel userModel = userQuery.findById(3);
        Assert.assertNotNull(userModel);
        userModel.setPassword("1234567"); // 123456 -> 1234567
        Integer integer = userQuery.updatePassword(userModel);
        Assert.assertNotNull(integer);
    }

    @Test
    public void testUpdateOnlyPhoto() {
        UserModel userModel = userQuery.findById(3);
        Assert.assertNotNull(userModel);
        userModel.setAvatar(null);
        Integer integer = userQuery.updateOnlyPhoto(userModel);
        Assert.assertNotNull(integer);
    }

    @Test
    public void testUpdatePhotoAndName() {
        UserModel userModel = userQuery.findById(3);
        Assert.assertNotNull(userModel);
        userModel.setAvatar(null);
        userModel.setName("john test");
        Integer integer = userQuery.updatePhotoAndInfo(userModel);
        Assert.assertNotNull(integer);
    }

    @Test
    public void testCreateTableUser() {
        database.QueryData("create table if not exists user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name varchar(255)," +
                "email varchar(255) unique, " +
                "password varchar(20)," +
                "avatar blob," +
                "phone varchar(20)," +
                "address varchar(255)," +
                "credit_card varchar(50)" +
                ")");
    }


    @Test
    public void onCreate() {
    }
}