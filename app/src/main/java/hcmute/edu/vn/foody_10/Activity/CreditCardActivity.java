package hcmute.edu.vn.foody_10.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_10.Common.Common;
import hcmute.edu.vn.foody_10.Common.CreditCardType;
import hcmute.edu.vn.foody_10.Database.IUserQuery;
import hcmute.edu.vn.foody_10.Database.UserQuery;
import hcmute.edu.vn.foody_10.R;

public class CreditCardActivity extends AppCompatActivity {
    private RadioButton rbEmpty, rbMoMo, rbZaloPay, rbMasterCard;
    private RadioGroup rgCheckCredit;
    private IUserQuery userQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rbEmpty = findViewById(R.id.rbEmpty);
        rbMoMo = findViewById(R.id.rbMoMo);
        rbMasterCard = findViewById(R.id.rbMasterCard);
        rbZaloPay = findViewById(R.id.rbZaloPay);
        rgCheckCredit = findViewById(R.id.rgCheckCredit);
        userQuery = UserQuery.getInstance();
        setCurrentUserPayment();


        rgCheckCredit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbEmpty:
                        Common.currentUserModel.setCreditCard(CreditCardType.EMPTY.name());
                        updateCreditCardUser("Bạn chưa chọn phương thức thanh toán");
                        break;
                    case R.id.rbMoMo:
                        Common.currentUserModel.setCreditCard(CreditCardType.MOMO.name());
                        updateCreditCardUser("Chọn MoMo thành công");
                        break;
                    case R.id.rbZaloPay:
                        Common.currentUserModel.setCreditCard(CreditCardType.ZALO.name());
                        updateCreditCardUser("Chọn ZaloPay thành công");
                        break;
                    case R.id.rbMasterCard:
                        Common.currentUserModel.setCreditCard(CreditCardType.MASTERCARD.name());
                        updateCreditCardUser("Chọn MasterCard thành công");
                        break;

                }
            }
        });
    }

    private void updateCreditCardUser(String msg) {
        final Integer updateCreditCardUser = userQuery.updateCreditCardUser(Common.currentUserModel);
        if (updateCreditCardUser != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update credit card failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentUserPayment() {
        if (Common.currentUserModel.getCreditCard().equals(CreditCardType.EMPTY.name())) {
            rbEmpty.setChecked(true);
        } else if (Common.currentUserModel.getCreditCard().equals(CreditCardType.ZALO.name())) {
            rbZaloPay.setChecked(true);
        } else if (Common.currentUserModel.getCreditCard().equals(CreditCardType.MOMO.name())) {
            rbMoMo.setChecked(true);
        } else if (Common.currentUserModel.getCreditCard().equals(CreditCardType.MASTERCARD.name())) {
            rbMasterCard.setChecked(true);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}