package hcmute.edu.vn.foody_10.Activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.CategoryQuery;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.ICategoryQuery;
import hcmute.edu.vn.foody_10.Database.IRestaurantQuery;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.RestaurantQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Fragment.FindBeverageFragment;
import hcmute.edu.vn.foody_10.Fragment.FindFoodFragment;
import hcmute.edu.vn.foody_10.Fragment.FindRestaurantFragment;
import hcmute.edu.vn.foody_10.Model.CategoryModel;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;
import hcmute.edu.vn.foody_10.Model.UserModel;
import hcmute.edu.vn.foody_10.R;

public class MainActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextView tvUsername, tvState;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackPressed = false;
    private SearchView searchView;
    public static Database database;
    private ActionBar actionBar;
    private Menu menu;
    private BottomNavigationView bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding();


        database = new Database(this, Constants.DATABASE, null, 1);
        setViewPager();


        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuHome:
                        break;
                    case R.id.mnuNotification:
                        if (Common.currentUserModel != null) {
                            Intent intentNotification = new Intent(MainActivity.this, NotificationActivity.class);
                            startActivity(intentNotification);
                        } else {
                            checkLoginUser();
                        }
                        break;
                    case R.id.mnuCart:
                        if (Common.currentUserModel != null) {
                            Intent intentCart = new Intent(MainActivity.this, PurchaseListUserActivity.class);
                            startActivity(intentCart);
                        } else {
                            checkLoginUser();
                        }
                        break;
                    case R.id.mnuProfile:
                        if (Common.currentUserModel != null) {
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        } else {
                            checkLoginUser();
                        }
                        break;
                }
                return false;
            }
        });
        loadDatabaseTable();
    }

    private void binding() {
        tabLayout = findViewById(R.id.tabMain);
        viewPager = findViewById(R.id.vpMain);
        bnvMain = findViewById(R.id.bnvMain);
    }

    private void setViewPager() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_restaurant));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_food));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_beverage));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(2);
        Adapter adapter = new Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void checkLoginUser() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Thông báo");
        dialog.setMessage("Bạn cần đăng nhập để thực hiện chức năng này");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.mnuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment fragment = getSupportFragmentManager().getFragments().get(viewPager.getCurrentItem());
                if (fragment instanceof FindFoodFragment) {
                    ((FindFoodFragment) fragment).getFindFoodAdapter().getFilter().filter(query);
                } else if (fragment instanceof FindBeverageFragment) {
                    ((FindBeverageFragment) fragment).getFindFoodAdapter().getFilter().filter(query);
                } else if (fragment instanceof FindRestaurantFragment) {
                    ((FindRestaurantFragment) fragment).getFindRestaurantAdapter().getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getSupportFragmentManager().getFragments().get(viewPager.getCurrentItem() + 1);
                if (fragment instanceof FindFoodFragment) {
                    ((FindFoodFragment) fragment).getFindFoodAdapter().getFilter().filter(newText);
                } else if (fragment instanceof FindBeverageFragment) {
                    ((FindBeverageFragment) fragment).getFindFoodAdapter().getFilter().filter(newText);
                } else if (fragment instanceof FindRestaurantFragment) {
                    ((FindRestaurantFragment) fragment).getFindRestaurantAdapter().getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        if (tabLayout.getSelectedTabPosition() > 0) {
            tabLayout.selectTab(tabLayout.getTabAt(0));
        } else {
            if (doubleBackPressed) {
                finishAffinity();
            } else {
                doubleBackPressed = true;
                Toast.makeText(this, R.string.press_back_to_exit, Toast.LENGTH_SHORT).show();
                // delay
                android.os.Handler handler = new android.os.Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackPressed = false;
                    }
                }, 2000);
            }
        }
    }

    class Adapter extends FragmentPagerAdapter {

        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FindRestaurantFragment();
                case 1:
                    return new FindFoodFragment();
                case 2:
                    return new FindBeverageFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabLayout.getTabCount();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_USER_STATE, MODE_PRIVATE);
        Utils.getPreferences(userPreferences);
        if (Common.currentUserModel != null) {
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("");
                ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_action_mode, null);
                ivProfile = actionBarLayout.findViewById(R.id.ivProfile);
                tvUsername = actionBarLayout.findViewById(R.id.tvUserName);
                tvState = actionBarLayout.findViewById(R.id.tvUserStatus);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setElevation(0);

                actionBar.setCustomView(actionBarLayout);
                actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);
                tvUsername.setText(Common.currentUserModel.getName());
                tvState.setText("online");
                Glide.with(this)
                        .load(Common.currentUserModel.getAvatar())
                        .placeholder(R.drawable.default_profile)
                        .error(R.drawable.default_profile)
                        .into(ivProfile);
