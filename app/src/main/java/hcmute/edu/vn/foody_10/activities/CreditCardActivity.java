package hcmute.edu.vn.foody_10.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody_10.R;
import hcmute.edu.vn.foody_10.common.Common;
import hcmute.edu.vn.foody_10.common.CreditCardType;
import hcmute.edu.vn.foody_10.database.IUserQuery;
import hcmute.edu.vn.foody_10.database.UserQuery;

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
                        Common.currentUser.setCreditCard(CreditCardType.EMPTY.name());
                        updateCreditCardUser("Select empty credit card successfully");
                        break;
                    case R.id.rbMoMo:
                        Common.currentUser.setCreditCard(CreditCardType.MOMO.name());
                        updateCreditCardUser("Select credit card MoMo successfully");
                        break;
                    case R.id.rbZaloPay:
                        Common.currentUser.setCreditCard(CreditCardType.ZALO.name());
                        updateCreditCardUser("Select credit card ZaloPay successfully");
                        break;
                    case R.id.rbMasterCard:
                        Common.currentUser.setCreditCard(CreditCardType.MASTERCARD.name());
                        updateCreditCardUser("Select empty credit card Master Card successfully");
                        break;

                }
            }
        });
    }

    private void updateCreditCardUser(String msg) {
        final Integer updateCreditCardUser = userQuery.updateCreditCardUser(Common.currentUser);
        if (updateCreditCardUser != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update credit card failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCurrentUserPayment() {
        if (Common.currentUser.getCreditCard().equals(CreditCardType.EMPTY.name())) {
            rbEmpty.setChecked(true);
        } else if (Common.currentUser.getCreditCard().equals(CreditCardType.ZALO.name())) {
            rbZaloPay.setChecked(true);
        } else if (Common.currentUser.getCreditCard().equals(CreditCardType.MOMO.name())) {
            rbMoMo.setChecked(true);
        } else if (Common.currentUser.getCreditCard().equals(CreditCardType.MASTERCARD.name())) {
            rbMasterCard.setChecked(true);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}