package com.example.user.shoppu.DB.Contracts;

import android.provider.BaseColumns;

/**
 * Created by juangarcilazoortiz on 2/3/16.
 */
public class CouponContract implements BaseColumns{
    public static final String TABLE_NAME = "Coupon";
    public static final String COLUMN_NAME_COUPON = "Discount";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_COUPON =
            "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_COUPON +TEXT_TYPE+
                    " )";

    public static final String SQL_DELETE_COUPONS =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
