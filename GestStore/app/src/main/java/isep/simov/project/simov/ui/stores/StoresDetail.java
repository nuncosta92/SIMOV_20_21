package isep.simov.project.simov.ui.stores;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.db.StoresBD;
import isep.simov.project.simov.ui.items.ItemsCreate;
import isep.simov.project.simov.ui.items.ItemsDetail;
import isep.simov.project.simov.ui.items.ItemsList;

public class StoresDetail extends Activity implements OnMapReadyCallback {


    protected String
            store_morada1,
            store_cidade1,
            store_email1,
            store_tlm1,
            store_tlf1;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private boolean cellClick = false;

    private static final String TAG = "StoresDetail";
    private String id;

    DatabaseReference mPostReference;

    private Boolean deleteFlag = false;

    private MapView mMapView;

/*
    public LatLng  getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng  p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng ((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stores_detail);


        // Capture our button from layout
        Button deleteButton = (Button) findViewById(R.id.btDelete);
        Button editButton = (Button) findViewById(R.id.btEdit);
        deleteButton.setOnClickListener(deleteButtonListener);
        editButton.setOnClickListener(editButtonListener);

        id = getIntent().getStringExtra("STORE_ID");

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Geocoder geocoder = new Geocoder(getBaseContext());

        LatLng sydney = new LatLng(41.06748300792361, -8.640075039190553);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        // [START_EXCLUDE silent]
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,20.0f));
        // [END_EXCLUDE]
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        // Read from the database

        TextView store_id = (TextView) findViewById(R.id.tvId);
        TextView store_nome = (TextView) findViewById(R.id.tvNome);
        TextView store_morada = (TextView) findViewById(R.id.tvMorada);
        TextView store_localidade = (TextView) findViewById(R.id.tvLocalidade);
        TextView store_cidade = (TextView) findViewById(R.id.tvCidade);

        TextView store_email = (TextView) findViewById(R.id.tvEmail);
        store_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        TextView store_tlm = (TextView) findViewById(R.id.tvTlm);
        store_tlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCellNumber();
                cellClick = true;
            }
        });

        TextView store_tlf = (TextView) findViewById(R.id.tvTlf);
        store_tlf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });


        ImageButton btn_map = (ImageButton) findViewById(R.id.imgBtn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });


        mPostReference = FirebaseDatabase.getInstance().getReference().child("stores").child("store " + id);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!deleteFlag) {
                    // Get Post object and use the values to update the UI
                    String id = dataSnapshot.getValue(Stores.class).getId().toString();
                    String nome = dataSnapshot.getValue(Stores.class).getNome();
                    String morada = dataSnapshot.getValue(Stores.class).getMorada();
                    String localidade = dataSnapshot.getValue(Stores.class).getLocalidade();
                    String cidade = dataSnapshot.getValue(Stores.class).getCidade();
                    String email = dataSnapshot.getValue(Stores.class).getEmail();
                    String tlm = dataSnapshot.getValue(Stores.class).getTlm();
                    String tlf = dataSnapshot.getValue(Stores.class).getTlf();
                    //Stores store = dataSnapshot.getValue(Stores.class);
                    Stores store = new Stores(id, nome, morada, localidade, cidade, email, tlm, tlf);
                    // [START_EXCLUDE]
                    store_id.setText(store.getId());
                    store_nome.setText(store.getNome());
                    store_morada.setText(store.getMorada());
                    store_morada.setText(store.getMorada());
                    store_localidade.setText(store.getLocalidade());
                    store_cidade.setText(store.getCidade());
                    store_cidade1 = store_cidade.getText().toString();
                    store_email.setText(store.getEmail());
                    store_tlm.setText(store.getTlm());
                    store_tlf.setText(store.getTlf());
                    store_email1 = store_email.getText().toString();
                    store_tlm1 = store_tlm.getText().toString();
                    store_tlf1 = store_tlf.getText().toString();
                    store_cidade1 = store_cidade.getText().toString();
                    store_morada1 = store_morada.getText().toString();
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
                Toast.makeText(StoresDetail.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };

        mPostReference.addValueEventListener(postListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(requestCode == 101)
        {
            if (permissions[0].equals("android.permission.ACCESS_COARSE_LOCATION")){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openMaps();
                }
            }
            else if (permissions[0].equals("android.permission.CALL_PHONE")){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if (cellClick == true){
                        callCellNumber();
                        cellClick = false;
                    }
                    else
                        callPhoneNumber();
                }
            }
            else if (permissions[0].equals("android.permission.READ_CONTACTS")){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    sendEmail();
                }
            }

        }
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StoresDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + store_tlf1));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void callCellNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StoresDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + store_tlm1));
                startActivity(callIntent);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendEmail()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { store_email1 });
                startActivity(emailIntent);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void openMaps()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StoresDetail.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    return;
                }

                Intent intentMaps = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + store_morada1.replaceAll(" ", "+") + "," + store_cidade1.replaceAll(" ", "+") + ""));
                startActivity(intentMaps);

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            deleteFlag = true;

            Context context = getApplicationContext();


            mPostReference.removeValue();

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, "Store deleted successfully", duration);
            toast.show();
            finish();
        }
    };


    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            Intent intent = new Intent(getApplicationContext(), StoresCreate.class);
            intent.putExtra("STORE_ID", id);
            startActivity(intent);
            finish();
        }
    };

}