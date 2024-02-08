package isep.simov.project.simov.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import isep.simov.project.simov.R;

public class ListViewAdapterOrders extends BaseAdapter {

    private final List<Orders> orders;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference storesDB;
    private DatabaseReference itemsDB;
    private DatabaseReference supsDB;

    public ListViewAdapterOrders(final List<Orders> orders) {
        this.orders = orders;
    }

    public int getCount() {
        return this.orders.size();
    }

    public Object getItem(int arg0) {
        return this.orders.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        final Orders row = this.orders.get(arg0);
        View ordersView = null;

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) arg2.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ordersView = inflater.inflate(R.layout.fragment_orders, null);
        } else {
            ordersView = arg1;
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        storesDB = firebaseDatabase.getReference("stores");
        itemsDB = firebaseDatabase.getReference("items");
        supsDB = firebaseDatabase.getReference("suppliers");

        // Set the text of the row
        TextView txtId = (TextView) ordersView.findViewById(R.id.ordersId);
        txtId.setText(row.getId());

        TextView orders_store = (TextView) ordersView.findViewById(R.id.ordersStore);
        storesDB.child("store " + row.getStore()).child("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String storeNome = snapshot.getValue(String.class);
                orders_store.setText(storeNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        TextView orders_item = (TextView) ordersView.findViewById(R.id.ordersItem);
        itemsDB.child("item " + row.getItem()).child("designacao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String itemNome = snapshot.getValue(String.class);
                orders_item.setText(itemNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        TextView orders_Stock = (TextView) ordersView.findViewById(R.id.ordersStock);
        orders_Stock.setText(row.getQtd());

        TextView orders_Fornecedor = (TextView) ordersView.findViewById(R.id.ordersFornecedor);
        supsDB.child("supplier " + row.getFornecedor()).child("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String supNome = snapshot.getValue(String.class);
                orders_Fornecedor.setText(supNome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });

        return ordersView;
    }

}
