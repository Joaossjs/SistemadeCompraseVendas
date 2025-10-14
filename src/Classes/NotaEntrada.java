package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NotaEntrada {

    /**
     * Salva a nota de entrada com seus itens e atualiza o estoque.
     */
    public boolean salvarNotaEAtualizarEstoque(String codigoNota, String fornecedorId, String dataSql, List<ItemNotaNE> itens)
            throws SQLException {

        Connection conexao = null;
        String notaeIdGerado = null; 

        try {
            conexao = ConexaoSQL.getConexaoSQL();
            conexao.setAutoCommit(false); // Inicia a transação
            
            // CORREÇÃO: Chama o método corrigido, que retorna String (o código da nota)
            notaeIdGerado = salvarCabecalho(conexao, codigoNota, fornecedorId, dataSql);

            // CORREÇÃO: Verifica se o retorno é null
            if (notaeIdGerado == null) {
                conexao.rollback();
                throw new SQLException("Falha ao salvar cabeçalho da nota de entrada. O notae_id pode já existir.");
            }

            // Salva os itens e atualiza o estoque
            for (ItemNotaNE item : itens) {
                // Passa notaeIdGerado como String
                salvarItemEAtualizarEstoque(conexao, notaeIdGerado, item);
            }

            conexao.commit();
            System.out.println("Nota de Entrada e Estoque atualizados com sucesso.");
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

    /**
     * Insere o cabeçalho da nota e retorna o notae_id fornecido.
     * Deve ser chamado com os dados já validados
     */
    // Retorna String e recebe fornecedorId como String
    private String salvarCabecalho(Connection conexao, String codigoNota, String fornecedorId, String dataSql)
            throws SQLException {

        String sql = "INSERT INTO notas_entrada (notae_id, for_id, notae_data) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) { 
            
            // notae_id
            pstmt.setString(1, codigoNota); 
            
            // for_id
            pstmt.setString(2, fornecedorId); 
            
            // notae_data
            pstmt.setString(3, dataSql);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                
                return codigoNota; 
            }
        }

        return null; // Retorna null em caso de falha na inserção
    }

    /**
     * Salva os itens da nota e atualiza o estoque de cada produto.
     */
    // CORREÇÃO: Recebe notae_id como String
    private void salvarItemEAtualizarEstoque(Connection conexao, String notae_id, ItemNotaNE item)
            throws SQLException {

        // Insere o item
        String sqlItem = "INSERT INTO itens_entrada (notae_id, prod_id, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmtItem = conexao.prepareStatement(sqlItem)) {
            // notae_id (VARCHAR)
            pstmtItem.setString(1, notae_id); 
            
            // prod_id (VARCHAR)
            pstmtItem.setString(2, item.getProdutoId());
            
            // preco
            pstmtItem.setDouble(3, item.getValorUnitario());
            
            // quantidade
            pstmtItem.setInt(4, item.getQuantidade());
            
            pstmtItem.executeUpdate();
        }

        // Atualiza o estoque
        atualizarEstoque(conexao, item.getProdutoId(), item.getQuantidade(), true);
    }

    /**
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
