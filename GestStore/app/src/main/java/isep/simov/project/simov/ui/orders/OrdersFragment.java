package isep.simov.project.simov.ui.orders;

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
import isep.simov.project.simov.ui.db.OrdersBD;

public class OrdersFragment extends Fragment {


    protected OrdersBD adapter;
    private OrdersViewModel ordersViewModel;
    private ListView listOrders;


    private ListViewAdapterOrders listViewAdapter;
    ArrayList<Orders> orders;

    @Override
    public void onStart() {
        super.onStart();

        adapter = new OrdersBD(this);
        orders = new ArrayList<Orders>();
        orders.addAll(adapter.getOrders());
        listViewAdapter = new ListViewAdapterOrders(orders);
        listOrders.setAdapter(listViewAdapter);
        listOrders.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
                Intent intent = new Intent(getContext(), OrdersDetail.class);
                Orders s = (Orders) listViewAdapter.getItem(arg2);
                intent.putExtra("ORDER_ID", s.getId());
                startActivity(intent);
            }
        });
        registerForContextMenu(listOrders);
    }

/*    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ordersViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        View root = inflater.inflate(R.layout.orders_list, container, false);

        listOrders = (ListView) root.findViewById(R.id.lists);

        return root;
    }*/
}
