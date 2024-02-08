package isep.simov.project.simov.ui.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import isep.simov.project.simov.BdHelper;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.stores.Stores;

public class StoresBD{
    private BdHelper dbHelper;

    private static final String TABLE = "STORES";
    private static final String ID = "ID";
    private static final String NOME = "NOME";
    private static final String MORADA = "MORADA";
    private static final String LOCALIDADE = "LOCALIDADE";
    private static final String CIDADE = "CIDADE";
    private static final String TLF = "TLF";
    private static final String TLM = "TLM";
    private static final String EMAIL = "EMAIL";



    public StoresBD(Fragment f) {
        dbHelper = new BdHelper(f, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOME + " TEXT," + MORADA + " TEXT," + LOCALIDADE + " TEXT," + CIDADE +
                " TEXT," + EMAIL + " TEXT," + TLF + " TEXT," + TLM + " TEXT");
    }

    public StoresBD(Context context) {
        dbHelper = new BdHelper(context, TABLE, ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOME + " TEXT," + MORADA + " TEXT," + LOCALIDADE + " TEXT," + CIDADE +
                " TEXT," + EMAIL + " TEXT," + TLF + " TEXT," + TLM + " TEXT");
    }

    public StoresBD() {

    }

    //All CRUD Operations (Create, Read, Update and Delete)
    public boolean insertStores( String nome, String morada, String localidade, String cidade, String email, int tlf, int tlm) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues initialValues = new ContentValues();
            initialValues.put(NOME, nome);
            initialValues.put(MORADA, morada);
            initialValues.put(LOCALIDADE, localidade);
            initialValues.put(CIDADE, cidade);
            initialValues.put(EMAIL, email);
            initialValues.put(TLF, tlf);
            initialValues.put(TLM, tlm);
            db.insert(TABLE, null, initialValues);
            db.close();
        } catch (SQLException sqlerror) {
            Log.v("Insert into table error", sqlerror.getMessage());
            return false;
        }
        return true;
    }

    public boolean apagarStores(String id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String whereClause = "ID = ?";
            String[] whereArgs = new String[1];
            whereArgs[0] = id;
            db.delete("STORES", whereClause, whereArgs);
        }catch (SQLException sqlerror){
            Log.v("Delete row error", sqlerror.getMessage());
            return false;
        }
        return true;
    }


    public Stores getStore(int id) {
        Stores store = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String s = "SELECT * FROM " + TABLE + " WHERE " + ID+ "=" + id;
        Cursor cursor = db.rawQuery(s, null);
        if(cursor != null) {
            cursor.moveToFirst();
            store = new Stores(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5) , cursor.getString(6), cursor.getString(7));
            cursor.close();
        }
        db.close();
        return store;
    }

    public ArrayList<Stores> getStores() {
        String query = "SELECT * FROM " + TABLE;
        ArrayList<Stores> store = new ArrayList<Stores>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                store.add(new Stores(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5) , cursor.getString(6), cursor.getString(7)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();
        return store;
    }


}
