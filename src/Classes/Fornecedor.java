package Classes;

/**
 *
 * @author pczinho
 */
public class Fornecedor {
    
    private String codigo;
    private String nome;
    private String nomefant;
    private String cep;
    private String num;
    private String email;
    private String tel;
    private String cnpj;
    
    public Fornecedor (String codigo, String nome, String nomefant, String cep, String num, String email, String tel, String cnpj) {
        this.codigo = codigo;
        this.nome = nome;
        this.nomefant = nomefant;
        this.cep = cep;
        this.num = num;
        this.email = email;
        this.tel = tel;
        this.cnpj = cnpj;
    }
    
    public String getCodigo() {return codigo;}
    public String getNome() {return nome;}
    public String getNomefant() {return nomefant;}
    public String getCep() {return cep;}
    public String getNum() {return num;}
    public String getEmail() {return email;}
    public String getTel() {return tel;}
    public String getCNPJ() {return cnpj;}
    
    @Override
    public String toString() {
       return nome + "(Código: " + codigo +  ")";
    }
}

