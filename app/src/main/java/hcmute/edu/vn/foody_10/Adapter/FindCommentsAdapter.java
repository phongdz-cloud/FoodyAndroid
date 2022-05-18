package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.Activity.FoodDetailActivity;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.Model.CommentModel;
import hcmute.edu.vn.foody_10.Model.UserModel;

public class FindCommentsAdapter extends RecyclerView.Adapter<FindCommentsAdapter.FindCommentViewHolder> {
    private Context context;
    private List<CommentModel> commentModels;
    private IUserQuery userQuery;

    public FindCommentsAdapter(Context context, List<CommentModel> commentModels) {
        this.context = context;
        this.commentModels = commentModels;
        userQuery = UserQuery.getInstance();
    }

    @NonNull
    @Override
    public FindCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_list_layout, parent, false);
        return new FindCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindCommentViewHolder holder, int position) {
        CommentModel commentModel = commentModels.get(position);
        final UserModel userModel = userQuery.findById(commentModel.getUserId());
        Glide.with(context)
                .load(userModel.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(holder.ivUser);

        holder.tvUsername.setText(userModel.getName());

        SimpleDateFormat sfd = new SimpleDateFormat("hh:mm a");

        String dateTime = sfd.format(new Date(commentModel.getDateTime()));
        holder.tvDate.setText(dateTime);
        holder.tvMessage.setText(commentModel.getMessage());
        if (Common.currentUserModel == null) {
            holder.ibEdit.setVisibility(View.GONE);
            holder.ibDelete.setVisibility(View.GONE);
        } else if (!userModel.getId().equals(Common.currentUserModel.getId())) {
            holder.ibEdit.setVisibility(View.GONE);
            holder.ibDelete.setVisibility(View.GONE);
        }

        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodDetailActivity) context).editComment(commentModel);
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Are you sure you want to delete this comment ?");
                alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((FoodDetailActivity) context).deleteComment(commentModel.getId());
                    }
                });

                alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialogBuilder.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return commentModels.size();
    }


    public static class FindCommentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivUser;
        TextView tvUsername, tvMessage, tvDate;
        ImageButton ibEdit, ibDelete;

        public FindCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.ivUser);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvDate);
            ibEdit = itemView.findViewById(R.id.ibEdit);
            ibDelete = itemView.findViewById(R.id.ibDelete);
        }
    }


}
