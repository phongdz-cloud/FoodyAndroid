package hcmute.edu.vn.foody_10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import hcmute.edu.vn.foody_10.Activity.NotificationActivity;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;
import hcmute.edu.vn.foody_10.R;

public class FindNotificationAdapter extends RecyclerView.Adapter<FindNotificationAdapter.FindNotificationViewFolder> {
    private final Context context;
    private final List<ReceiptModel> receipts;

    public FindNotificationAdapter(Context context, List<ReceiptModel> receipts) {
        this.context = context;
        this.receipts = receipts;
    }

    @NonNull
    @Override
    public FindNotificationViewFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_list_layout, parent, false);
        return new FindNotificationViewFolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindNotificationViewFolder holder, int position) {
        final ReceiptModel receiptModel = receipts.get(position);

        Glide.with(context)
                .load(((NotificationActivity) context).loadImageReceipt(receiptModel.getCode()))
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.ivMultiImageFood);

        holder.tvdDateTime.setText(receiptModel.getCode());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(receiptModel.getTotalPrice()) + "đ";
        holder.tvTotalPrice.setText(price);

        holder.ivMultiImageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NotificationActivity) context).loadDialogResultDetailReceipt(receiptModel.getCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public static class FindNotificationViewFolder extends RecyclerView.ViewHolder {
        ImageView ivMultiImageFood;
        TextView tvdDateTime, tvTotalPrice;

        public FindNotificationViewFolder(@NonNull View itemView) {
            super(itemView);
            ivMultiImageFood = itemView.findViewById(R.id.ivMultiImageFood);
            tvdDateTime = itemView.findViewById(R.id.tvDate);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);

        }
    }
}
