/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import configs.Connect;


public class LoginDAO {

    public void criaTabelaUsuarios() {
        Connection con = Connect.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS usuario (id_usuario INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
                + "nome VARCHAR(120) NOT NULL,\n"
                + "cpf VARCHAR(14) NOT NULL,\n"
                + "email VARCHAR(120) NOT NULL,\n"
                + "celular VARCHAR(14) NOT NULL,\n"
                + "senha VARCHAR(255) NOT NULL,\n"
                + "saldo_total DECIMAL(15,2) NOT NULL)";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.executeUpdate();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void criaTabelaTransacoes() {
        Connection con = Connect.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS transacao (id_transacao INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
                + "descricao VARCHAR(120)NOT NULL,\n"
                + "quantia DECIMAL(10,2)NOT NULL,\n"
                + "data_transacao VARCHAR(10)NOT NULL,\n"
                + "categoria VARCHAR(20)NOT NULL,\n"
                + "FK_id_usuario INT(6) UNSIGNED NOT NULL,\n"
                + "\n"
                + "FOREIGN KEY (FK_id_usuario) REFERENCES usuario(id_usuario))";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {

            smt.executeUpdate();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void criaTabelaContas() {
        Connection con = Connect.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS contas_pagar(id_contas INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
                + "descricao VARCHAR(120)NOT NULL,\n"
                + "quantia DECIMAL(10,2)NOT NULL,\n"
                + "data_conta VARCHAR(10)NOT NULL,\n"
                + "FK_id_usuario INT(6) UNSIGNED NOT NULL,\n"
                + "\n"
                + "FOREIGN KEY (FK_id_usuario) REFERENCES usuario(id_usuario))";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.executeUpdate();
            smt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
