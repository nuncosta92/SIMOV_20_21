package isep.simov.project.simov.ui.store_management;

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

public class StoreManagementsList extends AppCompatActivity {
    private ListView listView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Button btCreate;

    ArrayList<StoreManagements> myArrayList = new ArrayList<StoreManagements>();

    ArrayAdapter<StoreManagements> myArrayAdapter;

    private ListViewAdapterStoreManagement listViewAdapter;

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
        databaseReference = firebaseDatabase.getReference("store_managements");


        listView = (ListView) findViewById(R.id.lists);
        listViewAdapter = new ListViewAdapterStoreManagement(myArrayList);

        btCreate = (Button) findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent x = new Intent(StoreManagementsList.this, StoresManagementCreate.class);
                startActivity(x);
            }
        });

        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id = ds.child("id").getValue(String.class);
                    String store = ds.child("store").getValue(String.class);
                    String item = ds.child("item").getValue(String.class);
                    String stock = ds.child("stock").getValue(String.class);
                    String fornecedor = ds.child("fornecedor").getValue(String.class);

                    StoreManagements sups = new StoreManagements(id, store, item, stock, fornecedor);
                    myArrayList.add(sups);
                    myArrayAdapter = new ArrayAdapter<StoreManagements>(StoreManagementsList.this,android.R.layout.simple_list_item_1,myArrayList);
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
                            Intent intent = new Intent(getApplicationContext(), StoreManagementsDetail.class);
                            StoreManagements s = (StoreManagements) listViewAdapter.getItem(arg2);
                            intent.putExtra("STORE_MANAGEMENT_ID", s.getId());
                            startActivity(intent);
                        }
                    });
                    registerForContextMenu(listView);
                    Log.d("TAG", id + " / " + store + " / " + item + " / " + stock + " / " + fornecedor);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(eventListener);

    }

/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.store_management_list, container, false);
        listView = (ListView) root.findViewById(R.id.listStoreManagements);

        return root;
    }*/
}