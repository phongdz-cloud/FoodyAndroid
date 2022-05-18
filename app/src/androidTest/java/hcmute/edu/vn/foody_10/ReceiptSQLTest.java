package hcmute.edu.vn.foody_10;

import android.util.Log;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hcmute.edu.vn.foody_10.Activity.MainActivity;
import hcmute.edu.vn.foody_10.Common.Constants;
import hcmute.edu.vn.foody_10.Database.Database;
import hcmute.edu.vn.foody_10.Database.IReceiptQuery;
import hcmute.edu.vn.foody_10.Database.ReceiptQuery;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;

public class ReceiptSQLTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    private Database database = null;

    private IReceiptQuery receiptQuery;

    @Before
    public void setUp() throws Exception {
        mainActivity = mActivityTestRule.getActivity();
        database = new Database(mainActivity, Constants.DATABASE, null, 1);
        receiptQuery = ReceiptQuery.getInstance();
    }

    @After
    public void testDown() throws Exception {
        mainActivity = null;
    }

    @Test
    public void testLaunch() {
        View view = mainActivity.findViewById(R.id.vpMain);

        Assert.assertNotNull(view);
        Assert.assertNotNull(database);
        Assert.assertNotNull(receiptQuery);
    }


    @Test
    public void findAllReceipt() {
        final List<ReceiptModel> receipts = receiptQuery.findAll();
        Assert.assertTrue(receipts.size() > 0);
        for (ReceiptModel receipt : receipts) {
            Log.d("receipt", receipt.toString());
        }
    }


    @Test
    public void findAllReceiptByCode() {
        Set<String> codes = new HashSet<>();
        final List<ReceiptModel> receipts = receiptQuery.findAll();
        Assert.assertTrue(receipts.size() > 0);

        for (ReceiptModel receipt : receipts) {
            codes.add(receipt.getCode());
        }
        List<ReceiptModel> receiptForCode = null;
        for (String code : codes) {
            receiptForCode = receiptQuery.findReceiptForCode(code);
        }

        Assert.assertNotNull(receiptForCode);
        for (ReceiptModel receipt : receiptForCode) {
            Log.d("receipt", receipt.toString());
        }
    }

    @Test
    public void testCreateTableCategory() {
        database.QueryData("create table if not exists receipt(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "product_id INTEGER, " +
                "total_count INTEGER, " +
                "total_price float, " +
                "code varchar(255)" +
                ")");
    }
}
