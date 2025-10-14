package Classes;

public class ItemNotaNS {
    
    private String produtoId;
    private String nome;
    private int quantidade;
    private double valorUnitario;
    
    // Construtor
    public ItemNotaNS(String produtoId, String nome, int quantidade, double valorUnitario) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }
    
    // Getters
    public String getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }
    
    // Calcular subtotal dinamicamente
    public double getSubtotal() {
        return quantidade * valorUnitario;
    }
    
    public double getValorTotal() {
        return getSubtotal();
    }
}
