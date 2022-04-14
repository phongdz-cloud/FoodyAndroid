package hcmute.edu.vn.foody_10.orders;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Utils;


public class FindOrderAdapter extends RecyclerView.Adapter<FindOrderAdapter.FindOrderViewHolder> implements Filterable {
    private Context context;
    private List<OrderModel> orderModels;
    private List<OrderModel> orderModelsOld;

    public FindOrderAdapter(Context context, List<OrderModel> orderModels) {
        this.context = context;
        this.orderModels = orderModels;
        this.orderModelsOld = orderModels;
    }

    @NonNull
    @Override
    public FindOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_layout, parent, false);
        return new FindOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindOrderViewHolder holder, int position) {
        OrderModel orderModel = orderModels.get(position);

        holder.ivFood.setImageResource(orderModel.getPhotoFood());
        holder.tvFoodName.setText(orderModel.getFoodName());
        holder.tvAmount.setText("1");
        holder.tvFoodDescription.setText(orderModel.getFoodDescription());
        holder.tvTotalPrice.setText(Utils.formatCurrenCy(orderModel.getPrice()) + "đ");
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (!strSearch.isEmpty()) {
                    orderModels = orderModelsOld
                            .stream()
                            .filter(x -> x.getFoodName().toLowerCase().contains(strSearch.toLowerCase()))
                            .collect(Collectors.toList());
                } else {
                    orderModels = orderModelsOld;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = orderModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                orderModels = (List<OrderModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class FindOrderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood, ivAddFood, ivMinusFood;
        private TextView tvFoodName, tvAmount, tvFoodDescription, tvTotalPrice;

        public FindOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivAddFood = itemView.findViewById(R.id.ivAddFood);
            ivMinusFood = itemView.findViewById(R.id.ivMinusFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}