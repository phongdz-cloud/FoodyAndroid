package hcmute.edu.vn.foody_10.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.comments.FindCommentsAdapter;
import hcmute.edu.vn.foody_10.models.CommentModel;
import hcmute.edu.vn.foody_10.models.FoodModel;

public class FoodDetailActivity extends AppCompatActivity {
    ImageView ivFood;
    TextView tvFoodName, tvDescription, tvPriceDiscount, tvPriceNoDiscount, tvEmptyCommentList;
    RatingBar rbFood;
    RecyclerView rcComment;

    FindCommentsAdapter commentsAdapter;
    List<CommentModel> commentModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        binding();

        commentModels = new ArrayList<>();
        commentsAdapter = new FindCommentsAdapter(this, commentModels);

        rcComment.setLayoutManager(new LinearLayoutManager(this));
        rcComment.setAdapter(commentsAdapter);

        if (commentModels.size() > 0) {
            tvEmptyCommentList.setVisibility(View.GONE);
        }

        data();

        commentsAdapter.notifyDataSetChanged();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FoodModel foodModel = (FoodModel) getIntent().getSerializableExtra("food");
        if (foodModel != null) {
            Glide.with(this)
                    .load(foodModel.getPhotoFood())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(ivFood);
            tvFoodName.setText(foodModel.getFoodName());
            tvDescription.setText(foodModel.getFoodDescription());
            tvPriceDiscount.setText(foodModel.getPrice() + "$");
            tvPriceNoDiscount.setText((foodModel.getPrice() + foodModel.getPrice() * 0.2) + "$");
            tvPriceNoDiscount.setPaintFlags(tvPriceNoDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            rbFood.setRating(4.5f);
        }
    }

    private void binding() {
        ivFood = findViewById(R.id.ivFood);
        tvFoodName = findViewById(R.id.tvFoodName);
        tvDescription = findViewById(R.id.tvDescription);
        tvPriceDiscount = findViewById(R.id.tvPriceDiscount);
        tvPriceNoDiscount = findViewById(R.id.tvPriceNoDiscount);
        tvEmptyCommentList = findViewById(R.id.tvEmptyCommentList);
        rbFood = findViewById(R.id.rbFood);
        rcComment = findViewById(R.id.rcComment);
    }

    private void data() {
        commentModels.add(new CommentModel("Hello", System.currentTimeMillis()));
        commentModels.add(new CommentModel("Đồ ăn ngon ghê", System.currentTimeMillis()));
        commentModels.add(new CommentModel("Nên mua đồ ăn đây nhé mọi người", System.currentTimeMillis()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}