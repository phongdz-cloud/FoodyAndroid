package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.ReceiptModel;

public interface IReceiptQuery extends GenericQuery<ReceiptModel> {

    Long insert(ReceiptModel receiptModel);

    List<ReceiptModel> findAll();

    List<ReceiptModel> findReceiptForUserID(Integer userId);

    List<ReceiptModel> findReceiptForCode(String code);

}
