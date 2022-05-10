package hcmute.edu.vn.foody_10.foods;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.activities.FoodActivity;
import hcmute.edu.vn.foody_10.models.FoodModel;

public class FoodOfUserAdapter extends RecyclerView.Adapter<FoodOfUserAdapter.FoodOfUserViewHolder> {
    private Context context;
    private List<FoodModel> foods;

    public FoodOfUserAdapter(Context context, List<FoodModel> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodOfUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_user_list_layout, parent, false);
        return new FoodOfUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOfUserViewHolder holder, int position) {
        final FoodModel foodModel = foods.get(position);

        Glide.with(context)
                .load(foodModel.getPhotoFood())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.ivFood);

        holder.tvFoodName.setText(foodModel.getFoodName());
        holder.tvFoodDescription.setText(foodModel.getFoodDescription());
        holder.tvPrice.setText(foodModel.getPrice() + "$");

        holder.ibEdit.setOnClickListener(view -> {
            Intent intent = new Intent(context, FoodActivity.class);
            intent.putExtra("food", foodModel);
            context.startActivity(intent);
        });

        holder.ibDelete.setOnClickListener(view -> {
            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class FoodOfUserViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFood;
        TextView tvFoodName, tvFoodDescription, tvPrice;
        ImageButton ibEdit, ibDelete;

        public FoodOfUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.ivFood);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvFoodDescription = itemView.findViewById(R.id.tvFoodDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ibEdit = itemView.findViewById(R.id.ibEdit);
            ibDelete = itemView.findViewById(R.id.ibDelete);
        }
    }
}
