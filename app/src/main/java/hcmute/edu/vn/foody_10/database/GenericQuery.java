package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.common.QueryType;
import hcmute.edu.vn.foody_10.mapper.RowMapper;

public interface GenericQuery<T> {
    Object query(String sql, RowMapper<T> rowMapper, QueryType queryType, Object... parameters);

}
