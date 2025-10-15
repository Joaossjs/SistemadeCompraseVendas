package Classes;

/*
 * Classe auxiliar para armazenar ID e Label
 * em JComboBox.
 */
public class ComboItem {
    
    private final String id;
    private final String label;

    /*
     * Construtor da classe ComboItem.
     * O ID real do item
     * O texto de exibição formatado
     */
    public ComboItem(String id, String label) {
        this.id = id;
        this.label = label;
    }

    /*
     * Retorna o ID real do item.
     */
    public String getId() {
        return id;
    }

    /*
     * Retorna a Label do item.
     */
    public String getLabel() {
        return label;
    }

    /*
     * Método sobrescrito que é chamado internamente pelo JComboBox para
     * determinar o texto a ser exibido na lista.
     */
    @Override
    public String toString() {
        return label;
    }
}