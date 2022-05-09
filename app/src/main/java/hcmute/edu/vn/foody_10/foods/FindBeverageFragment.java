package hcmute.edu.vn.foody_10.foods;

import android.annotation.SuppressLint;
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

public class FindBeverageFragment extends Fragment {
    private FindFoodAdapter findFoodAdapter;
    private List<FoodModel> foodModels;
    private View progressBar;
    private TextView tvEmptyFriendsList;
    private IFoodQuery foodQuery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_beverage, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFindBeverages = view.findViewById(R.id.rvFindBeverages);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyFriendsList = view.findViewById(R.id.tvEmptyFoodList);
        rvFindBeverages.setLayoutManager(new LinearLayoutManager(getActivity()));
        foodQuery = FoodQuery.getInstance();

        foodModels = new ArrayList<>();
        findFoodAdapter = new FindFoodAdapter(getActivity(), foodModels);
        rvFindBeverages.setAdapter(findFoodAdapter);

        tvEmptyFriendsList.setVisibility(View.VISIBLE);

        dataBeverage();
        if (foodModels.size() > 0) {
            tvEmptyFriendsList.setVisibility(View.GONE);
        }

        findFoodAdapter.notifyDataSetChanged();
    }

    public void dataBeverage() {
        List<FoodModel> results = foodQuery.findFoodByCodeCategory("DU");
        if (results != null)
            foodModels.addAll(results);
    }


    public FindFoodAdapter getFindFoodAdapter() {
        return findFoodAdapter;
    }
}
