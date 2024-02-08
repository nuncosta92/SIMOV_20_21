package isep.simov.project.simov.ui.suppliers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.StoresBD;
import isep.simov.project.simov.ui.db.SupsBD;
import isep.simov.project.simov.ui.stores.ListViewAdapterStores;
import isep.simov.project.simov.ui.stores.Stores;
import isep.simov.project.simov.ui.stores.StoresDetail;

public class SuppliersFragment extends Fragment {

    protected SupsBD adapter;
    private SuppliersViewModel suppliersViewModel;
    private ListView listSups;

    private ListViewAdapterSuppliers listViewAdapter;
    ArrayList<Suppliers> sup;

    @Override
    public void onStart() {
        super.onStart();
        adapter = new SupsBD(this);
        sup = new ArrayList<Suppliers>();
        sup.addAll(adapter.getSuppliers());
        listViewAdapter = new ListViewAdapterSuppliers(sup);
        listSups.setAdapter(listViewAdapter);
        listSups.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intent = new Intent(getContext(), SuppliersDetail.class);
                Suppliers s = (Suppliers) listViewAdapter.getItem(arg2);
                intent.putExtra("SUP_ID", s.getId());
                startActivity(intent);
            }
        });
        registerForContextMenu(listSups);

    }


/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        suppliersViewModel = new ViewModelProvider(this).get(SuppliersViewModel.class);
        View root = inflater.inflate(R.layout.suppliers_list, container, false);

        listSups = (ListView) root.findViewById(R.id.listSups);

        return root;
    }*/
}