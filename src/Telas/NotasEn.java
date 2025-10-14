package Telas;

import Classes.ComboItem;
import Classes.ConexaoSQL;
import Classes.ItemNotaNE;
import Classes.NotaEntrada;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author pczinho
 */
public class NotasEn extends javax.swing.JFrame {

    public static List<ItemNotaNE> getItemNotaNE() {
        return listaItensNota;
    }

    private final NotaEntrada notaDAO = new NotaEntrada(); 
    private static List<ItemNotaNE> listaItensNota = new ArrayList<>();

        public NotasEn() {
        initComponents(); 
        formatadorMoeda();
        setLocationRelativeTo(null);
        carregarFornecedores();
        carregarProdutos();
        preencherTabela();

        spnQuantNE.setValue(0);
    }
        
    // Preenche a tabela com os itens da lista estática
    private void preencherTabela() {

    DefaultTableModel modelo = (DefaultTableModel) tblProdsNE.getModel();
    
    // Limpa a tabela
    modelo.setRowCount(0);
    
    // Verifica se está vazia
    if (listaItensNota.isEmpty()) {
        System.out.println("Nenhum item na lista");
        return;
    }
    
    // Percorre e adiciona na tabela
    for (ItemNotaNE item : listaItensNota) {
        
        modelo.addRow(new Object[]{
            item.getProdutoId(),
            item.getNome(),
            item.getQuantidade(),
            "R$ " + String.format("%.2f", item.getValorUnitario()),
        });
    }
 
}
        
        
    private void carregarFornecedores() {
        String sql = "SELECT for_id, for_nome FROM fornecedores ORDER BY for_nome";
        
        try (Connection conexao = ConexaoSQL.getConexaoSQL();
         PreparedStatement pstmt = conexao.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
            
            // Limpa e adiciona o item padrão
            cmbForNE.removeAllItems();
            cmbForNE.addItem(new ComboItem("", "Selecione o fornecedor...")); 
            
            while (rs.next()) {
            String id = rs.getString("for_id");
            String nome = rs.getString("for_nome");
            
            //Passar o ID (int) e o texto formatado (String)
            String textoFormatado = id + " - " + nome;
            ComboItem item = new ComboItem(id, textoFormatado); //Passa o ID e o Texto
            
            cmbForNE.addItem(item);
        }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar fornecedores: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

private void carregarProdutos() {

    String sql = "SELECT prod_id, prod_nome FROM produtos ORDER BY prod_nome";

    try (Connection conexao = ConexaoSQL.getConexaoSQL();
         PreparedStatement pstmt = conexao.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        //Limpa e adiciona o item padrão
        cmbProdNE.removeAllItems();
        cmbProdNE.addItem(new ComboItem("", "Selecione o produto...")); //Construtor (int, String)

        while (rs.next()) {

            String id = rs.getString("prod_id");
            String nome = rs.getString("prod_nome");


            String textoFormatado = id + " - " + nome;
            ComboItem item = new ComboItem(id, textoFormatado); //Passa o ID e o TEXTO

            cmbProdNE.addItem(item);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
    }
}

    private void formatadorMoeda() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new java.util.Locale("pt", "BR"));
        NumberFormatter formatadorMoeda = new NumberFormatter(formatoMoeda);
        formatadorMoeda.setValueClass(Double.class);
        formatadorMoeda.setMinimum(0.0);
        formatadorMoeda.setAllowsInvalid(false);

        txtPrecoNE.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(formatadorMoeda));
        txtPrecoNE.setValue(0.0);  
    }
    
    private boolean validarNota() {
    //Valida se há pelo menos um produto cadastrado na nota   
    if (listaItensNota.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto antes de salvar.", "Erro de validação", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    //Caso tenha pelo menos um produto cadastrado, é feito o pedido de confirmação para poder salvar a nota
    if (spnDateNE.getValue() == null || cmbForNE.getSelectedIndex() == 0) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Os campos de Código, Data ou Fornecedor estão vazios. Deseja salvar a nota assim?", 
            "Confirmação de Salvamento", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        return (confirm == JOptionPane.YES_OPTION);
    }   
    

return true; // Todos os campos estão válidos

}
    
    

    private void limparCampos() {
        spnDateNE.setValue(new java.util.Date()); // Data atual
        cmbForNE.setSelectedIndex(0);
        cmbProdNE.setSelectedIndex(0);
        spnQuantNE.setValue(1); // Volta para o valor inicial
        txtPrecoNE.setValue(0.0);
    }
    
