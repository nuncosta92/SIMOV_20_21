package isep.simov.project.simov.ui.suppliers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.stores.Stores;

public class ListViewAdapterSuppliers extends BaseAdapter {

    private final List<Suppliers> items;

    public ListViewAdapterSuppliers(final List<Suppliers> items) {
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
        final Suppliers row = this.items.get(arg0);
        View itemView = null;

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) arg2.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.fragment_suppliers, null);
        } else {
            itemView = arg1;
        }

        // Set the text of the row
        TextView txtId = (TextView) itemView.findViewById(R.id.supId);
        txtId.setText(row.getId());

        TextView sup_name = (TextView) itemView.findViewById(R.id.supNome);
        sup_name.setText(row.getNome());

        TextView sup_tlf = (TextView) itemView.findViewById(R.id.supTlf);
        sup_tlf.setText(row.getTlf());

        TextView sup_localidade = (TextView) itemView.findViewById(R.id.supLocalidade);
        sup_localidade.setText(row.getLocalidade());

        return itemView;
    }

}
