package isep.simov.project.simov.ui.store_management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.ItemsBD;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.db.StoresBD;
import isep.simov.project.simov.ui.db.SupsBD;
import isep.simov.project.simov.ui.items.Items;
import isep.simov.project.simov.ui.stores.Stores;
import isep.simov.project.simov.ui.suppliers.Suppliers;

public class ListViewAdapterStoreManagement extends BaseAdapter {

    private final List<StoreManagements> storeManagements;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference storesDB;
    private DatabaseReference itemsDB;
    private DatabaseReference supsDB;

    public ListViewAdapterStoreManagement(final List<StoreManagements> storeManagements) {
        this.storeManagements = storeManagements;
    }

    public int getCount() {
        return this.storeManagements.size();
    }

    public Object getItem(int arg0) {
        return this.storeManagements.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        final StoreManagements row = this.storeManagements.get(arg0);
        View storeManagementView = null;

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) arg2.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            storeManagementView = inflater.inflate(R.layout.fragment_store_management, null);
        } else {
            storeManagementView = arg1;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        storesDB = firebaseDatabase.getReference("stores");
        itemsDB = firebaseDatabase.getReference("items");
        supsDB = firebaseDatabase.getReference("suppliers");

        // Set the text of the row
        TextView txtId = (TextView) storeManagementView.findViewById(R.id.storeManagementId);
        txtId.setText(row.getId());

        TextView storeM_store = (TextView) storeManagementView.findViewById(R.id.storeManagementStore);
        storesDB.child("store " + row.getStore()).child("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String storeNome = snapshot.getValue(String.class);
                storeM_store.setText(storeNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        TextView storeM_item = (TextView) storeManagementView.findViewById(R.id.storeManagementItem);
        itemsDB.child("item " + row.getItem()).child("designacao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String itemNome = snapshot.getValue(String.class);
                storeM_item.setText(itemNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        TextView storeM_Stock = (TextView) storeManagementView.findViewById(R.id.storeManagementStock);
        storeM_Stock.setText(row.getStock());

        TextView storeM_Fornecedor = (TextView) storeManagementView.findViewById(R.id.storeManagementFornecedor);
        supsDB.child("supplier " + row.getFornecedor()).child("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String supNome = snapshot.getValue(String.class);
                storeM_Fornecedor.setText(supNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        return storeManagementView;
    }

}
