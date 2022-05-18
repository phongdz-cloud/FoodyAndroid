package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Mapper.ReceiptMapper;
import hcmute.edu.vn.foody_10.Model.ReceiptModel;

public class ReceiptQuery extends AbstractQuery<ReceiptModel> implements IReceiptQuery {
    private static IReceiptQuery instance = null;

    public static IReceiptQuery getInstance() {
        if (instance == null) {
            instance = new ReceiptQuery();
        }
        return instance;
    }

    @Override
    public Long insert(ReceiptModel receiptModel) {
        final String sql = "INSERT INTO receipt VALUES (null, ?, ?, ?, ?, ?)";
        return insert(sql, receiptModel.getUserId(), receiptModel.getProductId()
                , receiptModel.getTotalCount(), receiptModel.getTotalPrice(), receiptModel.getCode());
    }

    @Override
    public List<ReceiptModel> findAll() {
        final String sql = "SELECT * FROM receipt";
        return query(sql, new ReceiptMapper());
    }

    @Override
    public List<ReceiptModel> findReceiptForUserID(Integer userId) {
        final String sql = "SELECT * FROM receipt WHERE user_id = " + userId;
        return query(sql, new ReceiptMapper());
    }

    @Override
    public List<ReceiptModel> findReceiptForCode(String code) {
        final String sql = "SELECT * FROM receipt WHERE code = '" + code + "'";
        return query(sql, new ReceiptMapper());
    }
}
