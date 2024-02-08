package isep.simov.project.simov.ui.suppliers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.db.SupsBD;
import isep.simov.project.simov.ui.items.Items;
import isep.simov.project.simov.ui.items.ItemsDetail;
import isep.simov.project.simov.ui.stores.StoresCreate;
import isep.simov.project.simov.ui.stores.StoresDetail;

public class SuppliersDetail extends Activity {

    protected String
            sup_morada1,
            sup_cidade1,
            sup_email1,
            sup_tlm1,
            sup_tlf1;

    private boolean cellClick = false;

    private static final String TAG = "SuppliersDetail";
    private String id;

    DatabaseReference mPostReference;


    private Boolean deleteFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suppliers_detail);


        // Capture our button from layout
        Button deleteButton = (Button) findViewById(R.id.btDelete);
        Button editButton = (Button) findViewById(R.id.btEdit);
        deleteButton.setOnClickListener(deleteButtonListener);
        editButton.setOnClickListener(editButtonListener);

        id = getIntent().getStringExtra("SUPS_ID");
    }

    @Override
    public void onStart() {
        super.onStart();
        // Read from the database

        TextView sup_id = (TextView) findViewById(R.id.tvId);
        TextView sup_name = (TextView) findViewById(R.id.tvNome);
        TextView sup_morada = (TextView) findViewById(R.id.tvMorada);
        TextView sup_localidade = (TextView) findViewById(R.id.tvLocalidade);
        TextView sup_cidade = (TextView) findViewById(R.id.tvCidade);
        TextView sup_email = (TextView) findViewById(R.id.tvEmail);
        sup_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        TextView sup_tlm = (TextView) findViewById(R.id.tvTlm);
        sup_tlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCellNumber();
            }
        });

        TextView sup_tlf = (TextView) findViewById(R.id.tvTlf);
        sup_tlf.setOnClickListener(new View.OnClickListener() {
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
        TextView sup_tPagamento = (TextView) findViewById(R.id.tvTPagamamento);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("suppliers").child("supplier " + id);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!deleteFlag) {
                    // Get Post object and use the values to update the UI
                    String id = dataSnapshot.getValue(Suppliers.class).getId();
                    String name = dataSnapshot.getValue(Suppliers.class).getNome();
                    String morada = dataSnapshot.getValue(Suppliers.class).getMorada();
                    String localidade = dataSnapshot.getValue(Suppliers.class).getLocalidade();
                    String cidade = dataSnapshot.getValue(Suppliers.class).getCidade();
                    String email = dataSnapshot.getValue(Suppliers.class).getEmail();
                    String tlm = dataSnapshot.getValue(Suppliers.class).getTlm();
                    String tlf = dataSnapshot.getValue(Suppliers.class).getTlf();
                    String tPagamento = dataSnapshot.getValue(Suppliers.class).getTpagamento();

                    Suppliers suppliers = new Suppliers (id, name, morada, localidade, cidade, email, tlm, tlf, tPagamento);
                    // [START_EXCLUDE]
                    sup_id.setText(suppliers.id);
                    sup_name.setText(suppliers.nome);
                    sup_morada.setText(suppliers.morada);
                    sup_localidade.setText(suppliers.localidade);
                    sup_cidade.setText(suppliers.cidade);
                    sup_email.setText(suppliers.email);
                    sup_tlm.setText(suppliers.tlm);
                    sup_tlf.setText(suppliers.tlf);
                    sup_tPagamento.setText(suppliers.tpagamento);
                    sup_email1 = sup_email.getText().toString();
                    sup_tlm1 = sup_tlm.getText().toString();
                    sup_tlf1 = sup_tlf.getText().toString();
                    sup_cidade1 = sup_cidade.getText().toString();
                    sup_morada1 = sup_morada.getText().toString();
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
                Toast.makeText(SuppliersDetail.this, "Failed to load post.",
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
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + sup_tlf1));
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
                    ActivityCompat.requestPermissions(SuppliersDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + sup_tlm1));
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

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { sup_email1 });
                startActivity(Intent.createChooser(emailIntent, ""));

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
                    ActivityCompat.requestPermissions(SuppliersDetail.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    return;
                }

                Intent intentMaps = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + sup_morada1.replaceAll(" ", "+") + "," + sup_cidade1.replaceAll(" ", "+") + ""));
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
            Toast toast = Toast.makeText(context, "Supplier deleted successfully", duration);
            toast.show();
            finish();
        }
    };


    private View.OnClickListener editButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            Context context = getApplicationContext();

            Intent intent = new Intent(getApplicationContext(), SuppliersCreate.class);
            intent.putExtra("SUPS_ID", id);
            startActivity(intent);
            finish();
        }
    };

}
