package isep.simov.project.simov.ui.stores;

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
import android.widget.TextView;
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
import isep.simov.project.simov.ui.items.Items;
import isep.simov.project.simov.ui.items.ItemsDetail;
import isep.simov.project.simov.ui.items.ItemsList;
import isep.simov.project.simov.ui.orders.Orders;

public class StoresCreate extends Activity {

    protected EditText store_id,
            store_nome,
            store_morada,
            store_localidade,
            store_cidade,
            store_email,
            store_tlm,
            store_tlf;

    private boolean storeValidation = false;

    private static final String TAG = "StoresCreate";
    private String id2;



    DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stores_create);

        id2 = getIntent().getStringExtra("STORE_ID");

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

        store_id = (EditText) findViewById(R.id.tvStoreIdData);
        store_nome = (EditText) findViewById(R.id.tvStoreNomeData);
        store_morada = (EditText) findViewById(R.id.tvStoreMoradaData);
        store_localidade = (EditText) findViewById(R.id.tvStoreLocalidadeData);
        store_cidade = (EditText) findViewById(R.id.tvStoreCidadeData);
        store_email = (EditText) findViewById(R.id.tvStoreEmailData);
        store_tlm = (EditText) findViewById(R.id.tvStoreTlmData);
        store_tlf = (EditText) findViewById(R.id.tvStoreTlfData);


        mPostReference = FirebaseDatabase.getInstance().getReference().child("stores");

        store_id.addTextChangedListener(new TextWatcher() {
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

                mPostReference.child("store " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && id2 == null) {
                            store_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = false;
                        } else {
                            store_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = true;
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
                    String nome = dataSnapshot.child("store "+ id2).getValue(Stores.class).getNome();
                    String morada = dataSnapshot.child("store "+ id2).getValue(Stores.class).getMorada();
                    String localidade = dataSnapshot.child("store "+ id2).getValue(Stores.class).getLocalidade();
                    String cidade = dataSnapshot.child("store "+ id2).getValue(Stores.class).getCidade();
                    String email = dataSnapshot.child("store "+ id2).getValue(Stores.class).getEmail();
                    String tlm = dataSnapshot.child("store "+ id2).getValue(Stores.class).getTlm();
                    String tlf = dataSnapshot.child("store "+ id2).getValue(Stores.class).getTlf();

                    Stores store = new Stores(id2, nome, morada, localidade, cidade, email, tlm, tlf);

                    store_id.setText(store.id);
                    store_id.setEnabled(false);
                    store_nome.setText(store.nome);
                    store_morada.setText(store.morada);
                    store_localidade.setText(store.localidade);
                    store_cidade.setText(store.cidade);
                    store_email.setText(store.email);
                    store_tlf.setText(store.tlf);
                    store_tlm.setText(store.tlm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(StoresCreate.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (!storeValidation) {
                errorMessage(null);
                return;
            }

            // Get Post object and use the values to update the UI
            String id = store_id.getText().toString();
            String nome = store_nome.getText().toString();
            String morada = store_morada.getText().toString();
            String localidade = store_localidade.getText().toString();
            String cidade = store_cidade.getText().toString();
            String email = store_email.getText().toString();
            String tlf = store_tlf.getText().toString();
            String tlm = store_tlm.getText().toString();

            Stores store = new Stores(id, nome, morada, localidade, cidade, email, tlm, tlf);

            if (!store.validateStore()) {
                errorMessage(store);
                return;
            }

            String key = mPostReference.push().getKey();

            Map<String, Object> itemValues = store.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/store " + id + "/", itemValues);

            mPostReference.updateChildren(childUpdates);


            if (id2 != null) {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Store updated successfully", duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), StoresDetail.class);
                intent.putExtra("STORE_ID", id);
                startActivity(intent);
                finish();
            }else {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Store created successfully", duration);
                toast.show();
                finish();
            }
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (id2 != null) {
                Intent intent = new Intent(getApplicationContext(), StoresDetail.class);
                intent.putExtra("STORE_ID", id2);
                startActivity(intent);
                finish();
            }else {
                finish();
            }


            finish();
        }
    };

    private void errorMessage(Stores store) {
        if (store == null) {
            if (!storeValidation) {
                store_id.setError("O id já existe!");
            }
        } else {
            if (!store.validateId()) {
                store_id.setError("Insira um id válido!");
            }
            if (!store.validateNome()) {
                store_nome.setError("Insira um nome!");
            }
            if (!store.validateMorada()) {
                store_morada.setError("Insira uma morada!");
            }
            if (!store.validateLocalidade()) {
                store_localidade.setError("Insira uma localidade!");
            }
            if (!store.validateCidade()) {
                store_cidade.setError("Insira uma cidade!");
            }
            if (!store.validateTlf()) {
                store_tlf.setError("Insira um número de telefone válido!");
            }
            if (!store.validateTlm()) {
                store_tlm.setError("Insira um número de telemóvel válido!");
            }
            if (!store.validateEmail()) {
                store_email.setError("Insira um email!");
            }
        }
    }
}
