package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
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

import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;
import hcmute.edu.vn.foody_10.R;

public class FindReceiptAdapter extends RecyclerView.Adapter<FindReceiptAdapter.FindReceiptViewHolder> {

    private Context context;
    private List<ReceiptModel> receipts;
    private IFoodQuery foodQuery;

    public FindReceiptAdapter(Context context, List<ReceiptModel> receipts) {
        this.context = context;
        this.receipts = receipts;
        this.foodQuery = FoodQuery.getInstance();
    }

    @NonNull
    @Override
    public FindReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.receipt_list_layout, parent, false);
        return new FindReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindReceiptViewHolder holder, int position) {
        final ReceiptModel receiptModel = receipts.get(position);
        final FoodModel foodModel = foodQuery.findById(receiptModel.getProductId());
        Glide.with(context)
                .load(foodModel.getPhotoFood())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.ivFood);
        holder.tvFoodName.setText(foodModel.getFoodName());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(foodModel.getPrice()) + " x " + receiptModel.getTotalCount() + " = " + formatter.format(receiptModel.getTotalPrice() * receiptModel.getTotalCount());
        holder.tvPriceFood.setText(price);
        holder.tvFoodDescription.setText(foodModel.getFoodDescription());
    }


    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public static class FindReceiptViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFood;
        TextView tvFoodName, tvPriceFood, tvFoodDescription;

        public FindReceiptViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvPriceFood = itemView.findViewById(R.id.tvPriceFood);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
        }
    }
}
