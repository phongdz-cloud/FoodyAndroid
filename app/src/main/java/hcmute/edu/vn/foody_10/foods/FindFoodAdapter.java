package hcmute.edu.vn.foody_10.foods;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.stream.Collectors;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.activities.FoodDetailActivity;
import hcmute.edu.vn.foody_10.activities.MainActivity;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.Utils;
import hcmute.edu.vn.foody_10.database.IOrderQuery;
import hcmute.edu.vn.foody_10.database.OrderQuery;
import hcmute.edu.vn.foody_10.models.FoodModel;
import hcmute.edu.vn.foody_10.orders.FindOrdersFragment;

public class FindFoodAdapter extends RecyclerView.Adapter<FindFoodAdapter.FindFoodViewHolder> implements Filterable {
    private Context context;
    private List<FoodModel> foodModels;
    private List<FoodModel> foodModelsOld;
    private IOrderQuery orderQuery;

    public FindFoodAdapter(Context context, List<FoodModel> foodModels) {
        this.context = context;
        this.foodModels = foodModels;
        this.foodModelsOld = foodModels;
        this.orderQuery = OrderQuery.getInstance();
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

        Glide.with(context)
                .load(Utils.convertBytesToBitMap(foodModel.getPhotoFood()))
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(holder.ivFood);
        holder.tvFoodName.setText(foodModel.getFoodName());
        holder.tvFoodDescription.setText(foodModel.getFoodDescription());
        holder.tvDiscountPrice.setText(foodModel.getPrice() + foodModel.getPrice() * 0.2 + "$");
        holder.tvDiscountPrice.setPaintFlags(holder.tvDiscountPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tvActualPrice.setText(foodModel.getPrice() + "$");

        holder.ivAddFood.setOnClickListener(click -> {
            if (Common.currentUser != null) {
                FindOrdersFragment.insertOrUpdateOrder(this.context, foodModel);
            } else {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.checkLoginUser();
            }
        });

        holder.ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("food", foodModel);
                context.startActivity(intent);
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
