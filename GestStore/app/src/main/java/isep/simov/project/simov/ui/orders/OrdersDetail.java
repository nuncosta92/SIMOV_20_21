package isep.simov.project.simov.ui.orders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.items.ItemsCreate;
import isep.simov.project.simov.ui.items.ItemsList;
import isep.simov.project.simov.ui.stores.StoresDetail;

public class OrdersDetail extends Activity {


    private static final String TAG = "OrdersDetail";
    private String id;

    private Boolean deleteFlag = false;

    DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_detail);

        id = getIntent().getStringExtra("ORDER_ID");

        Button deleteButton = (Button) findViewById(R.id.btDelete);
        deleteButton.setOnClickListener(deleteButtonListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Read from the database

        TextView order_id = (TextView) findViewById(R.id.tvOrdersIdData);
        TextView order_store = (TextView) findViewById(R.id.tvOrdersLojaData);
        TextView order_qtd = (TextView) findViewById(R.id.tvOrdersStockData);
        TextView order_item = (TextView) findViewById(R.id.tvOrdersItemData);
        TextView order_forncedor = (TextView) findViewById(R.id.tvOrdersFornecedorData);




        mPostReference = FirebaseDatabase.getInstance().getReference().child("orders").child("order " + id);
         DatabaseReference storesDB = FirebaseDatabase.getInstance().getReference("stores");
         DatabaseReference itemsDB = FirebaseDatabase.getInstance().getReference("items");
         DatabaseReference supsDB = FirebaseDatabase.getInstance().getReference("suppliers");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!deleteFlag) {
                    // Get Post object and use the values to update the UI
                    String id = dataSnapshot.getValue(Orders.class).getId();
                    String store = dataSnapshot.getValue(Orders.class).getStore();
                    String qtd = dataSnapshot.getValue(Orders.class).getQtd();
                    String item = dataSnapshot.getValue(Orders.class).getItem();
                    String fornecedor = dataSnapshot.getValue(Orders.class).getFornecedor();
                    //Orders order = dataSnapshot.getValue(Orders.class);
                    Orders order = new Orders(id, store, item, qtd, fornecedor);
                    // [START_EXCLUDE]
                    order_id.setText(order.getId());

                    storesDB.child("store " + order.getStore()).child("nome").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String storeNome = snapshot.getValue(String.class);
                            order_store.setText(storeNome);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("The read failed: " + error.getCode());
                        }
                    });

                    order_qtd.setText(order.getQtd());

                    itemsDB.child("item " + order.getItem()).child("designacao").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String itemNome = snapshot.getValue(String.class);
                            order_item.setText(itemNome);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("The read failed: " + error.getCode());
                        }
                    });


                    supsDB.child("supplier " + order.getFornecedor()).child("nome").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String supNome = snapshot.getValue(String.class);
                            order_forncedor.setText(supNome);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("The read failed: " + error.getCode());
                        }
                    });
                    // [END_EXCLUDE]
                } else {
                    deleteFlag = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(OrdersDetail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    private View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteFlag = true;

            Context context = getApplicationContext();


            mPostReference.removeValue();

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, "Order deleted successfully", duration);
            toast.show();
            finish();
        }
    };
}