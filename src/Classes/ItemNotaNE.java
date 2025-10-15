package Classes;

public class ItemNotaNE {
    private int produtoId;
    private String nome;
    private int quantidade;
    private double valorUnitario;

    public ItemNotaNE(int produtoId, String nome, int quantidade, double valorUnitario) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public int getProdutoId() { return produtoId; }
    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getValorUnitario() { return valorUnitario; }

    public double getSubtotal() { return quantidade * valorUnitario; }
    public double getValorTotal() { return getSubtotal(); }
}