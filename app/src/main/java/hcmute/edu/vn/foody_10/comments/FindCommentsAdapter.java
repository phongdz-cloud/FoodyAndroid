package hcmute.edu.vn.foody_10.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;
import hcmute.edu.vn.foody_10.models.CommentModel;
import hcmute.edu.vn.foody_10.models.User;

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
        final User user = userQuery.findById(commentModel.getUserId());
        Glide.with(context)
                .load(user.getAvatar())
                .placeholder(R.drawable.default_profile)
                .error(R.drawable.default_profile)
                .into(holder.ivUser);

        holder.tvUsername.setText(user.getName());

        SimpleDateFormat sfd = new SimpleDateFormat("hh:mm a");

        String dateTime = sfd.format(new Date(commentModel.getDateTime()));
//        String[] splitString = dateTime.split(" ");
//        String messageTime = splitString[1];
        holder.tvDate.setText(dateTime);
        holder.tvMessage.setText(commentModel.getMessage());
        if (Common.currentUser == null) {
            holder.ibEdit.setVisibility(View.GONE);
            holder.ibDelete.setVisibility(View.GONE);
        } else if (!user.getId().equals(Common.currentUser.getId())) {
            holder.ibEdit.setVisibility(View.GONE);
            holder.ibDelete.setVisibility(View.GONE);
        }
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
