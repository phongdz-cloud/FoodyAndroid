package hcmute.edu.vn.foody_10.foods;

import android.content.Context;
import android.graphics.Paint;
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

public class FindFoodAdapter extends RecyclerView.Adapter<FindFoodAdapter.FindFoodViewHolder> implements Filterable {
    private Context context;
    private List<FoodModel> foodModels;
    private List<FoodModel> foodModelsOld;

    public FindFoodAdapter(Context context, List<FoodModel> foodModels) {
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelsOld = foodModels;
    }

    @NonNull
    @Override
    public FindFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_layout, parent, false);
        return new FindFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindFoodViewHolder holder, int position) {
        FoodModel foodModel = foodModels.get(position);

        holder.ivFood.setImageResource(foodModel.getPhotoFood());
        holder.tvFoodName.setText(foodModel.getFoodName());
        holder.tvFoodDescription.setText(foodModel.getFoodDescription());
        holder.tvDiscountPrice.setText(Utils.formatCurrenCy(foodModel.getPrice()) + "đ");
        holder.tvDiscountPrice.setPaintFlags(holder.tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvActualPrice.setText(Utils.formatCurrenCy(foodModel.getPrice()) + "đ");
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
        private TextView tvFoodName, tvFoodDescription, tvDiscountPrice, tvActualPrice;

        public FindFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            ivAddFood = itemView.findViewById(R.id.ivAddFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvDiscountPrice = itemView.findViewById(R.id.tvDiscountPrice);
            tvActualPrice = itemView.findViewById(R.id.tvActualPrice);
        }
    }
}
