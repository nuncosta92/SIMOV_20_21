package isep.simov.project.simov.ui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import isep.simov.project.simov.BdHelper;
import isep.simov.project.simov.ui.items.Items;
import isep.simov.project.simov.ui.store_management.StoreManagements;

public class StoreManagementBD {
    public static final String TABLE = "STORE_MANAGEMENT";

    private BdHelper dbHelper;


    private static final String ID = "ID";
    private static final String STORE = "STORE";
    private static final String ITEM = "ITEM";
    private static final String STOCK = "STOCK";
    private static final String FORNECEDOR = "FORNECEDOR";

    public StoreManagementBD(Fragment f) {
        dbHelper = new BdHelper(f, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + STORE + " INTEGER," +
                ITEM + " INTEGER," + STOCK + " INTEGER," + FORNECEDOR + " INTEGER");
    }

    public StoreManagementBD(Context context) {
        dbHelper = new BdHelper(context, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + STORE + " INTEGER," +
                ITEM + " INTEGER," + STOCK + " INTEGER," + FORNECEDOR + " INTEGER");
    }

    public StoreManagementBD() {

    }

    //All CRUD Operations (Create, Read, Update and Delete)
    public boolean insertStoreManagement( String store, String item, Integer stock, String fornecedor) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues initialValues = new ContentValues();
            initialValues.put(STORE, store);
            initialValues.put(ITEM, item);
            initialValues.put(STOCK, stock);
            initialValues.put(FORNECEDOR, fornecedor);
            db.close();
        } catch (SQLException sqlerror) {
            Log.v("Insert into table error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStoreManagements(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ID = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("STORE_MANAGEMENT", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStoreManByStore(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "STORE = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("STORE_MANAGEMENT", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStoreManByItem(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ITEM = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("STORE_MANAGEMENT", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteStoreManBySup(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "FORNECEDOR = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("STORE_MANAGEMENT", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public StoreManagements getStoreManagement(int id) {
        StoreManagements storeManagements = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String s = "SELECT * FROM " + TABLE + " WHERE " + ID+ "=" + id;
        Cursor cursor = db.rawQuery(s, null);
        if(cursor != null) {
            cursor.moveToFirst();
            storeManagements = new StoreManagements(cursor.getString(0), cursor.getString(1),
                                                    cursor.getString(2), cursor.getString(3), cursor.getString(4));
            cursor.close();
        }
        db.close();
        return storeManagements;
    }

    public ArrayList<StoreManagements> getStoreManagements() {
        String query = "SELECT * FROM " + TABLE;
        ArrayList<StoreManagements> storeManagements = new ArrayList<StoreManagements>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                storeManagements.add(new StoreManagements(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return storeManagements;
    }


}
