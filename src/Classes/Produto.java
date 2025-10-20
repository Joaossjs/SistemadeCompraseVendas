package Classes;

public class Produto {

    private int id;       // prod_id do banco de dados       
    private String nome;
    private String descricao;
    private int quantidade;

    // Construtor sem id para inserir no banco
    public Produto(String nome, String descricao, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // Construtor completo para criar a partir do banco
    public Produto(int id, String nome, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override // Exibir o produto com código e nome
    public String toString() {
        return nome + " (Código: " + id + ")";
    }
}