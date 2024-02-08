package isep.simov.project.simov.ui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import isep.simov.project.simov.BdHelper;
import isep.simov.project.simov.ui.orders.Orders;

public class OrdersBD {
    public static final String TABLE = "ORDERS";

    private BdHelper dbHelper;


    private static final String ID = "ID";
    private static final String STORE = "STORE";
    private static final String ITEM = "ITEM";
    private static final String QTD = "QTD";
    private static final String FORNECEDOR = "FORNECEDOR";

    public OrdersBD(Fragment f) {
        dbHelper = new BdHelper(f, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + STORE + " INTEGER," +
                ITEM + " INTEGER," + QTD + " INTEGER," + FORNECEDOR + " INTEGER");
    }

    public OrdersBD(Context context) {
        dbHelper = new BdHelper(context, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + STORE + " INTEGER," +
                ITEM + " INTEGER," + QTD + " INTEGER," + FORNECEDOR + " INTEGER");
    }

    public OrdersBD() {

    }

    //All CRUD Operations (Create, Read, Update and Delete)
    public boolean insertOrders( String store, String item, Integer stock, String fornecedor) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues initialValues = new ContentValues();
            initialValues.put(STORE, store);
            initialValues.put(ITEM, item);
            initialValues.put(QTD, stock);
            initialValues.put(FORNECEDOR, fornecedor);
            db.close();
        } catch (SQLException sqlerror) {
            Log.v("Insert into table error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrders(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ID = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("ORDERS", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrdersByStore(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "STORE = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("ORDERS", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrdersByItem(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ITEM = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("ORDERS", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrdersBySup(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "FORNECEDOR = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("ORDERS", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public Orders getOrder(int id) {
        Orders orders = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String s = "SELECT * FROM " + TABLE + " WHERE " + ID+ "=" + id;
        Cursor cursor = db.rawQuery(s, null);
        if(cursor != null) {
            cursor.moveToFirst();
            orders = new Orders(cursor.getString(0), cursor.getString(1),
                                cursor.getString(2), cursor.getString(3),
                                cursor.getString(4));
            cursor.close();
        }
        db.close();
        return orders;
    }

    public ArrayList<Orders> getOrders() {
        String query = "SELECT * FROM " + TABLE;
        ArrayList<Orders> orders = new ArrayList<Orders>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                orders.add(new Orders(cursor.getString(0), cursor.getString(1),
                                      cursor.getString(2), cursor.getString(3),
                                      cursor.getString(4)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return orders;
    }


}
