package isep.simov.project.simov.ui.items;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Items {

    public String id;
    public String designacao;
    public String codigo_barras;
    public String preco;
    public String unid_medida;

    public Items() {
    }

    public Items(String id, String designacao, String codigo_barras, String preco, String unid_medida) {
        this.id = id;
        this.designacao = designacao;
        this.codigo_barras = codigo_barras;
        this.preco = preco;
        this.unid_medida = unid_medida;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("designacao", designacao);
        result.put("codigo_barras", codigo_barras);
        result.put("preco", preco);
        result.put("unid_medida", unid_medida);

        return result;
    }
    // [END post_to_map]

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getUnid_medida() {
        return unid_medida;
    }

    public void setUnid_medida(String unid_medida) {
        this.unid_medida = unid_medida;
    }

    public String toString() {
        String item = id + " " + designacao;

        return item;
    }

    protected boolean validateId() {
        if (this.id.length() < 1 || Integer.parseInt(this.id ) <= 0)
            return false;

        return true;
    }

    protected boolean validateCodBarras() {
        if (this.codigo_barras.length() != 13)
            return false;

        return true;
    }

    protected boolean validatePreco() {
        if (this.preco.length() < 1 || Float.parseFloat(this.preco) <= 0)
            return false;

        return true;
    }

    public boolean validateItem() {
        if (!validateId() || !validateCodBarras() || !validatePreco())
            return false;

        return true;
    }
}
