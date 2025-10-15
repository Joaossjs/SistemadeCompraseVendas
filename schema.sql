-- Cria o banco de dados
CREATE DATABASE IF NOT EXISTS sistema_gestao;

-- Utiliza o banco de dados
USE sistema_gestao;

-- TABELA DE PRODUTOS
CREATE TABLE IF NOT EXISTS produtos (
    prod_id INT AUTO_INCREMENT PRIMARY KEY, 
    prod_nome VARCHAR(45) NOT NULL, 
    prod_desc TEXT,
    prod_quant INT NOT NULL DEFAULT 0,
    prod_data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA DE FORNECEDORES
CREATE TABLE IF NOT EXISTS fornecedores (
    for_id INT AUTO_INCREMENT PRIMARY KEY,
    for_nome VARCHAR(45) NOT NULL,    
    for_nomefant VARCHAR(45) NOT NULL,
    for_cnpj VARCHAR(19) NOT NULL,
    for_cep VARCHAR(10),
    for_numero VARCHAR(20),
    for_email VARCHAR(100),
    for_telefone VARCHAR(20),
    for_data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA DE CLIENTES
CREATE TABLE IF NOT EXISTS clientes (
    cl_id INT AUTO_INCREMENT PRIMARY KEY,
    cl_nome VARCHAR(45) NOT NULL,    
    cl_cep VARCHAR(10),
    cl_numero VARCHAR(20),
    cl_email VARCHAR(100),
    cl_telefone VARCHAR(20),
    cl_data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA DE NOTAS DE ENTRADA
CREATE TABLE IF NOT EXISTS notas_entrada (
    notae_id INT AUTO_INCREMENT PRIMARY KEY,
    for_id INT NOT NULL,
    notae_data DATE NOT NULL,
    KEY fk_notae_fornecedor (for_id),
    CONSTRAINT fk_notae_fornecedor FOREIGN KEY (for_id) REFERENCES fornecedores(for_id)
);

-- TABELA DE NOTAS DE SAÍDA
CREATE TABLE IF NOT EXISTS notas_saida (
    notasa_id INT AUTO_INCREMENT PRIMARY KEY,
    cl_id INT NOT NULL,
    notasa_data DATE NOT NULL,
    KEY fk_notasa_cliente (cl_id),
    CONSTRAINT fk_notasa_cliente FOREIGN KEY (cl_id) REFERENCES clientes(cl_id)
);

-- TABELA ITENS DE ENTRADA
CREATE TABLE IF NOT EXISTS itens_entrada (
    id INT AUTO_INCREMENT PRIMARY KEY,          
    notae_id INT NOT NULL,                     
    prod_id INT NOT NULL,                      
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    KEY fk_itens_entrada_nota (notae_id),
    KEY fk_itens_entrada_produto (prod_id),
    CONSTRAINT fk_itens_entrada_nota FOREIGN KEY (notae_id) REFERENCES notas_entrada(notae_id),
    CONSTRAINT fk_itens_entrada_prod FOREIGN KEY (prod_id) REFERENCES produtos(prod_id)
);

-- TABELA ITENS DE SAÍDA
CREATE TABLE IF NOT EXISTS itens_saida (
    id INT AUTO_INCREMENT PRIMARY KEY,         
    notasa_id INT NOT NULL,                     
    prod_id INT NOT NULL,                       
    preco DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    KEY fk_itens_saida_nota (notasa_id),
    KEY fk_itens_saida_produto (prod_id),
    CONSTRAINT fk_itens_saida_nota FOREIGN KEY (notasa_id) REFERENCES notas_saida(notasa_id),
    CONSTRAINT fk_itens_saida_prod FOREIGN KEY (prod_id) REFERENCES produtos(prod_id)
);




    
    
    