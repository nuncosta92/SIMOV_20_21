package isep.simov.project.simov;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BdHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bd-gestStore.db";
    private static final String TAG = "DBHelper";
    private static final int VERSION = 1;
    private String createStatement = "";
    private String dropStatement = "";
    private String tableName;
    private Context context;


    public BdHelper(Context context, String tableName, String fields) {
        super(context, tableName, null, VERSION);
        this.createStatement  = "CREATE TABLE IF NOT EXISTS ";
        this.createStatement += tableName + " (";
        this.createStatement += fields + ");";
        this.tableName = tableName;
        dropStatement= "DROP TABLE IF EXISTS " + this.tableName;
        this.context = context;
    }

    public BdHelper(Fragment fragment, String tableName, String fields) {
        super(fragment.getContext(), tableName, null, VERSION);
        this.createStatement  = "CREATE TABLE IF NOT EXISTS ";
        this.createStatement += tableName + " (";
        this.createStatement += fields + ");";
        this.tableName = tableName;
        dropStatement= "DROP TABLE IF EXISTS " + this.tableName;
        this.context = fragment.getContext();
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        Log.i(TAG, "onCreate: " + this.createStatement);
        arg0.execSQL(this.createStatement);
        if (tableName == "STORES"){
            insertData(arg0);
        }
        else if (tableName == "SUPPLIERS"){
            insertDataFornecedores(arg0);
        }
        else if (tableName == "ITEMS"){
            insertDataItems(arg0);
        }
        else if (tableName == "STORE_MANAGEMENT"){
            insertDataStoreManagement(arg0);
        }
        else if (tableName == "ORDERS"){
            insertDataStoreManagement(arg0);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        Log.i(TAG, "onUpgrade: " + this.dropStatement);
        arg0.execSQL(this.dropStatement);
        onCreate(arg0);
    }


    public void insertData(SQLiteDatabase db) {
        String sql;
        try {
            InputStream in = this.context.getResources().openRawResource(R.raw.data_stores);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);
            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                sql = "INSERT INTO " + this.tableName + " " + statements.item(i).getChildNodes().item(0).getNodeValue();
                db.execSQL(sql);
                sql = "";
            }
        } catch (Throwable t) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertDataFornecedores(SQLiteDatabase db) {
        String sql;
        try {
            InputStream in = this.context.getResources().openRawResource(R.raw.data_suppliers);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);
            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                sql = "INSERT INTO " + this.tableName + " " + statements.item(i).getChildNodes().item(0).getNodeValue();
                db.execSQL(sql);
                sql = "";
            }
        } catch (Throwable t) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertDataItems(SQLiteDatabase db) {
        String sql;
        try {
            InputStream in = this.context.getResources().openRawResource(R.raw.data_items);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);
            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                sql = "INSERT INTO " + this.tableName + " " + statements.item(i).getChildNodes().item(0).getNodeValue();
                db.execSQL(sql);
                sql = "";
            }
        } catch (Throwable t) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertDataStoreManagement(SQLiteDatabase db) {
        String sql;
        try {
            InputStream in = this.context.getResources().openRawResource(R.raw.data_store_managment);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);
            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                sql = "INSERT INTO " + this.tableName + " " + statements.item(i).getChildNodes().item(0).getNodeValue();
                db.execSQL(sql);
                sql = "";
            }
        } catch (Throwable t) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertOrders(SQLiteDatabase db) {
        String sql;
        try {
            InputStream in = this.context.getResources().openRawResource(R.raw.orders);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in, null);
            NodeList statements = doc.getElementsByTagName("statement");
            for (int i = 0; i < statements.getLength(); i++) {
                sql = "INSERT INTO " + this.tableName + " " + statements.item(i).getChildNodes().item(0).getNodeValue();
                db.execSQL(sql);
                sql = "";
            }
        } catch (Throwable t) {
            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
