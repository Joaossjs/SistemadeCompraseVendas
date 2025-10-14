package Classes;

/**
 *
 * @author pczinho
 */
public class Cliente {
    
    private String codigo;
    private String nome;
    private String cep;
    private String num;
    private String email;
    private String tel;
    
    public Cliente (String codigo, String nome, String cep, String num, String email, String tel) {
        this.codigo = codigo;
        this.nome = nome;
        this.cep = cep;
        this.num = num;
        this.email = email;
        this.tel = tel;
    }
    
    public String getCodigo() {return codigo;}
    public String getNome() {return nome;}
    public String getCep() {return cep;}
    public String getNum() {return num;}
    public String getEmail() {return email;}
    public String getTel() {return tel;}
    
    @Override
    public String toString() {
       return nome + "(Código: " + codigo +  ")";
    }
}