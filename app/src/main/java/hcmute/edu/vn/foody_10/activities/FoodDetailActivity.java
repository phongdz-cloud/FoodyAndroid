package hcmute.edu.vn.foody_10.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.comments.FindCommentsAdapter;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.database.CommentQuery;
import hcmute.edu.vn.foody_10.database.ICommentQuery;
import hcmute.edu.vn.foody_10.interfaces.IFoodDetail;
import hcmute.edu.vn.foody_10.models.CommentModel;
import hcmute.edu.vn.foody_10.models.FoodModel;


public class FoodDetailActivity extends AppCompatActivity implements IFoodDetail {
    private ImageView ivFood;
    private TextView tvFoodName, tvDescription, tvPriceDiscount, tvPriceNoDiscount, tvEmptyCommentList;
    private RatingBar rbFood;
    private RecyclerView rcComment;
    private FoodModel foodModel;
    private FindCommentsAdapter commentsAdapter;
    private List<CommentModel> commentModels;
    private ICommentQuery commentQuery;
    private EditText edtAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        commentQuery = CommentQuery.getInstance();
        binding();


        commentModels = new ArrayList<>();
        commentsAdapter = new FindCommentsAdapter(this, commentModels);

        rcComment.setLayoutManager(new LinearLayoutManager(this));
        rcComment.setAdapter(commentsAdapter);


        if (commentModels.size() > 0) {
            tvEmptyCommentList.setVisibility(View.GONE);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        foodModel = (FoodModel) getIntent().getSerializableExtra("food");
        loadComment();
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

    private void loadComment() {
        final List<CommentModel> comments = commentQuery.findCommentByFood(foodModel.getId());
        if (comments != null) {
            commentModels.clear();
            commentModels.addAll(comments);
            commentsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void insertOrUpdateComment(CommentModel comment) {
        LayoutInflater li = LayoutInflater.from(FoodDetailActivity.this);
        View addCommentView = li.inflate(R.layout.add_comment, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FoodDetailActivity.this);
        edtAddComment = addCommentView.findViewById(R.id.edtAddComment);
        if (comment != null) {
            edtAddComment.setText(comment.getMessage());
        }
        alertDialogBuilder.setNegativeButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = edtAddComment.getText().toString();
                if (!msg.isEmpty()) {
                    if (comment == null) {
                        CommentModel commentModel = new CommentModel();
                        commentModel.setMessage(msg);
                        commentModel.setDateTime(System.currentTimeMillis());
                        commentModel.setUserId(Common.currentUser.getId());
                        commentModel.setProductId(foodModel.getId());
                        final Long insertComment = commentQuery.insert(commentModel);
                        if (insertComment != null) {
                            loadComment();
                            Toast.makeText(FoodDetailActivity.this, getString(R.string.insert_comment_successfully), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FoodDetailActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        comment.setMessage(msg);
                        comment.setDateTime(System.currentTimeMillis());
                        final Integer updateComment = commentQuery.update(comment);
                        if (updateComment != null) {
                            loadComment();
                            Toast.makeText(FoodDetailActivity.this, getString(R.string.update_comment_successfully), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FoodDetailActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    edtAddComment.setError(getString(R.string.enter_message));
                }
            }
        });

        alertDialogBuilder.setView(addCommentView);
        alertDialogBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuAddComment) {
            if (Common.currentUser != null) {
                insertOrUpdateComment(null);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(FoodDetailActivity.this);
                dialog.setTitle("Yes");
                dialog.setMessage("You need to login to perform this function?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(FoodDetailActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }

        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void editComment(CommentModel commentModel) {
        insertOrUpdateComment(commentModel);
    }

    @Override
    public void deleteComment(Integer commentId) {
        final Integer deleteComment = commentQuery.delete(commentId);
        if (deleteComment != null) {
            loadComment();
            Toast.makeText(this, getString(R.string.delete_comment_successfully), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
        }
    }
}