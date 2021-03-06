package hcmute.edu.vn.foody_10.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.Adapter.FindCommentsAdapter;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Database.CommentQuery;
import hcmute.edu.vn.foody_10.Database.ICommentQuery;
import hcmute.edu.vn.foody_10.Interface.IFoodDetail;
import hcmute.edu.vn.foody_10.Model.CommentModel;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.R;


public class FoodDetailActivity extends AppCompatActivity implements IFoodDetail {
    private ImageView ivFood;
    private TextView tvFoodName, tvDescription, tvPrice, tvEmptyCommentList;
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





        foodModel = (FoodModel) getIntent().getSerializableExtra("food");
        loadComment();

        if (commentModels.size() > 0) {
            tvEmptyCommentList.setVisibility(View.GONE);
        }

        if (foodModel != null) {
            Glide.with(this)
                    .load(foodModel.getPhotoFood())
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .into(ivFood);
            tvFoodName.setText(foodModel.getFoodName());
            tvDescription.setText(foodModel.getFoodDescription());
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            tvPrice.setText(formatter.format(foodModel.getPrice()) + "??");
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
                        commentModel.setUserId(Common.currentUserModel.getId());
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
            if (Common.currentUserModel != null) {
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
        tvPrice = findViewById(R.id.tvPriceDiscount);
        tvEmptyCommentList = findViewById(R.id.tvEmptyCommentList);
        rcComment = findViewById(R.id.rcComment);
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