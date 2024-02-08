package isep.simov.project.simov.ui.store_management;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class StoreManagements {
    private String id;
    private String store;
    private String item;
    private String stock;
    private String fornecedor;

    public StoreManagements(String id, String store, String item, String stock, String fornecedor) {
        this.id = id;
        this.store = store;
        this.item = item;
        this.stock = stock;
        this.fornecedor = fornecedor;
    }

    public StoreManagements() {
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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
        result.put("stock", stock);
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

    protected boolean validateStock() {
        if (this.stock.length() < 1 || Integer.parseInt(this.stock) <= 0)
            return false;

        return true;
    }

    public boolean validateStoreManagement() {
        if (!validateId() || !validateStore() || !validateItem() || !validateFornecedor() || !validateStock())
            return false;

        return true;
    }
}
