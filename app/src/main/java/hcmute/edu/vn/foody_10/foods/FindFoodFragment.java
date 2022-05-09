package hcmute.edu.vn.foody_10.foods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.database.FoodQuery;
import hcmute.edu.vn.foody_10.database.IFoodQuery;
import hcmute.edu.vn.foody_10.models.FoodModel;

public class FindFoodFragment extends Fragment {
    private FindFoodAdapter findFoodAdapter;
    private List<FoodModel> foodModels;
    private TextView tvEmptyFriendsList;
    private IFoodQuery foodQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_find_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodQuery = FoodQuery.getInstance();
        RecyclerView rvFindFoods = view.findViewById(R.id.rvFindFoods);
        tvEmptyFriendsList = view.findViewById(R.id.tvEmptyFoodList);
        rvFindFoods.setLayoutManager(new LinearLayoutManager(getActivity()));

        foodModels = new ArrayList<>();
        findFoodAdapter = new FindFoodAdapter(getActivity(), foodModels);
        rvFindFoods.setAdapter(findFoodAdapter);

        tvEmptyFriendsList.setVisibility(View.VISIBLE);

        dataFood();
        if (foodModels.size() > 0) {
            tvEmptyFriendsList.setVisibility(View.GONE);
        }
        findFoodAdapter.notifyDataSetChanged();
    }

    public FindFoodAdapter getFindFoodAdapter() {
        return findFoodAdapter;
    }

    public void dataFood() {
        List<FoodModel> results = foodQuery.findFoodByCodeCategory("DA");
        if (results != null)
            foodModels.addAll(results);
    }
}
