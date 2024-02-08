package isep.simov.project.simov.ui.suppliers;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Suppliers {

    public String id;
    public String nome;
    public String morada;
    public String localidade;
    public String cidade;
    public String email;
    public String tlm;
    public String tlf;
    public String tpagamento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTlm() {
        return tlm;
    }

    public void setTlm(String tlm) {
        this.tlm = tlm;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getTpagamento() {
        return tpagamento;
    }

    public void setTpagamento(String tpagamento) {
        this.tpagamento = tpagamento;
    }

    public Suppliers(String id, String nome, String morada, String localidade, String cidade, String email, String tlm, String tlf, String tpagamento) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.localidade = localidade;
        this.cidade = cidade;
        this.tlm = tlm;
        this.tlf = tlf;
        this.email = email;
        this.tpagamento = tpagamento;
    }

    public Suppliers() {
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("nome", nome);
        result.put("morada", morada);
        result.put("localidade", localidade);
        result.put("cidade", cidade);
        result.put("email", email);
        result.put("tlf", tlf);
        result.put("tlm", tlm);
        result.put("tpagamento", tpagamento);

        return result;
    }
    // [END post_to_map]

    protected boolean validateId() {
        if (this.id.length() < 1 || Integer.parseInt(this.id) <= 0)
            return false;

        return true;
    }
    protected boolean validateNome() {
        if (this.nome.length() < 1)
            return false;

        return true;
    }
    protected boolean validateMorada() {
        if (this.morada.length() < 1)
            return false;

        return true;
    }
    protected boolean validateLocalidade() {
        if (this.localidade.length() < 1)
            return false;

        return true;
    }
    protected boolean validateCidade() {
        if (this.cidade.length() < 1)
            return false;

        return true;
    }
    protected boolean validateEmail() {
        if (this.email.length() < 1)
            return false;

        return true;
    }
    protected boolean validateTipoPagamento() {
        if (this.tpagamento.length() < 1)
            return false;

        return true;
    }

    protected boolean validateTlf() {
        if (this.tlf.length() != 9)
            return false;

        return true;
    }

    protected boolean validateTlm() {
        if (this.tlm.length() != 9)
            return false;

        return true;
    }

    public boolean validateSupplier() {
        if (!validateId() || !validateNome()  || !validateMorada()  || !validateLocalidade()
                || !validateCidade()  || !validateEmail()  || !validateTlf()
                || !validateTlm() || !validateTipoPagamento())
            return false;

        return true;
    }
}
