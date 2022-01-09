/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configs.Connect;
import java.sql.Connection;
import mapper.ContasPagar;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import mapper.Usuario;

public class ContasPagarDAO {
    
    public void cadastrar(ContasPagar c, Usuario u) {
        c.setUsuario(u);
        c.setFK_id_usuario(u.getId_usuario());
        Connection con = Connect.getConnection();
        String sql = "INSERT INTO contas_pagar (descricao, quantia, data_conta, FK_id_usuario) VALUES(?,?,?,?)";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, c.getDescricao());
            smt.setDouble(2, c.getQuantia());
            smt.setString(3, c.getData_conta());
            smt.setInt(4, c.getFK_id_usuario());
            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso.");

            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar." + e.getMessage());
        }
    }

    public void atualizar(ContasPagar c, Usuario u) {
        Connection con = Connect.getConnection();

        String sql = "UPDATE contas_pagar SET descricao = ?,quantia = ?, data_conta = ? WHERE id_contas = ?";
        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, c.getDescricao());
            smt.setDouble(2, c.getQuantia());
            smt.setString(3, c.getData_conta());
            smt.setInt(4, c.getId_conta());
            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso.");
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar." + e.getMessage());
        }

    }

    public void excluir(ContasPagar c, Usuario u) {
        Connection con = Connect.getConnection();

            String sql = "DELETE FROM contas_pagar WHERE id_contas = ?";
            int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir transação? " + c.getDescricao(), "Excluir transação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                try ( PreparedStatement smt = con.prepareStatement(sql)) {
                    smt.setInt(1, c.getId_conta());
                    smt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Excluído com sucesso.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir." + e.getMessage());
                }
            }

    }

    public List<ContasPagar> listarTodos() {
        Connection con = Connect.getConnection();
        List<ContasPagar> listaContas = new ArrayList<>();
        String sql = "SELECT id_contas, descricao, quantia, data_conta, FK_id_usuario FROM contas_pagar ORDER BY id_contas";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            ResultSet resultado = smt.executeQuery();

            while (resultado.next()) {
                ContasPagar c = new ContasPagar();
                c.setId_conta(resultado.getInt("id_contas"));
                c.setDescricao(resultado.getString("descricao"));
                c.setQuantia(resultado.getDouble("quantia"));
                c.setData_conta(resultado.getString("data_conta"));
                c.setFK_id_usuario(resultado.getInt("FK_id_usuario"));
                listaContas.add(c);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a lista de transacoes." + e.getMessage());
        }
        return listaContas;
    }
}
