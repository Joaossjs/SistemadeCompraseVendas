package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NotaEntrada {

    /**
     * Salva a nota de entrada com seus itens e atualiza o estoque.
     * Retorna o ID da nota gerado automaticamente.
     */
    public int salvarNotaEAtualizarEstoque(int fornecedorId, String dataSql, List<ItemNotaNE> itens)
            throws SQLException {

        Connection conexao = null;
        int notaeIdGerado = -1; 

        try {
            conexao = ConexaoSQL.getConexaoSQL();
            conexao.setAutoCommit(false); // inicia transação

            // Salva cabeçalho e retorna o ID gerado
            notaeIdGerado = salvarCabecalho(conexao, fornecedorId, dataSql);

            if (notaeIdGerado <= 0) {
                conexao.rollback();
                throw new SQLException("Falha ao salvar cabeçalho da nota de entrada.");
            }

            // Salva itens e atualiza estoque
            for (ItemNotaNE item : itens) {
                salvarItemEAtualizarEstoque(conexao, notaeIdGerado, item);
            }

            conexao.commit();
            return notaeIdGerado;

        } catch (SQLException e) {
            if (conexao != null) conexao.rollback();
            throw e;
        } finally {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        }
    }

    // Salva o cabeçalho da nota e retorna o notae_id gerado
    private int salvarCabecalho(Connection conexao, int fornecedorId, String dataSql)
            throws SQLException {

        String sql = "INSERT INTO notas_entrada (for_id, notae_data) VALUES (?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, fornecedorId);   // agora INT
            ps.setString(2, dataSql);

            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retorna o ID gerado pelo banco
                    }
                }
            }
        }
        return -1;
    }

    // Salva item da nota e atualiza estoque
    private void salvarItemEAtualizarEstoque(Connection conexao, int notaeId, ItemNotaNE item)
            throws SQLException {

        // Insere o item da nota
        String sqlItem = "INSERT INTO itens_entrada (notae_id, prod_id, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psItem = conexao.prepareStatement(sqlItem)) {
            psItem.setInt(1, notaeId);                     // notae_id INT
            psItem.setInt(2, item.getProdutoId());         // prod_id INT
            psItem.setDouble(3, item.getValorUnitario());  // preco DECIMAL
            psItem.setInt(4, item.getQuantidade());        // quantidade INT
            psItem.executeUpdate();
        }

        // Atualiza o estoque
        atualizarEstoque(conexao, item.getProdutoId(), item.getQuantidade(), true);
    }

    // Atualiza estoque
    private void atualizarEstoque(Connection conexao, int prodId, int quantidade, boolean entrada)
            throws SQLException {

        String sql = entrada
                ? "UPDATE produtos SET prod_quant = prod_quant + ? WHERE prod_id = ?"
                : "UPDATE produtos SET prod_quant = prod_quant - ? WHERE prod_id = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setInt(2, prodId);  // agora INT
            ps.executeUpdate();
        }
    }
}



