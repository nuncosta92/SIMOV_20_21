package isep.simov.project.simov.ui.orders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
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
import isep.simov.project.simov.ui.email.Email;

public class OrdersCreate extends Activity {

    private EditText order_id,
            order_item,
            order_store,
            order_fornecedor,
            order_qtd;

    private boolean orderValidation = false;
    private boolean itemValidation = false;
    private boolean storeValidation = false;
    private boolean fornecedorValidation = false;


    private static final String TAG = "OrdersCreate";
    private String id2;
    private Email emailData = new Email();

    DatabaseReference mPostReference;

    private DatabaseReference storesDB;
    private DatabaseReference itemsDB;
    private DatabaseReference supsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_create);

        id2 = getIntent().getStringExtra("ORDER_ID");

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

        order_id = (EditText) findViewById(R.id.tvOrdersIdData);
        order_item = (EditText) findViewById(R.id.tvOrdersItemData);
        order_store = (EditText) findViewById(R.id.tvOrdersStoreData);
        order_fornecedor = (EditText) findViewById(R.id.tvOrdersFornecedorData);
        order_qtd = (EditText) findViewById(R.id.tvOrdersQtdData);


        mPostReference = FirebaseDatabase.getInstance().getReference("orders");
        storesDB = FirebaseDatabase.getInstance().getReference("stores");
        itemsDB = FirebaseDatabase.getInstance().getReference("items");
        supsDB = FirebaseDatabase.getInstance().getReference("suppliers");

        order_id.addTextChangedListener(new TextWatcher() {
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
                    orderValidation = true;

                mPostReference.child("order " + s).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && id2 == null) {
                            order_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            orderValidation = false;
                        } else {
                            order_id.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            orderValidation = true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        order_item.addTextChangedListener(new TextWatcher() {
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
                            order_item.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = true;
                        } else {
                            order_item.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            itemValidation = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        order_store.addTextChangedListener(new TextWatcher() {
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
                            order_store.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = true;
                        } else {
                            order_store.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                            storeValidation = false;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        order_fornecedor.addTextChangedListener(new TextWatcher() {
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
                            order_fornecedor.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                            fornecedorValidation = true;
                        } else {
                            order_fornecedor.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
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
                    String item = dataSnapshot.child("order " + id2).getValue(Orders.class).getItem();
                    String store = dataSnapshot.child("order " + id2).getValue(Orders.class).getStore();
                    String fornecedor = dataSnapshot.child("order " + id2).getValue(Orders.class).getFornecedor();
                    String qtd = dataSnapshot.child("order " + id2).getValue(Orders.class).getQtd();

                    Orders order = new Orders(id2, store, item, qtd, fornecedor);

                    order_id.setText(order.getId());
                    order_id.setEnabled(false);
                    order_store.setText(order.getStore());
                    order_item.setText(order.getItem());
                    order_qtd.setText(order.getQtd());
                    order_fornecedor.setText(order.getFornecedor());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(OrdersCreate.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    public void prepareEmail(String id, String st, String item, String fornecedor) {
        try {

            System.out.println("ID: " + id);
            System.out.println("Store: " + st);
            System.out.println("Item: " + item);
            System.out.println("SUPP: " + fornecedor);

            if (Build.VERSION.SDK_INT > 22) {

                storesDB.child("store " + st).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String storeNome = snapshot.child("nome").getValue(String.class);
                        String storeMorada = snapshot.child("morada").getValue(String.class);
                        String storeLocalidade = snapshot.child("localidade").getValue(String.class);
                        String storeTlf = snapshot.child("tlf").getValue(String.class);
                        String storeTlm = snapshot.child("tlm").getValue(String.class);

                        emailData.setStoreNome(storeNome);
                        emailData.setStoreMorada(storeMorada);
                        emailData.setStoreLocalidade(storeLocalidade);
                        emailData.setStoreTlf(storeTlf);
                        emailData.setStoreTlm(storeTlm);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                    }
                });

                itemsDB.child("item " + item).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String itemCodBarras = snapshot.child("codigo_barras").getValue(String.class);

                        emailData.setItemCodBarras(itemCodBarras);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                    }
                });


                supsDB.child("supplier " + fornecedor).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String supEmail = snapshot.child("email").getValue(String.class);

                        emailData.setSupEmail(supEmail);
                        sendEmail();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendEmail() {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailData.getSupEmail()});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Encomenda do Produto " + emailData.getItemCodBarras() + " - " + emailData.getStoreNome());
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Pedido de encomenda.\n" + "Loja: " + emailData.getStoreNome() + "\n\t"
                    + "Morada: " + emailData.getStoreMorada() + "\n\t" + "Localidade: " + emailData.getStoreLocalidade() + "\n\t"
                    + "Tlf: " + emailData.getStoreTlf() + "\n\t" + "Tlm: " + emailData.getStoreTlm() + "\n\n"
                    + "Item " + emailData.getItemCodBarras() + "\n\t" + "Quantity: " + emailData.getQtd() + "\n\n"
                    + "Obrigado.");
            startActivity(emailIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private View.OnClickListener createButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (!orderValidation|| !itemValidation || !storeValidation || !fornecedorValidation) {
                errorMessage(null);
                return;
            }

            // Get Post object and use the values to update the UI
            String id = order_id.getText().toString();
            String item = order_item.getText().toString();
            String store = order_store.getText().toString();
            String fornecedor = order_fornecedor.getText().toString();
            String qtd = order_qtd.getText().toString();

            Orders order = new Orders(id, store, item, qtd, fornecedor);

            if (!order.validateOrder()) {
                errorMessage(order);
                return;
            }

            String key = mPostReference.push().getKey();

            Map<String, Object> orderValues = order.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/order " + id + "/", orderValues);

            mPostReference.updateChildren(childUpdates);


            if (id2 != null) {
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Order updated successfully", duration);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), OrdersDetail.class);
                intent.putExtra("ORDER_ID", id);
                startActivity(intent);
                finish();
            } else {
                System.out.println("PREPARE EMAIL START");
                prepareEmail(id, store, item, fornecedor);
                System.out.println("PREPARE EMAIL STOP");
                System.out.println("SEND EMAIL START");


                System.out.println("\n Store:\n" + emailData.getStoreNome());
                System.out.println(emailData.getStoreMorada());
                System.out.println(emailData.getStoreLocalidade());
                System.out.println(emailData.getStoreTlf());
                System.out.println(emailData.getStoreTlm());
                System.out.println("Item:\n" + emailData.getItemCodBarras());
                System.out.println("Supp:\n" + emailData.getSupEmail());


                emailData.setQtd(qtd);
                //sendEmail(qtd);
                System.out.println("SEND EMAIL STOP");
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, "Order created successfully", duration);
                toast.show();
                finish();
            }
        }
    };

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            if (id2 != null) {
                Intent intent = new Intent(getApplicationContext(), OrdersDetail.class);
                intent.putExtra("ORDER_ID", id2);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
            finish();
        }
    };

    private void errorMessage(Orders order) {
        if (order == null) {
            if (!orderValidation) {
                order_id.setError("O id já existe!");
            }
            if (!itemValidation) {
                order_item.setError("Insira um item id existente!");
            }
            if (!storeValidation) {
                order_store.setError("Insira uma loja existente!");
            }
            if (!fornecedorValidation) {
                order_fornecedor.setError("Insira um fornecedor existente!");
            }
        } else {
            if (!order.validateId()) {
                order_id.setError("Insira um id válido!");
            }
            if (!order.validateItem()) {
                order_item.setError("Insira um item válido!");
            }
            if (!order.validateStore()) {
                order_store.setError("Insira uma loja válida!");
            }
            if (!order.validateFornecedor()) {
                order_fornecedor.setError("Insira um fornecedor válido!");
            }
            if (!order.validateQtd()) {
                order_qtd.setError("Insira uma quantidade válida!");
            }
        }
    }
}
