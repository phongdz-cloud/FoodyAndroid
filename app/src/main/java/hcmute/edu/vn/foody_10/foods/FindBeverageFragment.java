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

public class FindBeverageFragment extends Fragment {
    private FindFoodAdapter findFoodAdapter;
    private List<FoodModel> foodModels;
    private View progressBar;
    private TextView tvEmptyFriendsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_beverage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFindBeverages = view.findViewById(R.id.rvFindBeverages);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyFriendsList = view.findViewById(R.id.tvEmptyFoodList);
        rvFindBeverages.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        foodModels.add(new FoodModel(1, R.drawable.nuoc_suoi, "Nước suối", "Nước suối đóng chai", 12.23f));
        foodModels.add(new FoodModel(2, R.drawable.tra_sua, "Trà sữa", "Trà sữa nhà làm", 12.2f));
        foodModels.add(new FoodModel(3, R.drawable.tra_vai, "Trà vãi", "Trà vãi Phúc Long", 12.2f));
        foodModels.add(new FoodModel(4, R.drawable.tra_cam, "Trà cam", "Trà cam Phúc Long", 12.2f));
    }


    public FindFoodAdapter getFindFoodAdapter() {
        return findFoodAdapter;
    }
}
