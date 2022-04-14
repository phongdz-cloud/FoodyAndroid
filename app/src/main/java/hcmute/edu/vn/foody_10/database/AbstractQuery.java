package hcmute.edu.vn.foody_10.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.MainActivity;
import hcmute.edu.vn.foody_10.common.QueryType;
import hcmute.edu.vn.foody_10.mapper.RowMapper;

public class AbstractQuery<T> implements GenericQuery<T> {

    private final Database database = MainActivity.database;

    private SQLiteStatement setParameter(String sql, Object... parameters) {
        SQLiteStatement statement = database.getWritableDatabase().compileStatement(sql);
        statement.clearBindings();
        int index = 0;
        for (int i = 0; i < parameters.length; i++) {
            Object obj = parameters[i];
            index = i + 1;
            if (obj instanceof Long) {
                statement.bindLong(index, (Long) obj);
            } else if (obj instanceof String) {
                statement.bindString(index, (String) obj);
            } else if (obj instanceof byte[]) {
                statement.bindBlob(index, (byte[]) obj);
            } else if (obj instanceof Double) {
                statement.bindDouble(index, (Double) obj);
            }
        }
        return statement;
    }


    @Override
    public Object query(String sql, RowMapper<T> rowMapper, QueryType queryType, Object... parameters) {
        String error = "";

        try {
            switch (queryType) {
                case SELECT:
                    List<T> results = new ArrayList<>();
                    Cursor cursor = database.getReadableDatabase().rawQuery(sql, null);
                    while (cursor.moveToNext()) {
                        results.add(rowMapper.mapRow(cursor));
                    }
                    cursor.close();
                    return results;
                case INSERT:
                    SQLiteStatement statementInsert = setParameter(sql, parameters);
                    Long id = statementInsert.executeInsert();
                    statementInsert.close();
                    return id;
                case UPDATE:
                case DELETE:
                    SQLiteStatement statementUpdateOrDelete = setParameter(sql, parameters);
                    Integer idUpdateOrDelete = statementUpdateOrDelete.executeUpdateDelete();
                    statementUpdateOrDelete.close();
                    return idUpdateOrDelete;
                default:
                    return null;
            }
        } catch (Exception ex) {
            Log.e("query", error + " : " + ex.getMessage());
            return null;
        } finally {
            database.close();
        }
    }

}
