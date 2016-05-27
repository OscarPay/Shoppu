package com.example.user.shoppu.DB.Contracts;

import android.provider.BaseColumns;

/**
 * Created by byhyuchiha on 18/02/16.
 */
public class InvoiceContract implements BaseColumns{
    public static final String TABLE_NAME = "Invoice";
    public static final String COLUMN_NAME_DATE = "Date";
    public static final String COLUMN_NAME_USER_ID = "User_id";
    public static final String COLUMN_NAME_TOTAL = "Total";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_INVOICE =
            "CREATE TABLE " + TABLE_NAME + " (" +_ID + " INTEGER PRIMARY KEY,"+
                    COLUMN_NAME_DATE+TEXT_TYPE+COMMA_SEP+
                    COLUMN_NAME_USER_ID+TEXT_TYPE+COMMA_SEP+
                    COLUMN_NAME_TOTAL+TEXT_TYPE+
                    " )";

    public static final String SQL_DELETE_INVOICE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
