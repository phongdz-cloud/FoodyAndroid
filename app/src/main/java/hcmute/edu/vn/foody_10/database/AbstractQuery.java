package hcmute.edu.vn.foody_10.database;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.foody_10.MainActivity;
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
            } else if (obj instanceof Integer) {
                statement.bindLong(index, (Integer) obj);
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


    @SuppressLint("Recycle")
    @Override
    public List<T> query(String sql, RowMapper<T> rowMapper) {
        List<T> results = new ArrayList<>();
        Cursor cursor = null;
        SQLiteDatabase dbQuery = null;
        try {
            dbQuery = this.database.getReadableDatabase();
            cursor = dbQuery.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                results.add(rowMapper.mapRow(cursor));
            }
            return results;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public Long insert(String sql, Object... parameters) {
        try (SQLiteStatement statementInsert = setParameter(sql, parameters)) {
            return statementInsert.executeInsert();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer update(String sql, Object... parameters) {

        try (SQLiteStatement statementUpdate = setParameter(sql, parameters)) {
            return statementUpdate.executeUpdateDelete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public T findById(String sql, RowMapper<T> rowMapper) {
        Cursor cursor = null;
        try {
            SQLiteDatabase dbQuery = this.database.getReadableDatabase();
            cursor = dbQuery.rawQuery(sql, null);
            cursor.moveToNext();
            return rowMapper.mapRow(cursor);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public Integer delete(String sql, Integer id)   {
        try (SQLiteStatement statementDelete = setParameter(sql, id)) {
            return statementDelete.executeUpdateDelete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
