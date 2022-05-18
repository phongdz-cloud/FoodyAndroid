package hcmute.edu.vn.foody_10.Activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hcmute.edu.vn.foody_10.Adapter.FindNotificationAdapter;
import hcmute.edu.vn.foody_10.Adapter.FindReceiptAdapter;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.Utils;
import hcmute.edu.vn.foody_10.Database.FoodQuery;
import hcmute.edu.vn.foody_10.Database.IFoodQuery;
import hcmute.edu.vn.foody_10.Database.IReceiptQuery;
import hcmute.edu.vn.foody_10.Database.ReceiptQuery;
import hcmute.edu.vn.foody_10.Model.FoodModel;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;
import hcmute.edu.vn.foody_10.R;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView rvNotifications;
    private TextView tvEmptyNotificationList;
    private List<ReceiptModel> receipts;
    private FindNotificationAdapter adapter;
    private IReceiptQuery receiptQuery;
    private IFoodQuery foodQuery;
    private List<ReceiptModel> receiptModelsDetail;
    private FindReceiptAdapter receiptDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        binding();
        receiptQuery = ReceiptQuery.getInstance();
        foodQuery = FoodQuery.getInstance();
        receipts = new ArrayList<>();
        adapter = new FindNotificationAdapter(this, receipts);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));
        rvNotifications.setAdapter(adapter);


        loadReceipt();

        if (receipts.size() > 0) {
            tvEmptyNotificationList.setVisibility(View.GONE);
        }
    }

    private void loadReceipt() {
        receipts.clear();
        final List<ReceiptModel> receiptLists = receiptQuery.findReceiptForUserID(Common.currentUserModel.getId());
        Set<String> receiptCodes = new HashSet();
        for (ReceiptModel receiptModel : receiptLists) {
            receiptCodes.add(receiptModel.getCode());
        }

        for (String code : receiptCodes) {
            ReceiptModel receipt = new ReceiptModel();
            float total = 0f;
            for (ReceiptModel receiptModel : receiptLists) {
                if (receiptModel.getCode().equals(code)) {
                    receipt.setUserId(receiptModel.getUserId());
                    receipt.setCode(code);
                    total += receiptModel.getTotalPrice() * receiptModel.getTotalCount();
                }
            }
            receipt.setTotalPrice(total);
            receipts.add(receipt);
        }
        adapter.notifyDataSetChanged();
    }

    public Bitmap loadImageReceipt(String code) {
        final List<ReceiptModel> receiptForCode = receiptQuery.findReceiptForCode(code);
        List<Bitmap> bitmaps = new ArrayList<>();
        for (ReceiptModel receiptModel : receiptForCode) {
            final FoodModel foodModel = foodQuery.findById(receiptModel.getProductId());
            Bitmap bitmap = Utils.convertBytesToBitMap(foodModel.getPhotoFood());
            bitmaps.add(bitmap);
        }
        return Utils.mergeThemAll(bitmaps);
    }

    public void loadDialogResultDetailReceipt(String code) {
        LayoutInflater li = LayoutInflater.from(this);
        View addReceiptView = li.inflate(R.layout.dialog_receipt_list, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        RecyclerView rcPurchase = addReceiptView.findViewById(R.id.rcPurchase);
        TextView tvTotalOrderPrice = addReceiptView.findViewById(R.id.tvTotalOrderPrice);
        float total = 0f;
        if (receiptModelsDetail == null && receiptDetailAdapter == null) {
            receiptModelsDetail = new ArrayList<>();
            receiptDetailAdapter = new FindReceiptAdapter(this, receiptModelsDetail);
        }
        rcPurchase.setLayoutManager(new LinearLayoutManager(this));
        rcPurchase.setAdapter(receiptDetailAdapter);

        receiptModelsDetail.clear();
        final List<ReceiptModel> receiptForCode = receiptQuery.findReceiptForCode(code);

        for (ReceiptModel receiptModel : receiptForCode) {
            total += receiptModel.getTotalPrice() * receiptModel.getTotalCount();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvTotalOrderPrice.setText(formatter.format(total));

        if (receiptForCode != null) {
            receiptModelsDetail.addAll(receiptForCode);
            receiptDetailAdapter.notifyDataSetChanged();
        }

        alertDialogBuilder.setView(addReceiptView);
        alertDialogBuilder.setTitle("Chi tiết sản phẩm đã mua");

        alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialogBuilder.show();
    }


    private void binding() {
        rvNotifications = findViewById(R.id.rvNotifications);
        tvEmptyNotificationList = findViewById(R.id.tvEmptyNotificationList);
    }
}