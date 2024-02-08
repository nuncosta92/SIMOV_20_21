package isep.simov.project.simov.ui.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import isep.simov.project.simov.R;

public class ListViewAdapterItems extends BaseAdapter {

    private final List<Items> items;

    public ListViewAdapterItems(final List<Items> items) {
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
        final Items row = this.items.get(arg0);
        View itemView = null;

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) arg2.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.fragment_items, null);
        } else {
            itemView = arg1;
        }

        // Set the text of the row
        TextView txtId = (TextView) itemView.findViewById(R.id.itemId);
        txtId.setText(row.getId());

        TextView item_desginacao = (TextView) itemView.findViewById(R.id.itemDesignacao);
        item_desginacao.setText(row.getDesignacao());

        TextView item_codigo = (TextView) itemView.findViewById(R.id.itemCodigo);
        item_codigo.setText(row.getCodigo_barras());

        return itemView;
    }

}
