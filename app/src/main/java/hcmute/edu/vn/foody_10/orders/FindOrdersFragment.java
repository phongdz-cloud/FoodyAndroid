package hcmute.edu.vn.foody_10.orders;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.database.IOrderQuery;
import hcmute.edu.vn.foody_10.database.OrderQuery;
import hcmute.edu.vn.foody_10.foods.FoodModel;

public class FindOrdersFragment extends Fragment {
    private static FindOrderAdapter findOrderAdapter;
    private static List<OrderModel> orderModels;
    private View progressBar;
    private TextView tvEmptyFriendsList;
    private static IOrderQuery orderQuery = OrderQuery.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_order, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
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
        orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void clickBtnPlusCount(Context context, OrderModel orderModel) {
        try {
            orderModel.setCount(orderModel.getCount() + 1);
            final Integer updateOrder = orderQuery.updateCount(orderModel);
            if (updateOrder != null) {
                Toast.makeText(context, context.getString(R.string.update_to_cart_successfully), Toast.LENGTH_SHORT).show();
                orderModels.clear();
                orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
                findOrderAdapter.notifyDataSetChanged();
            }

        } catch (Exception ex) {
            Toast.makeText(context, context.getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void clickBtnMinusCount(Context context, OrderModel orderModel) {
        try {
            if (orderModel.getCount() > 1) {
                orderModel.setCount(orderModel.getCount() - 1);
                final Integer updateOrder = orderQuery.updateCount(orderModel);
                if (updateOrder != null) {
                    Toast.makeText(context, context.getString(R.string.update_to_cart_successfully), Toast.LENGTH_SHORT).show();
                    orderModels.clear();
                    orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
                    findOrderAdapter.notifyDataSetChanged();
                }
            } else {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(R.string.notification);
                alertDialog.setMessage(R.string.remove_order);
                alertDialog.setIcon(R.mipmap.ic_launcher_foreground);
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Integer deleteOrder = orderQuery.deleteOrder(orderModel.getId());
                        if (deleteOrder != null) {
                            orderModels.clear();
                            Toast.makeText(context, context.getString(R.string.remove_food_success), Toast.LENGTH_SHORT).show();
                            orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
                            findOrderAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, context.getString(R.string.remove_food_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        } catch (Exception ex) {
            Toast.makeText(context, context.getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void insertOrUpdateOrder(Context context, FoodModel foodModel) {
        try {
            orderModels.clear();
            final OrderModel orderModel = orderQuery.findByProductIdAndUserId(foodModel.getId(), Common.currentUser.getId());
            if (orderModel == null) {
                OrderModel orderAdd = new OrderModel(null, foodModel.getPhotoFood(),
                        1, foodModel.getFoodName(), foodModel.getFoodDescription(),
                        foodModel.getPrice(), foodModel.getId(), Common.currentUser.getId());
                final Long insertOrder = orderQuery.insert(orderAdd);
                if (insertOrder != null) {
                    Toast.makeText(context, context.getString(R.string.add_to_cart_successfully), Toast.LENGTH_SHORT).show();
                    orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
                    findOrderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, context.getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT).show();
                }
            } else {
                orderModel.setCount(orderModel.getCount() + 1);
                final Integer updateCountOrder = orderQuery.updateCount(orderModel);
                if (updateCountOrder != null) {
                    Toast.makeText(context, context.getString(R.string.update_to_cart_successfully), Toast.LENGTH_SHORT).show();
                    orderModels.addAll(orderQuery.findOrderByUserId(Common.currentUser.getId()));
                    findOrderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, context.getString(R.string.update_to_cart_failed), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(context, context.getString(R.string.server_error, ex.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    public static FindOrderAdapter getFindOrderAdapter() {
        return findOrderAdapter;
    }
}
