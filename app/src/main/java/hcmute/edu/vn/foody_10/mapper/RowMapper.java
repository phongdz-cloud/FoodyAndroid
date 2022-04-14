package hcmute.edu.vn.foody_10.mapper;

import android.database.Cursor;

public interface RowMapper <T>{
    T mapRow(Cursor cs);
}
