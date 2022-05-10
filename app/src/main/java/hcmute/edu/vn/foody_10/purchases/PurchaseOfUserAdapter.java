package hcmute.edu.vn.foody_10.purchases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.models.OrderModel;

public class PurchaseOfUserAdapter extends RecyclerView.Adapter<PurchaseOfUserAdapter.PurchaseOfUserViewHolder> {
    private Context context;
    private List<OrderModel> orders;

    public PurchaseOfUserAdapter(Context context, List<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public PurchaseOfUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.purchase_user_list, parent, false);
        return new PurchaseOfUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOfUserViewHolder holder, int position) {
        final OrderModel orderModel = orders.get(position);

        Glide.with(context)
                .load(orderModel.getPhotoFood())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.ivFood);

        holder.tvFoodName.setText(orderModel.getFoodName());
        holder.tvFoodDescription.setText(orderModel.getFoodDescription());
        holder.tvAmount.setText("Count: " + orderModel.getCount());
        holder.tvTotalPrice.setText(orderModel.getPrice() * orderModel.getCount() + "$");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class PurchaseOfUserViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFood;
        TextView tvFoodName, tvAmount, tvFoodDescription, tvTotalPrice;

        public PurchaseOfUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
