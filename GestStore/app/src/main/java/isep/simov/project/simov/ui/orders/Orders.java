package isep.simov.project.simov.ui.orders;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Orders {
    private String id;
    private String store;
    private String item;
    private String qtd;
    private String fornecedor;

    public Orders(String id, String store, String item, String qtd, String fornecedor) {
        this.id = id;
        this.store = store;
        this.item = item;
        this.qtd = qtd;
        this.fornecedor = fornecedor;
    }

    public Orders() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("store", store);
        result.put("item", item);
        result.put("qtd", qtd);
        result.put("fornecedor", fornecedor);

        return result;
    }
    // [END post_to_map]

    protected boolean validateId() {
        if (this.id.length() < 1 || Integer.parseInt(this.id) <= 0)
            return false;

        return true;
    }

    protected boolean validateStore() {
        if (this.store.length() < 1 || Integer.parseInt(this.store) <= 0)
            return false;

        return true;
    }

    protected boolean validateItem() {
        if (this.item.length() < 1 || Integer.parseInt(this.item) <= 0)
            return false;

        return true;
    }

    protected boolean validateFornecedor() {
        if (this.fornecedor.length() < 1 || Integer.parseInt(this.fornecedor) <= 0)
            return false;

        return true;
    }

    protected boolean validateQtd() {
        if (this.qtd.length() < 1 || Integer.parseInt(this.qtd) <= 0)
            return false;

        return true;
    }

    public boolean validateOrder() {
        if (!validateId() || !validateStore() || !validateItem() || !validateFornecedor() || !validateQtd())
            return false;

        return true;
    }
}
