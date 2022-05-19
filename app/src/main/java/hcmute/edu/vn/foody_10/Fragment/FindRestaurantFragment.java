package hcmute.edu.vn.foody_10.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.Adapter.FindRestaurantAdapter;
import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Database.IRestaurantQuery;
import hcmute.edu.vn.foody_10.Database.RestaurantQuery;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;

public class FindRestaurantFragment extends Fragment {
    private FindRestaurantAdapter findRestaurantAdapter;
    private List<RestaurantModel> restaurants;
    private IRestaurantQuery restaurantQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFindRestaurant = view.findViewById(R.id.rvFindRestaurant);
        TextView tvEmptyRestaurantList = view.findViewById(R.id.tvEmptyRestaurantList);
        rvFindRestaurant.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        restaurantQuery = RestaurantQuery.getInstance();
        restaurants = new ArrayList<>();

        findRestaurantAdapter = new FindRestaurantAdapter(getActivity(), restaurants);


        rvFindRestaurant.setAdapter(findRestaurantAdapter);
        dataRestaurant();
        if (restaurants.size() > 0) {
            tvEmptyRestaurantList.setVisibility(View.GONE);
        }
    }

    private void dataRestaurant() {
        restaurants.clear();
        final List<RestaurantModel> res = restaurantQuery.findAll();
        if (res != null) {
            restaurants.addAll(res);
            findRestaurantAdapter.notifyDataSetChanged();
        }
    }

    public FindRestaurantAdapter getFindRestaurantAdapter() {
        return findRestaurantAdapter;
    }
}
