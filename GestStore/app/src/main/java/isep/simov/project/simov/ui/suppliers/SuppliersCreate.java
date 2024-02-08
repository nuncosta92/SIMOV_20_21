package isep.simov.project.simov.ui.suppliers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SuppliersCreate extends Activity {
    private TextView sup_id,
            sup_nome,
            sup_morada,
            sup_cidade,
            sup_localidade,
            sup_email,
            sup_tlm,
            sup_tlf;

    private Spinner sup_tpagamento;

    private ArrayAdapter<CharSequence> adapter;

    private boolean suppValidation = false;

    private static final String TAG = "SuppliersCreate";
    private String id2;


    DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suppliers_create);

        id2 = getIntent().getStringExtra("SUPS_ID");

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

        sup_id = (EditText) findViewById(R.id.tvSupplierIdData);
        sup_nome = (EditText) findViewById(R.id.tvSupplierNomeData);
        sup_morada = (EditText) findViewById(R.id.tvSupplierMoradaData);
        sup_localidade = (EditText) findViewById(R.id.tvSupplierLocalidadeData);
        sup_cidade = (EditText) findViewById(R.id.tvSupplierCidadeData);
        sup_email = (EditText) findViewById(R.id.tvSupplierEmailData);
        sup_tlm = (EditText) findViewById(R.id.tvSupplierTlmData);
        sup_tlf = (EditText) findViewById(R.id.tvSupplierTlfData);
        sup_tpagamento = findViewById(R.id.tvSupplierTlmData2);


        adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sup_tpagamento.setAdapter(adapter);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("suppliers");

        sup_id.addTextChangedListener(new TextWatcher() {
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
                    suppValidation = true;

                mPostReference.child("supplier " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && id2 == null) {
                            sup_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            suppValidation = false;
                        } else {
                            sup_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            suppValidation = true;
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
                    String nome = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getNome();
                    String morada = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getMorada();
                    String localidade = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getLocalidade();
                    String cidade = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getCidade();
                    String email = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getEmail();
                    String tlm = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getTlm();
                    String tlf = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getTlf();
                    String tpagamento = dataSnapshot.child("supplier " + id2).getValue(Suppliers.class).getTpagamento();

                    Suppliers sup = new Suppliers(id2, nome, morada, localidade, cidade, email, tlm, tlf, tpagamento);

                    sup_id.setText(sup.id);
                    sup_id.setEnabled(false);
                    sup_nome.setText(sup.nome);
                    sup_morada.setText(sup.morada);
                    sup_localidade.setText(sup.localidade);
                    sup_cidade.setText(sup.cidade);
                    sup_email.setText(sup.email);
                    sup_tlf.setText(sup.tlf);
                    sup_tlm.setText(sup.tlm);

                    switch (tpagamento) {
                        case "Transferência Bancária":
                            sup_tpagamento.setSelection(4);
                            break;
                        case "Cheque":
                            sup_tpagamento.setSelection(3);
                            break;
                        case "90 dias":
                            sup_tpagamento.setSelection(2);
                            break;
                        case "60 dias":
                            sup_tpagamento.setSelection(1);
                            break;
                        default:
                            sup_tpagamento.setSelection(0);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(SuppliersCreate.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (!suppValidation) {
                errorMessage(null);
                return;
            }

            // Get Post object and use the values to update the UI
            String id = sup_id.getText().toString();
            String nome = sup_nome.getText().toString();
            String morada = sup_morada.getText().toString();
            String localidade = sup_localidade.getText().toString();
            String cidade = sup_cidade.getText().toString();
            String email = sup_email.getText().toString();
            String tlf = sup_tlf.getText().toString();
            String tlm = sup_tlm.getText().toString();
            String tpagamento = sup_tpagamento.getSelectedItem().toString();

            Suppliers sup = new Suppliers(id, nome, morada, localidade, cidade, email, tlm, tlf, tpagamento);

            if (!sup.validateSupplier()) {
                errorMessage(sup);
                return;
            }


            String key = mPostReference.push().getKey();

            Map<String, Object> itemValues = sup.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/supplier " + id + "/", itemValues);

            mPostReference.updateChildren(childUpdates);


            if (id2 != null) {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Supplier updated successfully", duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), SuppliersDetail.class);
                intent.putExtra("SUPS_ID", id);
                startActivity(intent);
                finish();
            } else {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Supplier created successfully", duration);
                toast.show();
                finish();
            }
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (id2 != null) {
                Intent intent = new Intent(getApplicationContext(), SuppliersDetail.class);
                intent.putExtra("SUPS_ID", id2);
                startActivity(intent);
                finish();
            } else {
                finish();
            }


            finish();
        }
    };

    private void errorMessage(Suppliers supp) {
        if (supp == null) {
            if (!suppValidation) {
                sup_id.setError("O id já existe!");
            }
        } else {
            if (!supp.validateId()) {
                sup_id.setError("Insira um id válido!");
            }
            if (!supp.validateNome()) {
                sup_nome.setError("Insira um nome!");
            }
            if (!supp.validateMorada()) {
                sup_morada.setError("Insira uma morada!");
            }
            if (!supp.validateLocalidade()) {
                sup_localidade.setError("Insira uma localidade!");
            }
            if (!supp.validateCidade()) {
                sup_cidade.setError("Insira uma cidade!");
            }
            if (!supp.validateTlf()) {
                sup_tlf.setError("Insira um número de telefone válido!");
            }
            if (!supp.validateTlm()) {
                sup_tlm.setError("Insira um número de telemóvel válido!");
            }
            if (!supp.validateEmail()) {
                sup_email.setError("Insira um email!");
            }
        }
    }
}