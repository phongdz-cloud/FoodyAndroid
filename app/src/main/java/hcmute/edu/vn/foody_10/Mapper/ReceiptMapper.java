package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.ReceiptModel;

public class ReceiptMapper implements RowMapper<ReceiptModel> {
    @Override
    public ReceiptModel mapRow(Cursor cs) {
        ReceiptModel receiptModel = new ReceiptModel();
        receiptModel.setId(cs.getInt(0));
        receiptModel.setUserId(cs.getInt(1));
        receiptModel.setProductId(cs.getInt(2));
        receiptModel.setTotalCount(cs.getInt(3));
        receiptModel.setTotalPrice(cs.getFloat(4));
        receiptModel.setCode(cs.getString(5));
        return receiptModel;
    }
}
