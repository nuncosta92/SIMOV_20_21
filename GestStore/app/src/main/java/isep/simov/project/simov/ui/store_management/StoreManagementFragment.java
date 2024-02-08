package isep.simov.project.simov.ui.store_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.items.Items;

public class StoreManagementFragment extends Fragment {

    protected StoreManagementBD adapter;
    private StoreManagementViewModel storeManagementViewModel;
    private ListView listStoreManagement;


    private ListViewAdapterStoreManagement listViewAdapter;
    ArrayList<StoreManagements> storeManagements;

    @Override
    public void onStart() {
        super.onStart();

        adapter = new StoreManagementBD(this);
        storeManagements = new ArrayList<StoreManagements>();
        storeManagements.addAll(adapter.getStoreManagements());
        listViewAdapter = new ListViewAdapterStoreManagement(storeManagements);
        listStoreManagement.setAdapter(listViewAdapter);
        listStoreManagement.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intent = new Intent(getContext(), StoreManagementsDetail.class);
                StoreManagements s = (StoreManagements) listViewAdapter.getItem(arg2);
                intent.putExtra("STORE_MANAGEMENT_ID", s.getId());
                startActivity(intent);
            }
        });
        registerForContextMenu(listStoreManagement);
    }

/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        storeManagementViewModel = new ViewModelProvider(this).get(StoreManagementViewModel.class);
        View root = inflater.inflate(R.layout.store_management_list, container, false);

        listStoreManagement = (ListView) root.findViewById(R.id.listStoreManagements);

        return root;
    }*/


}