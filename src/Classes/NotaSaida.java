package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NotaSaida {

    /**
     * Salva a nota de saída com seus itens e atualiza o estoque.
     */
    public int salvarNotaESubtrairEstoque(int clienteId, String dataSql, List<ItemNotaNS> itens)
            throws SQLException {

        Connection conexao = null;
        int notasIdGerado = -1;

        try {
            conexao = ConexaoSQL.getConexaoSQL();
            conexao.setAutoCommit(false);

            // Salva cabeçalho
            notasIdGerado = salvarCabecalho(conexao, clienteId, dataSql);
            if (notasIdGerado <= 0) {
                conexao.rollback();
                throw new SQLException("Falha ao salvar cabeçalho da nota de saída.");
            }

            // Salva itens e subtrai estoque
            for (ItemNotaNS item : itens) {
                // Validação: não permitir retirar mais que o estoque
                int estoqueAtual = pegarEstoque(conexao, item.getProdutoId());
                if (item.getQuantidade() > estoqueAtual) {
                    throw new SQLException("Não é possível vender " + item.getQuantidade()
                            + " unidades do produto " + item.getNome() + ". Estoque atual: " + estoqueAtual);
                }
                salvarItemESubtrairEstoque(conexao, notasIdGerado, item);
            }

            conexao.commit();
            return notasIdGerado;

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

    private int salvarCabecalho(Connection conexao, int clienteId, String dataSql) throws SQLException {
        String sql = "INSERT INTO notas_saida (cl_id, notasa_data) VALUES (?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, clienteId);
            ps.setString(2, dataSql);
            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    private void salvarItemESubtrairEstoque(Connection conexao, int notasId, ItemNotaNS item) throws SQLException {
        String sqlItem = "INSERT INTO itens_saida (notasa_id, prod_id, preco, quantidade) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conexao.prepareStatement(sqlItem)) {
            ps.setInt(1, notasId);
            ps.setInt(2, item.getProdutoId());
            ps.setDouble(3, item.getValorUnitario());
            ps.setInt(4, item.getQuantidade());
            ps.executeUpdate();
        }

        // Subtrai do estoque
        String sqlAtualiza = "UPDATE produtos SET prod_quant = prod_quant - ? WHERE prod_id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sqlAtualiza)) {
            ps.setInt(1, item.getQuantidade());
            ps.setInt(2, item.getProdutoId());
            ps.executeUpdate();
        }
    }

    private int pegarEstoque(Connection conexao, int prodId) throws SQLException {
        String sql = "SELECT prod_quant FROM produtos WHERE prod_id = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, prodId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("prod_quant");
            }
        }
        return 0;
    }
}