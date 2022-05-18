package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Activity.FoodDetailActivity;
import hcmute.edu.vn.foody_10.Activity.PurchaseListUserActivity;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.IOrderQuery;
import hcmute.edu.vn.foody_10.Database.OrderQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.OrderModel;

public class PurchaseOfUserAdapter extends RecyclerView.Adapter<PurchaseOfUserAdapter.PurchaseOfUserViewHolder> {
    private final Context context;
    private final List<OrderModel> orders;

    public PurchaseOfUserAdapter(Context context, List<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public PurchaseOfUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_layout, parent, false);
        return new PurchaseOfUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOfUserViewHolder holder, int position) {
        final OrderModel orderModel = orders.get(position);

        Glide.with(context)
                .load(Utils.convertBytesToBitMap(orderModel.getPhotoFood()))
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(holder.ivFood);

        holder.tvFoodName.setText(orderModel.getFoodName());
        holder.tvAmount.setText(String.valueOf(orderModel.getCount()));
        holder.tvFoodDescription.setText(orderModel.getFoodDescription());
        holder.tvPrice.setText(calculatePrice(orderModel.getPrice(), orderModel.getCount()));

        holder.ivAddFood.setOnClickListener(view -> {
            ((PurchaseListUserActivity) context).addCountOrder(orderModel);
        });

        holder.ivMinusFood.setOnClickListener(view -> {
            ((PurchaseListUserActivity) context).minusCountFoodOrDeleteOrder(orderModel);
        });

        holder.ivFood.setOnClickListener(view -> {
            Intent intent = new Intent(context, FoodDetailActivity.class);
            FoodModel foodModel = new FoodModel();
            foodModel.setFoodName(orderModel.getFoodName());
            foodModel.setFoodDescription(orderModel.getFoodDescription());
            foodModel.setPhotoFood(orderModel.getPhotoFood());
            foodModel.setPrice(orderModel.getPrice());
            foodModel.setId(orderModel.getProductId());
            intent.putExtra("food", foodModel);
            context.startActivity(intent);
        });
    }

    private String calculatePrice(Float price, Integer count) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String priceFood = formatter.format(price) + " x " + count + " = ";
        return priceFood + formatter.format(price * count);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class PurchaseOfUserViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFood, ivAddFood, ivMinusFood;
        private final TextView tvPrice, tvFoodName, tvAmount, tvFoodDescription;

        public PurchaseOfUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivAddFood = itemView.findViewById(R.id.ivAddFood);
            ivMinusFood = itemView.findViewById(R.id.ivMinusFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvPriceFood);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
        }
    }
}
