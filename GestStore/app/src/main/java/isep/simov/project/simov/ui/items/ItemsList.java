package isep.simov.project.simov.ui.items;

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

public class ItemsList extends AppCompatActivity {
    private ListView listView;

    private Button btCreate;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private static final int REQUEST_CODE_1 = 1;

    ArrayList<Items> myArrayList = new ArrayList<Items>();

    ArrayAdapter<Items> myArrayAdapter;

    private ListViewAdapterItems listViewAdapter;

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
        databaseReference = firebaseDatabase.getReference("items");


        btCreate = (Button) findViewById(R.id.btCreate);
        btCreate.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent x = new Intent(ItemsList.this, ItemsCreate.class);
                startActivity(x);
            }
        });


        listView = (ListView) findViewById(R.id.lists);
        listViewAdapter = new ListViewAdapterItems(myArrayList);

        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String codigo_barras = ds.child("codigo_barras").getValue(String.class);
                    String designacao = ds.child("designacao").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
                    String preco = ds.child("preco").getValue(String.class);
                    String unid_medida = ds.child("unid_medida").getValue(String.class);

                    Items item = new Items(id, designacao, codigo_barras, preco, unid_medida);
                    myArrayList.add(item);
                    myArrayAdapter = new ArrayAdapter<Items>(ItemsList.this,android.R.layout.simple_list_item_1,myArrayList);
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
                            Intent intent = new Intent(getApplicationContext(), ItemsDetail.class);
                            Items s = (Items) listViewAdapter.getItem(arg2);
                            intent.putExtra("ITEM_ID", s.getId());
                            startActivity(intent);
                        }
                    });
                    registerForContextMenu(listView);
                    Log.d("TAG", codigo_barras + " / " + designacao + " / " + id + " / " + preco + " / " + unid_medida);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        databaseReference.addListenerForSingleValueEvent(eventListener);

    }

/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.items_list, container, false);
        listView = (ListView) root.findViewById(R.id.lists);
        return root;
    }*/

}