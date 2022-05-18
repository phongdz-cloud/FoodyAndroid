package hcmute.edu.vn.foody_10.Mapper;

import android.database.Cursor;

import hcmute.edu.vn.foody_10.Model.OrderModel;

public class OrderMapper implements RowMapper<OrderModel> {
    @Override
    public OrderModel mapRow(Cursor cs) {
        OrderModel order = new OrderModel();
        order.setId(cs.getInt(0));
        order.setPhotoFood(cs.getBlob(1));
        order.setCount(cs.getInt(2));
        order.setFoodName(cs.getString(3));
        order.setFoodDescription(cs.getString(4));
        order.setPrice(cs.getFloat(5));
        order.setProductId(cs.getInt(6));
        order.setUserId(cs.getInt(7));
        return order;
    }
}
