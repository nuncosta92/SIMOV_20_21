package isep.simov.project.simov.ui.items;

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
import isep.simov.project.simov.ui.db.ItemsBD;

public class ItemsFragment extends Fragment {

    protected ItemsBD adapter;
    private ItemsViewModel itemsViewModel;
    private ListView listItems;


    private ListViewAdapterItems listViewAdapter;
    ArrayList<Items> item;

    @Override
    public void onStart() {
        super.onStart();

        adapter = new ItemsBD(this);
        item = new ArrayList<Items>();
        item.addAll(adapter.getItems());
        listViewAdapter = new ListViewAdapterItems(item);
        listItems.setAdapter(listViewAdapter);
        listItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intent = new Intent(getContext(), ItemsDetail.class);
                Items s = (Items) listViewAdapter.getItem(arg2);
                intent.putExtra("ITEM_ID", s.getId());
                startActivity(intent);
            }
        });
        registerForContextMenu(listItems);
    }

}