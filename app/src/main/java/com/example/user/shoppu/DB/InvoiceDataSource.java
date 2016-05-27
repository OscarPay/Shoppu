package com.byhyuchiha.studentdb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.user.shoppu.DB.Contracts.InvoiceContract;
import com.example.user.shoppu.DB.Helpers.InvoiceDBHelper;
import com.example.user.shoppu.models.Invoice;

import java.util.HashMap;

/**
 * Created by byhyuchiha on 18/02/16.
 */
public class InvoiceDataSource {
    private SQLiteDatabase database;
    private InvoiceDBHelper invoiceDBHelper;
    private String[] allColumns = {InvoiceContract.COLUMN_NAME_DATE,
            InvoiceContract.COLUMN_NAME_USER_ID, InvoiceContract.COLUMN_NAME_TOTAL};

    public InvoiceDataSource(Context context) {
        invoiceDBHelper = new InvoiceDBHelper(context);
    }

    public void open() throws SQLException {
        database = invoiceDBHelper.getWritableDatabase();
    }

    public void close() {
        invoiceDBHelper.close();
    }

    public long insertInvoice(Invoice invoice) {
        ContentValues values = new ContentValues();
        values.put(InvoiceContract.COLUMN_NAME_DATE, invoice.getDate());
        values.put(InvoiceContract.COLUMN_NAME_TOTAL, invoice.getTotal());
        values.put(InvoiceContract.COLUMN_NAME_USER_ID, invoice.getUserId());
        long newRowId;
        newRowId = database.insert(InvoiceContract.TABLE_NAME, null, values);
        return newRowId;
    }

    private HashMap<String, String> getAllInvoice() {
        HashMap<String, String> users = new HashMap<>();
        Cursor cursor = database.query(InvoiceContract.TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.put(cursor.getString(0), cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    public boolean hasValidCredentials(String username, String password){
        HashMap<String, String> users = getAllInvoice();
        for(String key: users.keySet()){
            if(username.equals(username) && password.equals(users.get(key))){
                return true;
            }
        }
        return false;
    }
}