//                }
            }
        }
    }

    private void loadDatabaseTable() {
        IRestaurantQuery restaurantQuery = RestaurantQuery.getInstance();
        if (restaurantQuery.findAll() == null) {
            // CREATE TABLE USER
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

            // CREATE TABLE RESTAURANT
            database.QueryData("create table if not exists restaurant(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, " +
                    "restaurant_photo blob, " +
                    "name varchar(255), " +
                    "description varchar(255), " +
                    "date_time varchar(255), " +
                    "range_price varchar(255)" +
                    ")");

            // CREATE TABLE CATEGORY
            database.QueryData("create table if not exists category(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name varchar(50), " +
                    "code varchar(20)" +
                    ")");

            // CREATE TABLE FOOD
            database.QueryData("create table if not exists food (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "photo_food blob, " +
                    "food_name varchar(255), " +
                    "food_description varchar(255), " +
                    "price float, " +
                    "user_id INTEGER," +
                    "category_id INTEGER" +
                    ")");

            // CREATE TABLE COMMENT
            database.QueryData("create table if not exists comment(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "message varchar(255), " +
                    "date_time INTEGER, " +
                    "user_id INTEGER, " +
                    "product_id INTEGER" +
                    ")");

            // CREATE  TABLE ORDER
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

            //CREATE RECEIPT
            database.QueryData("create table if not exists receipt(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, " +
                    "product_id INTEGER, " +
                    "total_count INTEGER, " +
                    "total_price float, " +
                    "code varchar(255)" +
                    ")");

            IUserQuery userQuery = UserQuery.getInstance();
            IRestaurantQuery resQuery = RestaurantQuery.getInstance();
            ICategoryQuery categoryQuery = CategoryQuery.getInstance();
            // Thêm category gồm nước và thức ăn

            // Category Thức ăn
            CategoryModel categoryFood = new CategoryModel();
            categoryFood.setName("Đồ ăn");
            categoryFood.setCode("DA");

            categoryQuery.insert(categoryFood);

            //Category nước uống
            CategoryModel categoryBeverage = new CategoryModel();
            categoryBeverage.setName("Đồ uống");
            categoryBeverage.setCode("DU");
            categoryQuery.insert(categoryBeverage);

            // ********************* Quán nước Phúc Long *********************

            ImageView imageDefaultUser = new ImageView(this);
            imageDefaultUser.setImageResource(R.drawable.default_profile);
            //Tạo User 1 với nhà hàng 1 (Phúc Long)
            UserModel userPhucLong = new UserModel();
            userPhucLong.setName("Thanh Tuyền");
            userPhucLong.setEmail("thanhtuyen@gmail.com");
            userPhucLong.setPassword("123456");
            userPhucLong.setAvatar(Utils.convertImageViewToBytes(imageDefaultUser));
            userPhucLong.setAddress("Tầng Trệt,  Tầng G Crescent Mall, 101 Tôn Dật Tiên, P. Tân Phú, Quận 7, TP. HCM");
            userPhucLong.setPhone("012345678");

            ImageView imageViewPhucLong = new ImageView(this);
            imageViewPhucLong.setImageResource(R.drawable.phuclong);
            userQuery.insert(userPhucLong); // id = 1

            RestaurantModel restaurantPhucLong = new RestaurantModel();
            restaurantPhucLong.setUserId(1);
            restaurantPhucLong.setRestaurantPhoto(Utils.convertImageViewToBytes(imageViewPhucLong));
            restaurantPhucLong.setName("Phúc Long 42 Ngô Đức Kế");
            restaurantPhucLong.setDescription("Café/Dessert-Món Việt- Sinh viên, Nhóm hội");
            restaurantPhucLong.setDate_time("08:00 - 20:45 ");
            restaurantPhucLong.setRangePrice("25.000đ - 77.000đ");
            resQuery.insert(restaurantPhucLong);

            // ********************* Quán ăn ChangHi *********************

            UserModel userChangHi = new UserModel();
            userChangHi.setName("Chang Hi");
            userChangHi.setEmail("changhi@gmail.com");
            userChangHi.setPassword("123456");
            userChangHi.setAvatar(Utils.convertImageViewToBytes(imageDefaultUser));
            userChangHi.setPhone("0375489103");
            userChangHi.setAddress("107 Nơ Trang Long, P. 11, Quận Bình Thạnh, TP. HCM");

            userQuery.insert(userChangHi); // id = 2

            ImageView imageViewChangHi = new ImageView(this);
            imageViewChangHi.setImageResource(R.drawable.che_chang_hi);

            RestaurantModel restaurantChangHi = new RestaurantModel();
            restaurantChangHi.setUserId(2);
            restaurantChangHi.setRestaurantPhoto(Utils.convertImageViewToBytes(imageViewChangHi));
            restaurantChangHi.setName("Chang Hi - Chè Thốt Nốt, Chè Dừa Non & Chè Xoài Hong Kong");
            restaurantChangHi.setDescription("Quán ăn - Món Việt - Sinh Viên");
            restaurantChangHi.setDate_time("08:00 - 12:00 | 13:00 - 18:00 ");
            restaurantChangHi.setRangePrice("28.000đ - 35.000đ");

            resQuery.insert(restaurantChangHi);

            // ********************* Quán nước ChangHi *********************
            UserModel userGongCha = new UserModel();
            userGongCha.setName("Gong Cha");
            userGongCha.setEmail("gongcha@gmail.com");
            userGongCha.setPassword("123456");
            userGongCha.setAvatar(Utils.convertImageViewToBytes(imageDefaultUser));
            userGongCha.setPhone("099882732");
            userGongCha.setAddress("240 Phan Xích Long, P. 7, Quận Phú Nhuận, TP. HCM");
            userQuery.insert(userGongCha); // id = 3

            ImageView imageViewGongCha = new ImageView(this);
            imageViewGongCha.setImageResource(R.drawable.gongcha);
            RestaurantModel restaurantGongCha = new RestaurantModel();
            restaurantGongCha.setUserId(3);
            restaurantGongCha.setRestaurantPhoto(Utils.convertImageViewToBytes(imageViewGongCha));
            restaurantGongCha.setName("Trà Sữa Gong Cha - Phan Xích Long");
            restaurantGongCha.setDescription("Café/Dessert-Đài Loan- Sinh viên, Cặp đôi");
            restaurantGongCha.setDate_time("09:00 - 22:00 ");
            restaurantGongCha.setRangePrice("40.000đ - 80.000đ");

            resQuery.insert(restaurantGongCha);

            // ********************* Gà rán KFC *********************
            UserModel userHoaiPhong = new UserModel();
            userHoaiPhong.setName("Hoài Phong");
            userHoaiPhong.setEmail("hoaiphong@gmail.com");
            userHoaiPhong.setPassword("123456");
            userHoaiPhong.setAvatar(Utils.convertImageViewToBytes(imageDefaultUser));
            userHoaiPhong.setPhone("0375489103");
            userHoaiPhong.setAddress("255 - 257 Trần Phú, P. Cẩm Tây, Tp. Cẩm Phả, Quảng Ninh");
            userQuery.insert(userHoaiPhong); // id = 4

            ImageView imageViewKFC = new ImageView(this);
            imageViewKFC.setImageResource(R.drawable.ga_ran_kfc);
            RestaurantModel restaurantKFC = new RestaurantModel();
            restaurantKFC.setUserId(4);
            restaurantKFC.setRestaurantPhoto(Utils.convertImageViewToBytes(imageViewKFC));
            restaurantKFC.setName("Gà Rán KFC - Trần Phú");
            restaurantKFC.setDescription("Quán ăn");
            restaurantKFC.setDate_time("09:00 - 22:00");
            restaurantKFC.setRangePrice("50.000đ - 500.000đ");

            resQuery.insert(restaurantKFC);
        }

    }
}

