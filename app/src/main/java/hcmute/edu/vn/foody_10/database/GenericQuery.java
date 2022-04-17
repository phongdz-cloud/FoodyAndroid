package hcmute.edu.vn.foody_10.database;

import java.util.List;

import hcmute.edu.vn.foody_10.mapper.RowMapper;

public interface GenericQuery<T> {
    List<T> query(String sql, RowMapper<T> rowMapper);

    Long insert(String sql, Object... parameters);

    Integer update(String sql, Object... parameters);

    T findById(String sql, RowMapper<T> rowMapper);

    Integer delete(String sql, Integer id);
}
