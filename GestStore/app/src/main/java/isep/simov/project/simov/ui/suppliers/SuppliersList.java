 package isep.simov.project.simov.ui.suppliers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import isep.simov.project.simov.R;

 public class SuppliersList extends AppCompatActivity {
    private ListView listView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    ArrayList<Suppliers> myArrayList = new ArrayList<Suppliers>();

    ArrayAdapter<Suppliers> myArrayAdapter;
     private Button btCreate;

    private ListViewAdapterSuppliers listViewAdapter;

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("suppliers");


        btCreate = (Button) findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent x = new Intent(SuppliersList.this, SuppliersCreate.class);
                startActivity(x);
            }
        });

        listView = (ListView) findViewById(R.id.lists);
        listViewAdapter = new ListViewAdapterSuppliers(myArrayList);

        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String nome = ds.child("nome").getValue(String.class);
                    String morada = ds.child("morada").getValue(String.class);
                    String localidade = ds.child("localidade").getValue(String.class);
                    String cidade = ds.child("cidade").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    String tlm = ds.child("tlm").getValue(String.class);
                    String tlf = ds.child("tlf").getValue(String.class);
                    String tipoPagamento = ds.child("tipoPagamento").getValue(String.class);
                    Suppliers sups = new Suppliers(id, nome, morada, localidade, cidade, email, tlm, tlf, tipoPagamento);
                    myArrayList.add(sups);
                    myArrayAdapter = new ArrayAdapter<Suppliers>(SuppliersList.this,android.R.layout.simple_list_item_1,myArrayList);
                    listView.setAdapter(listViewAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        /*!
                         * Callback method to be invoked when an item in this
                         * AdapterView has been clicked.
                         * Implementers can call getItemAtPosition(position)
                         * if they need to access the data associated with
                         * the selected item.
                         * Parameters
                         * 	arg0 	The AdapterView where the click happened.
                         *  arg1 	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
                         *  arg2 	The position of the view in the adapter.
                         *  arg3 	The row id of the item that was clicked.
                         */
                        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                            Intent intent = new Intent(getApplicationContext(), SuppliersDetail.class);
                            Suppliers s = (Suppliers) listViewAdapter.getItem(arg2);
                            intent.putExtra("SUPS_ID", s.getId());
                            startActivity(intent);
                        }
                    });
                    registerForContextMenu(listView);
                    Log.d("TAG", sups + " / " + id + " / " + nome + " / " + morada + " / " + localidade + " / " + cidade + " / " + email + " / " + tlm + " / " + tlf + " / " + tipoPagamento);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(eventListener);

    }

/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.suppliers_list, container, false);
        listView = (ListView) root.findViewById(R.id.listSups);

        return root;
    }*/
}