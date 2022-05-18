package hcmute.edu.vn.foody_10.Interface;

import hcmute.edu.vn.foody_10.Model.OrderModel;

public interface IOrderFood {
    void addCountOrder(OrderModel orderModel);

    void minusCountFoodOrDeleteOrder(OrderModel orderModel);
}
