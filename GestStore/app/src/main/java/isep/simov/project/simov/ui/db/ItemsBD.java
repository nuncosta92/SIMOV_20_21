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

public class ItemsBD {
    private BdHelper dbHelper;

    private SQLiteDatabase database;

    private static final String TABLE = "ITEMS";
    private static final String ID = "ID";
    private static final String DESIGNACAO = "DESIGNACAO";
    private static final String CODIGO_BARRAS = "CODIGO_BARRAS";
    private static final String PRECO = "PRECO";
    private static final String UNIDADES_MEDIDA = "UNIDADES_MEDIDA";

    public ItemsBD(Fragment f) {
        dbHelper = new BdHelper(f, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DESIGNACAO + " TEXT," + CODIGO_BARRAS + " TEXT," + PRECO + " FLOAT," + UNIDADES_MEDIDA + " TEXT");
    }

    public ItemsBD(Context context) {
        dbHelper = new BdHelper(context, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DESIGNACAO + " TEXT," + CODIGO_BARRAS + " TEXT," + PRECO + " FLOAT," + UNIDADES_MEDIDA + " TEXT");
    }


    //All CRUD Operations (Create, Read, Update and Delete)
    public boolean insertItems( String designacao, String cod_barras, Float preco, String unid_medida) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues initialValues = new ContentValues();
            initialValues.put(DESIGNACAO, designacao);
            initialValues.put(CODIGO_BARRAS, cod_barras);
            initialValues.put(PRECO, preco);
            initialValues.put(UNIDADES_MEDIDA, unid_medida);
            db.insert(TABLE, null, initialValues);
            db.close();
        } catch (SQLException sqlerror) {
            Log.v("Insert into table error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteItems(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String[] whereArgs = new String[1];
            whereArgs[0] = id;

            String whereClause;


            //Delete on Items table
            whereClause = "ID = ?";
            db.delete("ITEMS", whereClause, whereArgs);

        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public Items getItem(int id) {
        Items item = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String s = "SELECT * FROM " + TABLE + " WHERE " + ID+ "=" + id;
        Cursor cursor = db.rawQuery(s, null);
        if(cursor != null) {
            cursor.moveToFirst();
            item = new Items(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));
            cursor.close();
        }
        db.close();
        return item;
    }

    public ArrayList<Items> getItems() {
        String query = "SELECT * FROM " + TABLE;
        ArrayList<Items> items = new ArrayList<Items>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                items.add(new Items(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return items;
    }


}
