package Classes;


public class Cliente {
    
    private int id;      //cl_id do banco de dados        
    private String nome;
    private String cep;
    private String numero;
    private String email;
    private String telefone;
    
    // Construtor completo
    public Cliente(String nome, String cep, String numero, String email, String telefone) {
        this.nome = nome;
        this.cep = cep;
        this.numero = numero;
        this.email = email;
        this.telefone = telefone;
}

    
    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
    
    // Setters (caso precise alterar dados)
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return nome + " (Código: " + id + ")";
    }
}
