package isep.simov.project.simov.ui.store_management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import isep.simov.project.simov.R;

public class StoresManagementCreate extends Activity {

    private EditText editId,
            editStore,
            editItem,
            editStock,
            editFornecedor;

    private boolean smValidation = false;
    private boolean itemValidation = false;
    private boolean storeValidation = false;
    private boolean fornecedorValidation = false;


    private static final String TAG = "StoreManagementCreate";
    private String id2;

    DatabaseReference mPostReference;

    private DatabaseReference storesDB;
    private DatabaseReference itemsDB;
    private DatabaseReference supsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_management_create);

        id2 = getIntent().getStringExtra("STORE_MANAGEMENT_ID");

        // Capture our createButton from layout
        Button createButton = (Button) findViewById(R.id.btCreate);
        Button cancelButton = (Button) findViewById(R.id.btCancel);

        if (id2 != null)
            createButton.setText("Atualizar");
        else
            createButton.setText("Criar");

        createButton.setOnClickListener(createButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Read from the database

        editId = (EditText) findViewById(R.id.tvSMIdData);
        editStore = (EditText) findViewById(R.id.tvSMStoreData);
        editItem = (EditText) findViewById(R.id.tvSMItemData);
        editStock = (EditText) findViewById(R.id.tvSMStockData);
        editFornecedor = (EditText) findViewById(R.id.tvSMFornecedorData);


        mPostReference = FirebaseDatabase.getInstance().getReference().child("store_managements");
        storesDB = FirebaseDatabase.getInstance().getReference("stores");
        itemsDB = FirebaseDatabase.getInstance().getReference("items");
        supsDB = FirebaseDatabase.getInstance().getReference("suppliers");

        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();

                if (s.length() < 1)
                    smValidation = true;

                mPostReference.child("store_management " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && id2 == null) {
                            editId.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            smValidation = false;
                        } else {
                            editId.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            smValidation = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        editItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();

                if (s.length() < 1)
                    itemValidation = true;

                itemsDB.child("item " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            editItem.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = true;
                        } else {
                            editItem.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        editStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();

                if (s.length() < 1)
                    storeValidation = true;

                storesDB.child("store " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            editStore.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = true;
                        } else {
                            editStore.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        editFornecedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();

                if (s.length() < 1)
                    fornecedorValidation = true;

                supsDB.child("supplier " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            editFornecedor.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            fornecedorValidation = true;
                        } else {
                            editFornecedor.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            fornecedorValidation = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (id2 != null) {
                    // Get Post object and use the values to update the UI
                    String item = dataSnapshot.child("store_management " + id2).getValue(StoreManagements.class).getItem();
                    String store = dataSnapshot.child("store_management " + id2).getValue(StoreManagements.class).getStore();
                    String fornecedor = dataSnapshot.child("store_management " + id2).getValue(StoreManagements.class).getFornecedor();
                    String stock = dataSnapshot.child("store_management " + id2).getValue(StoreManagements.class).getStock();

                    StoreManagements sm = new StoreManagements(id2, store, item, stock, fornecedor);

                    editId.setText(sm.getId());
                    editId.setEnabled(false);
                    editStore.setText(sm.getStore());
                    editItem.setText(sm.getItem());
                    editStock.setText(sm.getStock());
                    editFornecedor.setText(sm.getFornecedor());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(StoresManagementCreate.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (!smValidation || !itemValidation || !storeValidation || !fornecedorValidation) {
                errorMessage(null);
                return;
            }

            // Get Post object and use the values to update the UI
            String id = editId.getText().toString();
            String item = editItem.getText().toString();
            String store = editStore.getText().toString();
            String fornecedor = editFornecedor.getText().toString();
            String stock = editStock.getText().toString();

            StoreManagements sm = new StoreManagements(id, store, item, stock, fornecedor);

            if (!sm.validateStoreManagement()) {
                errorMessage(sm);
                return;
            }

            String key = mPostReference.push().getKey();

            Map<String, Object> smValues = sm.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/store_management " + id + "/", smValues);

            mPostReference.updateChildren(childUpdates);


            if (id2 != null) {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "StoreManagement updated successfully", duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), StoreManagementsDetail.class);
                intent.putExtra("STORE_MANAGEMENT_ID", id);
                startActivity(intent);
                finish();
            } else {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "StoreManagement created successfully", duration);
                toast.show();
                finish();
            }
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (id2 != null) {
                Intent intent = new Intent(getApplicationContext(), StoreManagementsDetail.class);
                intent.putExtra("STORE_MANAGEMENT_ID", id2);
                startActivity(intent);
                finish();
            } else {
                finish();
            }


            finish();
        }
    };

    private void errorMessage(StoreManagements sm) {
        if (sm == null) {
            if (!smValidation) {
                editId.setError("O id já existe!");
            }
            if (!itemValidation) {
                editItem.setError("Insira um item id existente!");
            }
            if (!storeValidation) {
                editStore.setError("Insira uma loja existente!");
            }
            if (!fornecedorValidation) {
                editFornecedor.setError("Insira um fornecedor existente!");
            }
        } else {
            if (!sm.validateId()) {
                editId.setError("Insira um id válido!");
            }
            if (!sm.validateItem()) {
                editItem.setError("Insira um item válido!");
            }
            if (!sm.validateStore()) {
                editStore.setError("Insira uma loja válida!");
            }
            if (!sm.validateFornecedor()) {
                editFornecedor.setError("Insira um fornecedor valido!");
            }
            if (!sm.validateStock()) {
                editStock.setError("Insira um stock válido!");
            }
        }
    }
}