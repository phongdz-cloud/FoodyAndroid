package hcmute.edu.vn.foody_10.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.database.IOrderQuery;
import hcmute.edu.vn.foody_10.database.OrderQuery;
import hcmute.edu.vn.foody_10.models.OrderModel;
import hcmute.edu.vn.foody_10.purchases.PurchaseOfUserAdapter;

public class PurchaseListUserActivity extends AppCompatActivity {
    private RecyclerView rcPurchase;
    private TextView tvTotalOrderPrice;
    private Button btnBuy;
    private PurchaseOfUserAdapter adapter;
    private List<OrderModel> orders;
    private IOrderQuery orderQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderQuery = OrderQuery.getInstance();
        rcPurchase = findViewById(R.id.rcPurchase);
        tvTotalOrderPrice = findViewById(R.id.tvTotalOrderPrice);
        btnBuy = findViewById(R.id.btnBuy);

        orders = new ArrayList<>();
        adapter = new PurchaseOfUserAdapter(this, orders);


        rcPurchase.setLayoutManager(new LinearLayoutManager(this));
        rcPurchase.setAdapter(adapter);



    }

    private void loadDataOrder() {
        final List<OrderModel> orderByUserId = orderQuery.findOrderByUserId(Common.currentUser.getId());
        if (orderByUserId != null) {
            orders.addAll(orderByUserId);
            Float total = 0.0f;
            for (OrderModel order : orders) {
                total += order.getPrice() * order.getCount();
            }
            tvTotalOrderPrice.setText("Total Price: " + total + "$");
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataOrder();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}