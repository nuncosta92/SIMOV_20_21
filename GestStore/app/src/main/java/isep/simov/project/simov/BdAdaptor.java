package isep.simov.project.simov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import isep.simov.project.simov.ui.stores.StoresFragment;

public class BdAdaptor {

    private BdHelper dbHelper;
    private SQLiteDatabase database;



    public BdAdaptor open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

}
