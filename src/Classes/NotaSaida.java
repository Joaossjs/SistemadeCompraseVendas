package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NotaSaida {

    /**
     * Salva a nota de saída com seus itens e atualiza o estoque.
     */
    public boolean salvarNotaEAtualizarEstoque(String codigoNota, String clienteId, String dataSql, List<ItemNotaNS> itens)
            throws SQLException {

        Connection conexao = null;
        String notasaIdGerado = null;

        try {
            conexao = ConexaoSQL.getConexaoSQL();
            conexao.setAutoCommit(false); // Inicia a transação
            
            // Salva o cabeçalho da nota de saída
            notasaIdGerado = salvarCabecalho(conexao, codigoNota, clienteId, dataSql);

            if (notasaIdGerado == null) {
                conexao.rollback();
                throw new SQLException("Falha ao salvar cabeçalho da nota de saída. O notasa_id pode já existir.");
            }

            // Salva os itens e atualiza o estoque (subtrai)
            for (ItemNotaNS item : itens) {
                // Passa notasaIdGerado como String
                salvarItemEAtualizarEstoque(conexao, notasaIdGerado, item);
            }

            conexao.commit();
            System.out.println("Nota de Saída e Estoque atualizados com sucesso.");
            return true;

        } catch (SQLException e) {
            if (conexao != null) {
                conexao.rollback();
            }
            throw e;
        } finally {
            if (conexao != null) {
                conexao.setAutoCommit(true);
                conexao.close();
            }
        }
    }

    /*
     * Insere o cabeçalho da nota de saída.
     */
    private String salvarCabecalho(Connection conexao, String codigoNota, String clienteId, String dataSql)
            throws SQLException {

        // Tabela: notas_saida, ID da nota: notasa_id, ID do cliente: cli_id
        String sql = "INSERT INTO notas_saida (notasa_id, cli_id, notasa_data) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            // notasa_id
            pstmt.setString(1, codigoNota);
            
            // cli_id 
            pstmt.setString(2, clienteId);
            
            // notasa_data
            pstmt.setString(3, dataSql);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                return codigoNota;
            }
        }

        return null;
    }

    /**
     * Salva os itens da nota e atualiza o estoque de cada produto.
     */
    private void salvarItemEAtualizarEstoque(Connection conexao, String notasa_id, ItemNotaNS item)
            throws SQLException {

        // Insere o item
        String sqlItem = "INSERT INTO itens_saida (notasa_id, prod_id, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmtItem = conexao.prepareStatement(sqlItem)) {
            
            // notasa_id
            pstmtItem.setString(1, notasa_id);
            
            // prod_id
            pstmtItem.setString(2, item.getProdutoId());
            
            // preco
            pstmtItem.setDouble(3, item.getValorUnitario());
            
            // quantidade
            pstmtItem.setInt(4, item.getQuantidade());
            
            pstmtItem.executeUpdate();
        }

        // Atualiza o estoque
        atualizarEstoque(conexao, item.getProdutoId(), item.getQuantidade(), false);
    }
    
    /*
     * Atualiza o estoque de um produto.
     */
    private void atualizarEstoque(Connection conexao, String prod_id, int prod_quant, boolean entrada)
            throws SQLException {

        String sql;
        String nomeColunaQuant = "prod_quant";
        String nomeTabelaProd = "produtos";

        if (entrada) {
            sql = "UPDATE " + nomeTabelaProd +
                    " SET " + nomeColunaQuant + " = " + nomeColunaQuant + " + ? WHERE prod_id = ?";
        } else {
            sql = "UPDATE " + nomeTabelaProd +
                    " SET " + nomeColunaQuant + " = " + nomeColunaQuant + " - ? WHERE prod_id = ?";
        }

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, prod_quant);
            pstmt.setString(2, prod_id);
            pstmt.executeUpdate();
        }
    }
}