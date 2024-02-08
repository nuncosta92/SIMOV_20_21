package isep.simov.project.simov.ui.stores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import isep.simov.project.simov.R;

public class ListViewAdapterStores extends BaseAdapter {

    private final List<Stores> items;

    public ListViewAdapterStores(final List<Stores> items) {
        this.items = items;
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int arg0) {
        return this.items.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        final Stores row = this.items.get(arg0);
        View itemView = null;

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) arg2.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.fragment_stores, null);
        } else {
            itemView = arg1;
        }

        // Set the text of the row
        TextView txtId = (TextView) itemView.findViewById(R.id.storeId);
        txtId.setText(row.getId());

        TextView store_name = (TextView) itemView.findViewById(R.id.storeNome);
        store_name.setText(row.getNome());

        TextView store_tlf = (TextView) itemView.findViewById(R.id.storeTlf);
        store_tlf.setText(row.getTlf());

        TextView store_localidade = (TextView) itemView.findViewById(R.id.storeLocalidade);
        store_localidade.setText(row.getLocalidade());

        return itemView;
    }

}
