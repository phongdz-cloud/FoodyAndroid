package hcmute.edu.vn.foody_10.Database;

import java.util.List;

import hcmute.edu.vn.foody_10.Mapper.OrderMapper;
import hcmute.edu.vn.foody_10.Model.OrderModel;

public class OrderQuery extends AbstractQuery<OrderModel> implements IOrderQuery {
    private static IOrderQuery instance = null;

    public static IOrderQuery getInstance() {
        if (instance == null) {
            instance = new OrderQuery();
        }
        return instance;
    }

    @Override
    public Long insert(OrderModel orderModel) {
        final String sql = "INSERT INTO orders VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
        return insert(sql, orderModel.getPhotoFood(), orderModel.getCount(), orderModel.getFoodName()
                , orderModel.getFoodDescription(), orderModel.getPrice(), orderModel.getProductId(), orderModel.getUserId());
    }


    @Override
    public Integer updateCount(OrderModel orderModel) {
        final String sql = "UPDATE orders SET count = ? WHERE id = ?";
        return update(sql, orderModel.getCount(), orderModel.getId());
    }

    @Override
    public Integer deleteOrder(Integer id) {
        final String sql = "DELETE FROM orders WHERE id = ?";
        return delete(sql, id);
    }

    @Override
    public OrderModel findById(Integer id) {
        final String sql = "SELECT * FROM orders WHERE id = " + id;
        return findById(sql, new OrderMapper());
    }

    @Override
    public OrderModel findByProductId(Integer productId) {
        final String sql = "SELECT * FROM orders WHERE product_id = " + productId;
        final List<OrderModel> orderModels = query(sql, new OrderMapper());
        return orderModels.size() > 0 ? orderModels.get(0) : null;
    }



    @Override
    public OrderModel findByProductIdAndUserId(Integer productId, Integer userId) {
        final String sql = "SELECT * FROM orders WHERE product_id = " + productId + " and" +
                " user_id = " + userId;
        final List<OrderModel> orderModels = query(sql, new OrderMapper());
        return orderModels.size() > 0 ? orderModels.get(0) : null;
    }


    @Override
    public List<OrderModel> findAll() {
        final String sql = "SELECT * FROM orders";
        return query(sql, new OrderMapper());
    }

    @Override
    public List<OrderModel> findOrderByUserId(Integer userId) {
        final String sql = "SELECT * FROM orders WHERE user_id = " + userId;
        return query(sql, new OrderMapper());
    }
}
