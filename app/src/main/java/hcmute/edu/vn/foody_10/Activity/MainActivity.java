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
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Fragment.FindBeverageFragment;
import hcmute.edu.vn.foody_10.Fragment.FindFoodFragment;
import hcmute.edu.vn.foody_10.Fragment.FindRestaurantFragment;
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
                        if(Common.currentUserModel != null){
                            Intent intentNotification = new Intent(MainActivity.this, NotificationActivity.class);
                            startActivity(intentNotification);
                        }else{
                            checkLoginUser();
                        }
                        break;
                    case R.id.mnuCart:
                        if(Common.currentUserModel != null){
                            Intent intentCart = new Intent(MainActivity.this, PurchaseListUserActivity.class);
                            startActivity(intentCart);
                        }else{
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


}