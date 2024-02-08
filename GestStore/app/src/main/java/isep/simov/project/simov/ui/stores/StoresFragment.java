package isep.simov.project.simov.ui.stores;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.StoresBD;

public class StoresFragment extends Fragment {

    protected StoresBD adapter;
    private StoresViewModel storesViewModel;
    private ListView listStores;


    private ListViewAdapterStores listViewAdapter;
    ArrayList<Stores> store;

    @Override
    public void onStart() {
        super.onStart();

        adapter = new StoresBD(this);
        store = new ArrayList<Stores>();
        store.addAll(adapter.getStores());
        listViewAdapter = new ListViewAdapterStores(store);
        listStores.setAdapter(listViewAdapter);
        listStores.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intent = new Intent(getContext(), StoresDetail.class);
                Stores s = (Stores) listViewAdapter.getItem(arg2);
                intent.putExtra("STORE_ID", s.getId());
                startActivity(intent);
            }
        });
        registerForContextMenu(listStores);

    }
/*
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        storesViewModel = new ViewModelProvider(this).get(StoresViewModel.class);
        View root = inflater.inflate(R.layout.stores_list, container, false);

        listStores = (ListView) root.findViewById(R.id.lists);

        return root;
    }*/



}