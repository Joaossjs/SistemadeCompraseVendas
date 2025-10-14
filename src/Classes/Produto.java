package Classes;

/**
 *
 * @author pczinho
 */
public class Produto {
    
    private String id;
    private String nome;
    private String descricao;
    private int quantidade;
    
    public Produto (String id, String nome, String descricao, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }
    
    public String getId() {return id;}
    public String getNome() {return nome;}
    public String getDescricao() {return descricao;}
    public int getQuantidade() {return quantidade;}
    
    @Override
    public String toString() {
       return nome + "(Código: " + id +  ")";
    }
}
