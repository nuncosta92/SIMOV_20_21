package isep.simov.project.simov.ui.items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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

public class ItemsCreate extends Activity {

    private TextView item_id,
            item_descricao,
            item_preco,
            item_codBarras,
            item_undMedi;

    private boolean itemValidation = false;

    private static final String TAG = "ItemsCreate";
    private String id2;


    DatabaseReference mPostReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_create);

        id2 = getIntent().getStringExtra("ITEM_ID");

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

        item_id = (EditText) findViewById(R.id.tvItemIdData);
        item_descricao = (EditText) findViewById(R.id.tvItemDescricaoData);
        item_preco = (EditText) findViewById(R.id.tvItemPrecoData);
        item_codBarras = (EditText) findViewById(R.id.tvItemCodigoBarrasData);
        item_undMedi = (EditText) findViewById(R.id.tvItemUnidadeMedidaData);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("items");

        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint = 10;
            final int maxDigitsAfterDecimalPoint = 2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"

                )) {
                    if (source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        item_preco.setFilters(new InputFilter[] { filter });


        item_id.addTextChangedListener(new TextWatcher() {
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

                mPostReference.child("item " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && id2 == null) {
                            item_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = false;
                        } else {
                            item_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        item_codBarras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();

                if (s.length() != 13)
                    item_codBarras.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                else
                    item_codBarras.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (id2 != null) {
                    // Get Post object and use the values to update the UI
                    String codigo_barras = dataSnapshot.child("item " + id2).getValue(Items.class).getCodigo_barras();
                    String designacao = dataSnapshot.child("item " + id2).getValue(Items.class).getDesignacao();
                    String preco = dataSnapshot.child("item " + id2).getValue(Items.class).getPreco();
                    String unid_medida = dataSnapshot.child("item " + id2).getValue(Items.class).getUnid_medida();

                    Items item = new Items(id2, designacao, codigo_barras, preco, unid_medida);

                    item_id.setText(item.id);
                    item_id.setEnabled(false);
                    item_descricao.setText(item.designacao);
                    item_preco.setText(item.preco);
                    item_codBarras.setText(item.codigo_barras);
                    item_undMedi.setText(item.unid_medida);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(ItemsCreate.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (!itemValidation) {
                errorMessage(null);
                return;
            }

            // Get Post object and use the values to update the UI
            String id = item_id.getText().toString();
            String codigo_barras = item_codBarras.getText().toString();
            String designacao = item_descricao.getText().toString();
            String preco = item_preco.getText().toString();
            String unid_medida = item_undMedi.getText().toString();

            Items item = new Items(id, designacao, codigo_barras, preco, unid_medida);

            if (!item.validateItem()) {
                errorMessage(item);
                return;
            }

            String key = mPostReference.push().getKey();

            Map<String, Object> itemValues = item.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/item " + id + "/", itemValues);

            mPostReference.updateChildren(childUpdates);


            if (id2 != null) {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Item updated successfully", duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), ItemsDetail.class);
                intent.putExtra("ITEM_ID", id);
                startActivity(intent);
                finish();
            } else {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Item created successfully", duration);
                toast.show();
                finish();
            }
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (id2 != null) {
                Intent intent = new Intent(getApplicationContext(), ItemsDetail.class);
                intent.putExtra("ITEM_ID", id2);
                startActivity(intent);
                finish();
            } else {
                finish();
            }

            finish();
        }
    };

    private void errorMessage(Items item) {
        if (item == null) {
            item_id.setError("Insira um id válido!");
        } else {
            if (!item.validateId()) {
                item_id.setError("Insira um id válido!");
            }
            if (!item.validateCodBarras()) {
                item_codBarras.setError("Insira um código de barras válido!");
            }
            if (!item.validatePreco()) {
                item_preco.setError("Insira um preço válido!");
            }
        }
    }
}

