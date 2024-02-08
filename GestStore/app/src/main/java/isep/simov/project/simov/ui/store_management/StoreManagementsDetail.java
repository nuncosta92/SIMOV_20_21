package isep.simov.project.simov.ui.store_management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.items.ItemsCreate;
import isep.simov.project.simov.ui.items.ItemsList;

public class StoreManagementsDetail extends Activity {

    private static final String TAG = "StoreManagementsDetail";
    private String id;

    DatabaseReference mPostReference;

    private Boolean deleteFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_management_detail);

        id = getIntent().getStringExtra("STORE_MANAGEMENT_ID");

        // Capture our button from layout
        Button deleteButton = (Button) findViewById(R.id.btDelete);
        Button editButton = (Button) findViewById(R.id.btEdit);
        deleteButton.setOnClickListener(deleteButtonListener);
        editButton.setOnClickListener(editButtonListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Read from the database

        TextView sm_id = (TextView) findViewById(R.id.tvStoreManagementIdData);
        TextView sm_store = (TextView) findViewById(R.id.tvStoreManagementLojaData);
        TextView sm_stock = (TextView) findViewById(R.id.tvStoreManagementStockData);
        TextView sm_item = (TextView) findViewById(R.id.tvStoreManagementItemData);
        TextView sm_forncedor = (TextView) findViewById(R.id.tvStoreManagementFornecedorData);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("store_managements").child("store_management " + id);

        DatabaseReference storesDB = FirebaseDatabase.getInstance().getReference("stores");
        DatabaseReference itemsDB = FirebaseDatabase.getInstance().getReference("items");
        DatabaseReference supsDB = FirebaseDatabase.getInstance().getReference("suppliers");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!deleteFlag) {
                    // Get Post object and use the values to update the UI
                    String id = dataSnapshot.getValue(StoreManagements.class).getId();
                    String store = dataSnapshot.getValue(StoreManagements.class).getStore();
                    String stock = dataSnapshot.getValue(StoreManagements.class).getStock();
                    String item = dataSnapshot.getValue(StoreManagements.class).getItem();
                    String fornecedor = dataSnapshot.getValue(StoreManagements.class).getFornecedor();
                    if (!id.isEmpty()) {
                        StoreManagements sm = new StoreManagements(id, store, item, stock, fornecedor);
                        // [START_EXCLUDE]
                        sm_id.setText(sm.getId());

                        storesDB.child("store " + sm.getStore()).child("nome").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String storeNome = snapshot.getValue(String.class);
                                sm_store.setText(storeNome);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("The read failed: " + error.getCode());
                            }
                        });

                        sm_stock.setText(sm.getStock());

                        itemsDB.child("item " + sm.getItem()).child("designacao").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String itemNome = snapshot.getValue(String.class);
                                sm_item.setText(itemNome);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("The read failed: " + error.getCode());
                            }
                        });


                        supsDB.child("supplier " + sm.getFornecedor()).child("nome").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String supNome = snapshot.getValue(String.class);
                                sm_forncedor.setText(supNome);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                System.out.println("The read failed: " + error.getCode());
                            }
                        });
                        // [END_EXCLUDE]
                    }
                }
                else {
                    deleteFlag = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(StoreManagementsDetail.this, "Failed to load post.",
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
            Toast toast = Toast.makeText(context, "Store Management deleted successfully", duration);
            toast.show();
            finish();
        }
    };

    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            Intent intent = new Intent(getApplicationContext(), StoresManagementCreate.class);
            intent.putExtra("STORE_MANAGEMENT_ID", id);
            startActivity(intent);
            finish();
        }
    };
}