package Telas;

import Classes.ConexaoSQL;
import Classes.Produto;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author 2830482411041
 */
public class CadastroProd extends javax.swing.JFrame {

    private static List<Produto> produtos = new ArrayList<>();
    private TabelaProdutos tabelaProdutos; // referência para atualizar tabela em tempo real

    public static List<Produto> getProdutos() {
        return produtos;
    }

    public CadastroProd() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private boolean validarCampos() {
        if (txtNomeprod.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do produto!", "Atenção", JOptionPane.WARNING_MESSAGE);
            txtNomeprod.requestFocus();
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtNomeprod.setText("");
        txtDescprod.setText("");
        spnQuantprod.setValue(0);
        txtNomeprod.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNomeprod = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescprod = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        spnQuantprod = new javax.swing.JSpinner();
        returnbTnprod = new javax.swing.JButton();
        saveprodbTn = new javax.swing.JButton();
        clearprodbTn = new javax.swing.JButton();
        prodscadbTn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(74, 85, 104));
        jPanel2.setPreferredSize(new java.awt.Dimension(80, 100));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\pczinho\\Downloads\\JAVAprojeto\\JAVAprojeto\\src\\icons\\icone_caixa.png")); // NOI18N
        jLabel7.setText("Cadastro de Produtos");
        jLabel7.setToolTipText("");
        jLabel7.setIconTextGap(10);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(240, 244, 248))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Nome:");

        txtNomeprod.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNomeprod.setForeground(new java.awt.Color(31, 41, 55));
        txtNomeprod.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(203, 213, 224), 1, true));
        txtNomeprod.setMargin(new java.awt.Insets(5, 10, 5, 10));
        txtNomeprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeprodActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setText("Descrição:");

        txtDescprod.setColumns(20);
        txtDescprod.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDescprod.setForeground(new java.awt.Color(31, 41, 55));
        txtDescprod.setRows(5);
        txtDescprod.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(203, 213, 224), 1, true));
        txtDescprod.setMargin(new java.awt.Insets(5, 10, 5, 10));
        jScrollPane1.setViewportView(txtDescprod);

        jLabel5.setBackground(new java.awt.Color(51, 51, 51));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setText("Quantidade em estoque:");

        spnQuantprod.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        spnQuantprod.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spnQuantprod.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(203, 213, 224), 1, true));
        spnQuantprod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spnQuantprodKeyTyped(evt);
            }
        });

        returnbTnprod.setBackground(new java.awt.Color(107, 114, 128));
        returnbTnprod.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        returnbTnprod.setForeground(new java.awt.Color(255, 255, 255));
        returnbTnprod.setText("Voltar");
        returnbTnprod.setBorderPainted(false);
        returnbTnprod.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returnbTnprod.setFocusPainted(false);
        returnbTnprod.setPreferredSize(new java.awt.Dimension(50, 40));
        returnbTnprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnbTnprodActionPerformed(evt);
            }
        });

        saveprodbTn.setBackground(new java.awt.Color(16, 185, 129));
        saveprodbTn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saveprodbTn.setForeground(new java.awt.Color(255, 255, 255));
        saveprodbTn.setText("Salvar Produto");
        saveprodbTn.setBorderPainted(false);
        saveprodbTn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveprodbTn.setFocusPainted(false);
        saveprodbTn.setPreferredSize(new java.awt.Dimension(94, 40));
        saveprodbTn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveprodbTnActionPerformed(evt);
            }
        });

        clearprodbTn.setBackground(new java.awt.Color(245, 158, 11));
        clearprodbTn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        clearprodbTn.setForeground(new java.awt.Color(255, 255, 255));
        clearprodbTn.setText("Limpar");
        clearprodbTn.setBorderPainted(false);
        clearprodbTn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearprodbTn.setFocusPainted(false);
        clearprodbTn.setPreferredSize(new java.awt.Dimension(50, 40));
        clearprodbTn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearprodbTnActionPerformed(evt);
            }
        });

        prodscadbTn.setBackground(new java.awt.Color(59, 130, 246));
        prodscadbTn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        prodscadbTn.setForeground(new java.awt.Color(255, 255, 255));
        prodscadbTn.setText("Produtos Cadastrados");
        prodscadbTn.setBorderPainted(false);
        prodscadbTn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        prodscadbTn.setFocusPainted(false);
        prodscadbTn.setPreferredSize(new java.awt.Dimension(131, 40));
        prodscadbTn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prodscadbTnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomeprod)
                            .addComponent(jScrollPane1)
                            .addComponent(spnQuantprod)
                            .addComponent(prodscadbTn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(saveprodbTn, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(158, 158, 158)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(returnbTnprod, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clearprodbTn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeprod, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnQuantprod, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnbTnprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearprodbTn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveprodbTn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prodscadbTn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void returnbTnprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnbTnprodActionPerformed
        Menu voltarmenu1 = new Menu();
        voltarmenu1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_returnbTnprodActionPerformed

    private void prodscadbTnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prodscadbTnActionPerformed
        // Cria ou reutiliza a tabela de produtos
    if (tabelaProdutos == null) {
        tabelaProdutos = new TabelaProdutos(); 
    }
    tabelaProdutos.atualizarTabela(); // Atualiza os dados
    tabelaProdutos.setVisible(true);   // mostra a janela
    }//GEN-LAST:event_prodscadbTnActionPerformed

    private void txtNomeprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeprodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeprodActionPerformed

    private void clearprodbTnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearprodbTnActionPerformed
        limparCampos();
    }//GEN-LAST:event_clearprodbTnActionPerformed

    private void saveprodbTnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveprodbTnActionPerformed
        try {
        // Valida os campos
        if (!validarCampos()) return;

        String nome = txtNomeprod.getText().trim();
        String descricao = txtDescprod.getText().trim();
        int quantidade = (int) spnQuantprod.getValue();

        String sql = "INSERT INTO produtos (prod_nome, prod_desc, prod_quant, prod_data) VALUES (?, ?, ?, ?)";
        int idGerado = 0;

        try (Connection conexao = ConexaoSQL.getConexaoSQL();
             PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nome);
            pstmt.setString(2, descricao);
            pstmt.setInt(3, quantidade);
            pstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));

            pstmt.executeUpdate();

            // Pega o ID gerado pelo banco
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                }
            }
        }

        // Cria o produto e adiciona à lista
        Produto novoProduto = new Produto(idGerado, nome, descricao, quantidade);
        produtos.add(novoProduto);

        limparCampos();
        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Abre a janela TabelaProdutos ou atualiza se já estiver aberta
        if (tabelaProdutos == null) {
            tabelaProdutos = new TabelaProdutos();
        }
        tabelaProdutos.atualizarTabela();
        tabelaProdutos.setVisible(true);

    } catch (HeadlessException | SQLException ex) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar o produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }      
       
    }//GEN-LAST:event_saveprodbTnActionPerformed

    private void spnQuantprodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spnQuantprodKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c) && c != '-' && c != java.awt.event.KeyEvent.VK_BACK_SPACE){
            evt.consume();
        }
    }//GEN-LAST:event_spnQuantprodKeyTyped
    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroProd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new CadastroProd().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearprodbTn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton prodscadbTn;
    private javax.swing.JButton returnbTnprod;
    private javax.swing.JButton saveprodbTn;
    private javax.swing.JSpinner spnQuantprod;
    private javax.swing.JTextArea txtDescprod;
    private javax.swing.JTextField txtNomeprod;
    // End of variables declaration//GEN-END:variables
}
