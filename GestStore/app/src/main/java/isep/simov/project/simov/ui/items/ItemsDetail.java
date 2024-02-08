package isep.simov.project.simov.ui.items;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import isep.simov.project.simov.R;

public class ItemsDetail extends Activity {
    protected EditText editCodigoItem,
            editDescricaoItem,
            editCodigoBarrasItem,
            editPrecoItem,
            editUnidadeMedidaItem;


    private static final String TAG = "ItemsDetail";
    private String id;

    DatabaseReference mPostReference;

    private Boolean deleteFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_detail);

        id = getIntent().getStringExtra("ITEM_ID");

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

        TextView item_id = (TextView) findViewById(R.id.tvItemIdData);
        TextView item_descricao = (TextView) findViewById(R.id.tvItemDescricaoData);
        TextView item_preco = (TextView) findViewById(R.id.tvItemPrecoData);
        TextView item_codBarras = (TextView) findViewById(R.id.tvItemCodigoBarrasData);
        TextView item_undMedi = (TextView) findViewById(R.id.tvItemUnidadeMedidaData);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("items").child("item " + id);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!deleteFlag) {
                    // Get Post object and use the values to update the UI
                    String codigo_barras = dataSnapshot.getValue(Items.class).getCodigo_barras();
                    String designacao = dataSnapshot.getValue(Items.class).getDesignacao();
                    String id = dataSnapshot.getValue(Items.class).getId();
                    String preco = dataSnapshot.getValue(Items.class).getPreco().toString();
                    String unid_medida = dataSnapshot.getValue(Items.class).getUnid_medida();
                    //Items item = dataSnapshot.getValue(Items.class);
                    Items item = new Items(id, designacao, codigo_barras, preco, unid_medida);
                    // [START_EXCLUDE]
                    item_id.setText(item.id);
                    item_descricao.setText(item.designacao);
                    item_preco.setText(item.preco);
                    item_codBarras.setText(item.codigo_barras);
                    item_undMedi.setText(item.unid_medida);
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
                Toast.makeText(ItemsDetail.this, "Failed to load post.",
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
            Toast toast = Toast.makeText(context, "Item deleted successfully", duration);
            toast.show();
            finish();

        }
    };


    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            Intent intent = new Intent(getApplicationContext(), ItemsCreate.class);
            intent.putExtra("ITEM_ID", id);
            startActivity(intent);
            finish();
        }
    };
}
