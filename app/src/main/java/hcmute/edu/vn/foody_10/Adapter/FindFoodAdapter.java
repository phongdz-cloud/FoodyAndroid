package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import hcmute.edu.vn.foody_10.Activity.DetailRestaurantActivity;
import hcmute.edu.vn.foody_10.Activity.FoodDetailActivity;
import hcmute.edu.vn.foody_10.Activity.LoginActivity;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.IOrderQuery;
import hcmute.edu.vn.foody_10.Database.OrderQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.OrderModel;
import hcmute.edu.vn.foody_10.R;

public class FindFoodAdapter extends RecyclerView.Adapter<FindFoodAdapter.FindFoodViewHolder> implements Filterable {
    private final Context context;
    private List<FoodModel> foodModels;
    private final IOrderQuery orderQuery;
    private final List<FoodModel> foodModelsOld;

    public FindFoodAdapter(Context context, List<FoodModel> foodModels) {
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelsOld = foodModels;
        orderQuery = OrderQuery.getInstance();
    }

    @NonNull
    @Override
    public FindFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_restaurant_list, parent, false);
        return new FindFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindFoodViewHolder holder, int position) {
        FoodModel foodModel = foodModels.get(position);
        holder.ivFood.setImageBitmap(Utils.convertBytesToBitMap(foodModel.getPhotoFood()));
        holder.tvFoodName.setText(foodModel.getFoodName());
        holder.tvFoodDescription.setText(foodModel.getFoodDescription());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(foodModel.getPrice()) + "đ";
        holder.tvPrice.setText(price);

        holder.ivFood.setOnClickListener(view -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            intent.putExtra("food", foodModel);
            context.startActivity(intent);
        });

        holder.ivAddFood.setOnClickListener(view -> {
            if (Common.currentUserModel != null) {
                if (context.getClass().equals(DetailRestaurantActivity.class)) {
                    final OrderModel orderByFoodName = orderQuery.findByProductId(foodModel.getId());
                    if (orderByFoodName == null) {
                        OrderModel orderModel = new OrderModel();
                        orderModel.setPhotoFood(foodModel.getPhotoFood());
                        orderModel.setCount(1);
                        orderModel.setFoodName(foodModel.getFoodName());
                        orderModel.setFoodDescription(foodModel.getFoodDescription());
                        orderModel.setPrice(foodModel.getPrice());
                        orderModel.setProductId(foodModel.getId());
                        orderModel.setUserId(foodModel.getUserId());
                        final Long insertOrder = orderQuery.insert(orderModel);
                        if (insertOrder > 0) {
                            Toast.makeText(context, "Đặt giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Món hàng này đã tồn tại trong giỏ hàng của bạn", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(context, DetailRestaurantActivity.class);
                    intent.putExtra("userId", foodModel.getUserId());
                    context.startActivity(intent);
                }
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Thông báo");
                dialog.setMessage("Bạn cần đăng nhập để có thể đặt món ăn");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (!strSearch.isEmpty()) {
                    foodModels = foodModelsOld
                            .stream()
                            .filter(x -> x.getFoodName().toLowerCase().contains(strSearch.toLowerCase()))
                            .collect(Collectors.toList());
                } else {
                    foodModels = foodModelsOld;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = foodModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodModels = (List<FoodModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class FindFoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood, ivAddFood;
        private TextView tvFoodName, tvFoodDescription, tvPrice;

        public FindFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivAddFood = itemView.findViewById(R.id.ivAddFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}
