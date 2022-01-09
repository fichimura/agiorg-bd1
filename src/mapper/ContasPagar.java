/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mapper;

//ENTIDADE CONTAS A PAGAR
public class ContasPagar {

    //ATRIBUTOS
    private int id_conta;
    private String descricao;
    private double quantia;
    private String data_conta;
    private int FK_id_usuario;
    
    private Usuario usuario;
    
    //GETTERS E SETTERS
    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }

    public double getQuantia() {
        return quantia;
    }

    public void setQuantia(double quantia) {
        this.quantia = quantia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_conta() {
        return data_conta;
    }

    public void setData_conta(String data_conta) {
        this.data_conta = data_conta;
    }

    public int getFK_id_usuario() {
        return FK_id_usuario;
    }

    public void setFK_id_usuario(int FK_id_usuario) {
        this.FK_id_usuario = FK_id_usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    

}
