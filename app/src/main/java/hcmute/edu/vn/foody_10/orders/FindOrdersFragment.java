package hcmute.edu.vn.foody_10.orders;

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

public class FindOrdersFragment extends Fragment {
    private FindOrderAdapter findOrderAdapter;
    private List<OrderModel> orderModels;
    private View progressBar;
    private TextView tvEmptyFriendsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFindOrders = view.findViewById(R.id.rvFindOrders);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyFriendsList = view.findViewById(R.id.tvEmptyFoodList);
        rvFindOrders.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderModels = new ArrayList<>();
        findOrderAdapter = new FindOrderAdapter(getActivity(), orderModels);
        rvFindOrders.setAdapter(findOrderAdapter);

        tvEmptyFriendsList.setVisibility(View.VISIBLE);

        dataFood();
        if (orderModels.size() > 0) {
            tvEmptyFriendsList.setVisibility(View.GONE);
        }
        findOrderAdapter.notifyDataSetChanged();
    }

    private void dataFood() {
        orderModels.add(new OrderModel(1, R.drawable.banh_plan, 2, "Bánh flan", "Bánh flan", 2.5f));
        orderModels.add(new OrderModel(2, R.drawable.banh_trang, 3, "Bánh tráng", "Bánh tráng", 3.2f));
        orderModels.add(new OrderModel(3, R.drawable.tra_cam, 1, "Trà cam", "Trà cam", 3.7f));
        orderModels.add(new OrderModel(4, R.drawable.tra_vai, 2, "Trà vãi", "Trà vãi", 2.5f));
    }

    public FindOrderAdapter getFindOrderAdapter() {
        return findOrderAdapter;
    }
}
