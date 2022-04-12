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

public class FindFoodFragment extends Fragment {
    private FindFoodAdapter findFoodAdapter;
    private List<FoodModel> foodModels;
    private View progressBar;
    private TextView tvEmptyFriendsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_find_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFindFoods = view.findViewById(R.id.rvFindFoods);
        progressBar = view.findViewById(R.id.progressBar);
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
        foodModels.add(new FoodModel(1, R.drawable.banh_trang, "Bánh tráng", "Bánh tráng nướng", 123456.79f));
        foodModels.add(new FoodModel(2, R.drawable.com_chay, "Cơm cháy", "Cơm cháy chà bông", 12.2f));
        foodModels.add(new FoodModel(3, R.drawable.banh_plan, "Bánh flan", "Bánh plan", 12.2f));
        foodModels.add(new FoodModel(4, R.drawable.hu_tieu, "Hủ tiếu", "Hủ tiếu", 12.2f));

    }
}