    private void adicionarProdutoNaTabela() {
    if (cmbProdNE.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Selecione um produto válido!");
        return;
    }

    ComboItem itemSelecionado = (ComboItem) cmbProdNE.getSelectedItem();
    String produtoId = itemSelecionado.getId();
    String nomeProduto = itemSelecionado.toString();
    int quantidade = ((Number) spnQuantNE.getValue()).intValue();
    double valorUnitario = ((Number) txtPrecoNE.getValue()).doubleValue();

    // Verifica se produto já foi adicionado
    DefaultTableModel modelo = (DefaultTableModel) tblProdsNE.getModel();
    for (int i = 0; i < modelo.getRowCount(); i++) {
        if (modelo.getValueAt(i, 0).toString().equals(produtoId)) {
            JOptionPane.showMessageDialog(this, "Produto já adicionado à tabela!");
            return;
        }
    }

    // Adiciona à tabela
    modelo.addRow(new Object[]{
        produtoId,
        nomeProduto,
        quantidade,
        valorUnitario
    });

    // Limpa os campos
    cmbProdNE.setSelectedIndex(0);
    spnQuantNE.setValue(1);
    txtPrecoNE.setValue(0.0);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbForNE = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        spnDateNE = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        cmbProdNE = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        spnQuantNE = new javax.swing.JSpinner();
        returnbTnNE = new javax.swing.JButton();
        clearbTnNE = new javax.swing.JButton();
        saveNEbTn = new javax.swing.JButton();
        NEscadbTn = new javax.swing.JButton();
        txtPrecoNE = new javax.swing.JFormattedTextField();
        clearbTnNE1 = new javax.swing.JButton();
        AddprodNE = new javax.swing.JButton();
        txtIdNE = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdsNE = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(59, 130, 246));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\pczinho\\Downloads\\JAVAprojeto\\JAVAprojeto\\src\\icons\\icone_entrada.png")); // NOI18N
        jLabel2.setText("Lançamento de Notas de Entrada");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setText("Valor unitário:");

        cmbForNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbForNEActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setText("Código da nota:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel7.setText("Produto:");

        spnDateNE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        spnDateNE.setModel(new javax.swing.SpinnerDateModel());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setText("Fornecedor:");

        cmbProdNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProdNEActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel9.setText("Quantidade comprada:");

        spnQuantNE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spnQuantNEKeyTyped(evt);
            }
        });

        returnbTnNE.setBackground(new java.awt.Color(107, 114, 128));
        returnbTnNE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        returnbTnNE.setForeground(new java.awt.Color(255, 255, 255));
        returnbTnNE.setText("Voltar");
        returnbTnNE.setBorderPainted(false);
        returnbTnNE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnbTnNE.setFocusPainted(false);
        returnbTnNE.setPreferredSize(new java.awt.Dimension(50, 40));
        returnbTnNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnbTnNEActionPerformed(evt);
            }
        });

        clearbTnNE.setBackground(new java.awt.Color(245, 158, 11));
        clearbTnNE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearbTnNE.setForeground(new java.awt.Color(255, 255, 255));
        clearbTnNE.setText("Limpar");
        clearbTnNE.setBorderPainted(false);
        clearbTnNE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearbTnNE.setFocusPainted(false);
        clearbTnNE.setPreferredSize(new java.awt.Dimension(50, 40));
        clearbTnNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbTnNEActionPerformed(evt);
            }
        });

        saveNEbTn.setBackground(new java.awt.Color(16, 185, 129));
        saveNEbTn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saveNEbTn.setForeground(new java.awt.Color(255, 255, 255));
        saveNEbTn.setText("Salvar Nota");
        saveNEbTn.setBorderPainted(false);
        saveNEbTn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveNEbTn.setFocusPainted(false);
        saveNEbTn.setPreferredSize(new java.awt.Dimension(50, 40));
        saveNEbTn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNEbTnActionPerformed(evt);
            }
        });

        NEscadbTn.setBackground(new java.awt.Color(59, 130, 246));
        NEscadbTn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        NEscadbTn.setForeground(new java.awt.Color(255, 255, 255));
        NEscadbTn.setText("Notas Cadastradas");
        NEscadbTn.setBorderPainted(false);
        NEscadbTn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NEscadbTn.setFocusPainted(false);
        NEscadbTn.setPreferredSize(new java.awt.Dimension(50, 40));
        NEscadbTn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NEscadbTnActionPerformed(evt);
            }
        });

        txtPrecoNE.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(203, 213, 224), 1, true));
        txtPrecoNE.setForeground(new java.awt.Color(31, 41, 55));
        txtPrecoNE.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtPrecoNE.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPrecoNE.setMargin(new java.awt.Insets(5, 10, 5, 10));
        txtPrecoNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoNEActionPerformed(evt);
            }
        });
        txtPrecoNE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecoNEKeyTyped(evt);
            }
        });

        clearbTnNE1.setBackground(new java.awt.Color(139, 92, 246));
        clearbTnNE1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearbTnNE1.setForeground(new java.awt.Color(255, 255, 255));
        clearbTnNE1.setText("Produtos Adicionados");
        clearbTnNE1.setBorderPainted(false);
        clearbTnNE1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearbTnNE1.setFocusPainted(false);
        clearbTnNE1.setPreferredSize(new java.awt.Dimension(50, 40));
        clearbTnNE1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbTnNE1ActionPerformed(evt);
            }
        });

        AddprodNE.setBackground(new java.awt.Color(107, 114, 128));
        AddprodNE.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AddprodNE.setForeground(new java.awt.Color(255, 255, 255));
        AddprodNE.setText("Adicionar Produto");
        AddprodNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddprodNEActionPerformed(evt);
            }
        });

        txtIdNE.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(203, 213, 224), 1, true));
        txtIdNE.setForeground(new java.awt.Color(31, 41, 55));
        txtIdNE.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtIdNE.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtIdNE.setMargin(new java.awt.Insets(5, 10, 5, 10));
        txtIdNE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdNEActionPerformed(evt);
            }
        });
        txtIdNE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdNEKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setText("Data da compra:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnQuantNE, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(NEscadbTn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(saveNEbTn, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearbTnNE1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(returnbTnNE, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearbTnNE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cmbProdNE, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddprodNE, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                    .addComponent(cmbForNE, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spnDateNE)
                    .addComponent(txtPrecoNE)
                    .addComponent(txtIdNE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdNE, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnDateNE, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecoNE, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbForNE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbProdNE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddprodNE))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnQuantNE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnbTnNE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbTnNE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveNEbTn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbTnNE1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NEscadbTn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Produtos Adicionados");

        tblProdsNE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Quantidade", "Preço Unitário"
            }
        ));
        jScrollPane1.setViewportView(tblProdsNE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbForNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbForNEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbForNEActionPerformed

    private void cmbProdNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProdNEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProdNEActionPerformed

    private void returnbTnNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnbTnNEActionPerformed
        Menu voltarmenu1 = new Menu();
        voltarmenu1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_returnbTnNEActionPerformed

    private void clearbTnNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbTnNEActionPerformed
        limparCampos();
    }//GEN-LAST:event_clearbTnNEActionPerformed

    private void saveNEbTnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveNEbTnActionPerformed
    // Pegando dados da tabela
    DefaultTableModel model = (DefaultTableModel) tblProdsNE.getModel();

    if (tblProdsNE.getModel().getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto antes de salvar a nota.");
        return;
    }

    String codigoNota = txtIdNE.getText().trim();
    if (codigoNota.isEmpty()) {
         JOptionPane.showMessageDialog(this, "O Código da Nota é obrigatório.");
         txtIdNE.requestFocus();
         return;
    }

    // Coleta e validação do Fornecedor
    ComboItem fornecedorSelecionado = (ComboItem) cmbForNE.getSelectedItem();
    String fornecedorId = (fornecedorSelecionado != null && !fornecedorSelecionado.getId().isEmpty())
                          ? fornecedorSelecionado.getId()
                          : null;
                          
    if (fornecedorId == null || cmbForNE.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(this, "Selecione um fornecedor válido.");
        return;
    }
   
    // Coleta da Data
    Date dataSelecionada = (Date) spnDateNE.getValue();
    java.sql.Date dataSQL = (dataSelecionada != null) ? new java.sql.Date(dataSelecionada.getTime()) : null;

    Connection conexao = null;
    String novaNotaId = null;

    try {
        conexao = ConexaoSQL.getConexaoSQL();
        conexao.setAutoCommit(false); // Transação

        // --- Insere cabeçalho da nota ---
        // A SQL deve incluir notae_id, e for_id e notae_id são VARCHAR
        String sqlNota = "INSERT INTO notas_entrada (notae_id, for_id, notae_data) VALUES (?, ?, ?)";
        
        try (PreparedStatement psNota = conexao.prepareStatement(sqlNota)) {

            // notae_id
            psNota.setString(1, codigoNota); 
            
            // for_id
            psNota.setString(2, fornecedorId); 
            
            // notae_data
            if (dataSQL == null) {
                 psNota.setNull(3, java.sql.Types.DATE);
            } else {
                 psNota.setDate(3, dataSQL);
            }

            psNota.executeUpdate();

            // O ID da nota agora é o código fornecido
            novaNotaId = codigoNota; 
        }

        //Insere os itens
        String sqlProduto = "INSERT INTO itens_entrada (notae_id, prod_id, quantidade, preco) VALUES (?, ?, ?, ?)";
        // SQL para Atualizar o Estoque (Adicionar quantidade)
        String sqlEstoque = "UPDATE produtos SET prod_quant = prod_quant + ? WHERE prod_id = ?";
        
        try (PreparedStatement psProd = conexao.prepareStatement(sqlProduto);
             PreparedStatement psEstoque = conexao.prepareStatement(sqlEstoque)) { // PreparedStatement para o estoque

            for (int i = 0; i < model.getRowCount(); i++) {
    
            // Coleta os dados do Item
            Object prodIdObj = model.getValueAt(i, 0);
            //TRATAMENTO E VALIDAÇÃO DE PROD_ID
                if (prodIdObj == null || prodIdObj.toString().trim().isEmpty()) {
                // Se o código do produto estiver nulo ou vazio
                throw new IllegalArgumentException("O Código do Produto (coluna 1) não pode estar vazio na linha " + (i + 1) + " da tabela.");
            }
    
            String prodId = prodIdObj.toString();

    // Coleta dos demais dados  
    String qtdString = model.getValueAt(i, 2).toString();
    String precoString = model.getValueAt(i, 3).toString().replace("R$", "").replace(",", ".").trim();
    
    int qtd = Integer.parseInt(qtdString);
    double preco = Double.parseDouble(precoString);
                
                // notae_id
                psProd.setString(1, novaNotaId); 
                
                // prod_id
                psProd.setString(2, prodId);
                
                // quantidade
                psProd.setInt(3, qtd);
                
                // preco
                psProd.setDouble(4, preco);

                psProd.executeUpdate();
                
                // quantidade
                psEstoque.setInt(1, qtd);
                
                // prod_id
                psEstoque.setString(2, prodId);
                
                psEstoque.executeUpdate(); // Executa a atualização do estoque
            }
        }


        conexao.commit(); // Confirma transação
        JOptionPane.showMessageDialog(this, "Nota de entrada salva com sucesso!");

        // Limpa tabela e formulário
        model.setRowCount(0);
        limparCampos();
        
    } catch (Exception e) {
        try {
            if (conexao != null) conexao.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Erro ao salvar a nota: " + e.getMessage(), "Erro de Transação", JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (conexao != null) conexao.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}
    }//GEN-LAST:event_saveNEbTnActionPerformed

    private void NEscadbTnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NEscadbTnActionPerformed
        TabelaNotasEn telaListagem = new TabelaNotasEn();
        telaListagem.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_NEscadbTnActionPerformed

    private void txtPrecoNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoNEActionPerformed

    }//GEN-LAST:event_txtPrecoNEActionPerformed

    private void txtPrecoNEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecoNEKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != '-' && c != java.awt.event.KeyEvent.VK_BACK_SPACE){
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecoNEKeyTyped

    private void clearbTnNE1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbTnNE1ActionPerformed
        limparCampos();
    }//GEN-LAST:event_clearbTnNE1ActionPerformed

    private void AddprodNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddprodNEActionPerformed
        adicionarProdutoNaTabela();
    }//GEN-LAST:event_AddprodNEActionPerformed

    private void spnQuantNEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spnQuantNEKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != '-' && c != java.awt.event.KeyEvent.VK_BACK_SPACE){
            evt.consume();
        }
    }//GEN-LAST:event_spnQuantNEKeyTyped

    private void txtIdNEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdNEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdNEActionPerformed

    private void txtIdNEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdNEKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdNEKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotasEn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NotasEn().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddprodNE;
    private javax.swing.JButton NEscadbTn;
    private javax.swing.JButton clearbTnNE;
    private javax.swing.JButton clearbTnNE1;
    private javax.swing.JComboBox<ComboItem> cmbForNE;
    private javax.swing.JComboBox<ComboItem> cmbProdNE;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton returnbTnNE;
    private javax.swing.JButton saveNEbTn;
    private javax.swing.JSpinner spnDateNE;
    private javax.swing.JSpinner spnQuantNE;
    private javax.swing.JTable tblProdsNE;
    private javax.swing.JFormattedTextField txtIdNE;
    private javax.swing.JFormattedTextField txtPrecoNE;
    // End of variables declaration//GEN-END:variables
}
