package hcmute.edu.vn.foody_10;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.material.tabs.TabLayout;

import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Constants;
import hcmute.edu.vn.foody_10.common.Utils;
import hcmute.edu.vn.foody_10.database.Database;
import hcmute.edu.vn.foody_10.foods.FindBeverageFragment;
import hcmute.edu.vn.foody_10.foods.FindFoodFragment;
import hcmute.edu.vn.foody_10.login.LoginActivity;
import hcmute.edu.vn.foody_10.orders.FindOrdersFragment;
import hcmute.edu.vn.foody_10.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView ivProfile;
    private TextView tvUsername, tvState;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean doubleBackPressed = false;
    private SearchView searchView;
    public static Database database;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding();

        database = new Database(this, "foody_test1.sqlite", null, 1);
        createTableUser();

        setViewPager();
    }

    private void createTableUser() {
        database.QueryData("create table if not exists user (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name varchar(255)," +
                "email varchar(255) unique, " +
                "password varchar(20)," +
                "avatar blob);");
    }


    private void binding() {
        tabLayout = findViewById(R.id.tabMain);
        viewPager = findViewById(R.id.vpMain);

    }

    private void setViewPager() {
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_food));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_beverage));
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.tab_order));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(2);
        Adapter adapter = new Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    private void checkLoginUser() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Yes");
        dialog.setMessage("You need to login to perform this function?");
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
                } else if (fragment instanceof FindOrdersFragment) {
                    ((FindOrdersFragment) fragment).getFindOrderAdapter().getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment fragment = getSupportFragmentManager().getFragments().get(viewPager.getCurrentItem());
                if (fragment instanceof FindFoodFragment) {
                    ((FindFoodFragment) fragment).getFindFoodAdapter().getFilter().filter(newText);
                } else if (fragment instanceof FindBeverageFragment) {
                    ((FindBeverageFragment) fragment).getFindFoodAdapter().getFilter().filter(newText);
                } else if (fragment instanceof FindOrdersFragment) {
                    ((FindOrdersFragment) fragment).getFindOrderAdapter().getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuProfile) {
            if (Common.currentUser != null) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                checkLoginUser();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
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
                    return new FindFoodFragment();
                case 1:
                    return new FindBeverageFragment();
                case 2:
                    return new FindOrdersFragment();
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
        if (Common.currentUser != null) {
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
                tvUsername.setText(Common.currentUser.getName());
                tvState.setText("online");
                byte[] image = Common.currentUser.getAvatar();
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ivProfile.setImageBitmap(bitmap);
            }
        }
    }

}