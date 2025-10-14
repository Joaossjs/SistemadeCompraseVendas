package Classes;

public class ItemNotaNE {
    private String produtoId;
    private String nome;
    private int quantidade;
    private double valorUnitario;
    private String fornecedorNome;
    
    // Construtor com 5 parâmetros
    public ItemNotaNE(String produtoId, String nome, int quantidade, double valorUnitario, String fornecedorNome) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.fornecedorNome = fornecedorNome;
    }
    
    // Getters
    public String getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }
    public String getFornecedorNome() { return fornecedorNome; }
    
    // Calcular subtotal dinamicamente
    public double getSubtotal() {
        return quantidade * valorUnitario;
    }

    //getSubtotal (se preferir chamar de valorTotal)
    public double getValorTotal() {
        return getSubtotal();
    }
}