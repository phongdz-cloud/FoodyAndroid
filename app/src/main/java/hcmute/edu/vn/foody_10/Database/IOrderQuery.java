package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Model.OrderModel;

public interface IOrderQuery extends GenericQuery<OrderModel> {
    Long insert(OrderModel orderModel);

    Integer updateCount(OrderModel orderModel);

    Integer deleteOrder(Integer id);

    OrderModel findById(Integer id);

    OrderModel findByProductId(Integer productId);

    OrderModel findByProductIdAndUserId(Integer productId, Integer userId);

    List<OrderModel> findAll();

    List<OrderModel> findOrderByUserId(Integer userId);

}
