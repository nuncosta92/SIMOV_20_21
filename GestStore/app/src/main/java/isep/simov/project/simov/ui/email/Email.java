package isep.simov.project.simov.ui.email;

public class Email {
    public String storeNome;
    public String storeMorada;
    public String storeLocalidade;
    public String storeTlf;
    public String storeTlm;
    public String itemCodBarras;
    public String supEmail;
    public String qtd;

    public Email() {
    }

    public Email(String storeNome, String storeMorada, String storeLocalidade, String storeTlf, String storeTlm, String qtd) {
        this.storeNome = storeNome;
        this.storeMorada = storeMorada;
        this.storeLocalidade = storeLocalidade;
        this.storeTlf = storeTlf;
        this.storeTlm = storeTlm;
        this.qtd = qtd;
    }

    public Email(String storeNome, String storeMorada, String storeLocalidade, String storeTlf, String storeTlm, String itemCodBarras, String supEmail, String qtd) {
        this.storeNome = storeNome;
        this.storeMorada = storeMorada;
        this.storeLocalidade = storeLocalidade;
        this.storeTlf = storeTlf;
        this.storeTlm = storeTlm;
        this.itemCodBarras = itemCodBarras;
        this.supEmail = supEmail;
        this.qtd = qtd;
    }

    public String getStoreNome() {
        return storeNome;
    }

    public void setStoreNome(String storeNome) {
        this.storeNome = storeNome;
    }

    public String getStoreMorada() {
        return storeMorada;
    }

    public void setStoreMorada(String storeMorada) {
        this.storeMorada = storeMorada;
    }

    public String getStoreLocalidade() {
        return storeLocalidade;
    }

    public void setStoreLocalidade(String storeLocalidade) {
        this.storeLocalidade = storeLocalidade;
    }

    public String getStoreTlf() {
        return storeTlf;
    }

    public void setStoreTlf(String storeTlf) {
        this.storeTlf = storeTlf;
    }

    public String getStoreTlm() {
        return storeTlm;
    }

    public void setStoreTlm(String storeTlm) {
        this.storeTlm = storeTlm;
    }

    public String getItemCodBarras() {
        return itemCodBarras;
    }

    public void setItemCodBarras(String itemCodBarras) {
        this.itemCodBarras = itemCodBarras;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }
}
