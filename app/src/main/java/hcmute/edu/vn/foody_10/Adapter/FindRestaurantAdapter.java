package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import hcmute.edu.vn.foody_10.Activity.DetailRestaurantActivity;
import hcmute.edu.vn.foody_10.Model.RestaurantModel;
import hcmute.edu.vn.foody_10.R;

public class FindRestaurantAdapter extends RecyclerView.Adapter<FindRestaurantAdapter.FindRestaurantViewHolder> implements Filterable {
    private final Context context;
    private List<RestaurantModel> restaurants;
    private final List<RestaurantModel> restaurantModelsOld;

    public FindRestaurantAdapter(Context context, List<RestaurantModel> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
        this.restaurantModelsOld = restaurants;
    }

    @NonNull
    @Override
    public FindRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_list_layout, parent, false);
        return new FindRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindRestaurantViewHolder holder, int position) {
        final RestaurantModel restaurantModel = restaurants.get(position);
        Picasso.get()
                .load(restaurantModel.getRestaurantPhoto())
                .into(holder.ivRestaurant);
        String foodName = restaurantModel.getName();
        if (foodName.length() > 20) {
            foodName = foodName.substring(0, 20);
        }
        holder.tvResName.setText(foodName + "...");
        holder.tvRestaurantDescription.setText(restaurantModel.getDescription());
        holder.ivRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailRestaurantActivity.class);
                intent.putExtra("detailRestaurant", restaurantModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (!strSearch.isEmpty()) {
                    restaurants = restaurantModelsOld
                            .stream()
                            .filter(x -> x.getName().toLowerCase().contains(strSearch.toLowerCase()))
                            .collect(Collectors.toList());
                } else {
                    restaurants = restaurantModelsOld;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = restaurants;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                restaurants = (List<RestaurantModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class FindRestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivRestaurant;
        private final TextView tvResName;
        private final TextView tvRestaurantDescription;

        public FindRestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRestaurant = itemView.findViewById(R.id.ivRestaurant);
            tvResName = itemView.findViewById(R.id.tvResName);
            tvRestaurantDescription = itemView.findViewById(R.id.tvRestaurantDescription);
        }
    }
}
