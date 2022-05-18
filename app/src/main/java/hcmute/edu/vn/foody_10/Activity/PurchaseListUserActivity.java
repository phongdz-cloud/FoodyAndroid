package hcmute.edu.vn.foody_10.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.foody_10.Adapter.PurchaseOfUserAdapter;
import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Database.IOrderQuery;
import hcmute.edu.vn.foody_10.Database.IReceiptQuery;
import hcmute.edu.vn.foody_10.Database.OrderQuery;
import hcmute.edu.vn.foody_10.Database.ReceiptQuery;
import hcmute.edu.vn.foody_10.Interface.IOrderFood;
import hcmute.edu.vn.foody_10.Model.OrderModel;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;
import hcmute.edu.vn.foody_10.R;

public class PurchaseListUserActivity extends AppCompatActivity implements IOrderFood {
    private RecyclerView rcPurchase;
    private TextView tvTotalOrderPrice, tvUserOrder;
    private ImageView ivUserOrder;
    private Button btnBuy;
    private PurchaseOfUserAdapter adapter;
    private List<OrderModel> orders;
    private IOrderQuery orderQuery;
    private IReceiptQuery receiptQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list_user);

        tvUserOrder = findViewById(R.id.tvUserOrder);
        ivUserOrder = findViewById(R.id.ivUserOrder);
        orderQuery = OrderQuery.getInstance();
        receiptQuery = ReceiptQuery.getInstance();
        rcPurchase = findViewById(R.id.rcPurchase);
        tvTotalOrderPrice = findViewById(R.id.tvTotalOrderPrice);
        btnBuy = findViewById(R.id.btnBuy);

        orders = new ArrayList<>();
        adapter = new PurchaseOfUserAdapter(this, orders);


        rcPurchase.setLayoutManager(new LinearLayoutManager(this));
        rcPurchase.setAdapter(adapter);

        if (Common.currentUserModel != null) {
            Glide.with(this)
                    .load(Common.currentUserModel.getAvatar())
                    .placeholder(R.drawable.default_profile)
                    .error(R.drawable.default_profile)
                    .into(ivUserOrder);
            tvUserOrder.setText(Common.currentUserModel.getName());
        }
        btnBuy.setOnClickListener(view -> {
            if (orders.size() > 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.S aa");
                String code = dateFormat.format(new Date());
                for (OrderModel orderModel : orders) {
                    ReceiptModel receiptModel = new ReceiptModel(orderModel.getUserId(),
                            orderModel.getProductId(), orderModel.getCount(),
                            orderModel.getPrice(), code);
                    final Long receiptInsert = receiptQuery.insert(receiptModel);
                    if (receiptInsert != null) {
                        final Integer deleteOrder = orderQuery.deleteOrder(orderModel.getId());
                        if (deleteOrder == null) {
                            break;
                        }
                    } else {
                        Toast.makeText(PurchaseListUserActivity.this, getString(R.string.server_error)
                                , Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                loadDataOrder();
                Toast.makeText(PurchaseListUserActivity.this, getString(R.string.payment_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PurchaseListUserActivity.this, R.string.empty_list_order, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadDataOrder() {
        orders.clear();
        if (Common.currentUserModel != null) {
            final List<OrderModel> orderByUserId = orderQuery.findOrderByUserId(Common.currentUserModel.getId());
            if (orderByUserId != null) {
                orders.addAll(orderByUserId);
                Float total = 0.0f;
                for (OrderModel order : orders) {
                    total += order.getPrice() * order.getCount();
                }
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                tvTotalOrderPrice.setText(formatter.format(total) + "đ");
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataOrder();
    }

    @Override
    public void addCountOrder(OrderModel orderModel) {
        orderModel.setCount(orderModel.getCount() + 1);
        final Integer updateOrder = orderQuery.updateCount(orderModel);
        if (updateOrder != null) {
            Toast.makeText(this, R.string.add_to_cart_successfully, Toast.LENGTH_SHORT).show();
            loadDataOrder();
        }
    }



    @Override
    public void minusCountFoodOrDeleteOrder(OrderModel orderModel) {
        if (orderModel.getCount() > 1) {
            orderModel.setCount(orderModel.getCount() - 1);
            final Integer updateOrder = orderQuery.updateCount(orderModel);
            if (updateOrder != null) {
                Toast.makeText(this, getString(R.string.update_to_cart_successfully), Toast.LENGTH_SHORT).show();
                loadDataOrder();
            }
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(R.string.notification);
            alertDialog.setMessage(R.string.remove_order);
            alertDialog.setIcon(R.mipmap.ic_launcher_foreground);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    final Integer deleteOrder = orderQuery.deleteOrder(orderModel.getId());
                    if (deleteOrder != null) {
                        Toast.makeText(PurchaseListUserActivity.this, R.string.remove_food_success, Toast.LENGTH_SHORT).show();
                        loadDataOrder();
                    } else {
                        Toast.makeText(PurchaseListUserActivity.this, R.string.remove_food_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }
    }
}