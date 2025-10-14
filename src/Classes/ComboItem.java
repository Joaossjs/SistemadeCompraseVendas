package Classes;

public class ComboItem {
    private String id;
    private String descricao;

    public ComboItem(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return descricao;
    }
}