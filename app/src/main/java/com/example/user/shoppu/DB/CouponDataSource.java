package com.example.user.shoppu.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.shoppu.DB.Contracts.CouponContract;
import com.example.user.shoppu.DB.Helpers.CouponDBHelper;
import com.example.user.shoppu.models.Coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juangarcilazoortiz on 2/3/16.
 */
public class CouponDataSource {
    private SQLiteDatabase database;
    private CouponDBHelper couponDBHelper;
    private String[] allColumns = {CouponContract.COLUMN_NAME_COUPON};

    public CouponDataSource(Context context) {
        couponDBHelper = new CouponDBHelper(context);
    }

    public void open() throws SQLException {
        database = couponDBHelper.getWritableDatabase();
    }

    public void close() {
        couponDBHelper.close();
    }


    /**
     * Function oriented to insert a new Alumno into DataBase
     *
     * @param stdToInsert the student to be inserted in the db
     * @return row ID of the newly Alumno inserted row, or -1
     */
    public long insertCoupon(Coupon stdToInsert) {
        ContentValues values = new ContentValues();
        values.put(CouponContract.COLUMN_NAME_COUPON, stdToInsert.getDiscount());
        long newRowId;
        newRowId = database.insert(CouponContract.TABLE_NAME, null, values);
        return newRowId;
    }


    public long deleteCoupon(Coupon stdToDelete) {
        String whereClause = CouponContract._ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(stdToDelete.getId())};
        return database.delete(CouponContract.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * Function oriented to recovery all Alumno's rows from Database
     *
     * @return List of Alumnos from Database
     */
    public List<Coupon> getAllCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        Coupon coupon;
        Cursor cursor = database.query(CouponContract.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            coupon = cursorToAlumno(cursor);
            coupons.add(coupon);
            cursor.moveToNext();
        }
        cursor.close();
        return coupons;
    }

    private Coupon cursorToAlumno(Cursor cursor) {
        return new Coupon(cursor.getString(0));
    }

}
