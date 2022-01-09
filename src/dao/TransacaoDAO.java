/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configs.Connect;
import mapper.Transacao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import mapper.Usuario;

public class TransacaoDAO {

    Transacao t = new Transacao();

    public Transacao getT() {
        return t;
    }

    public void setT(Transacao t) {
        this.t = t;
    }

    public void cadastrar(Transacao t, Usuario u) {
        t.setUsuario(u);
        t.setFK_id_usuario(u.getId_usuario());
        Connection con = Connect.getConnection();
        String sql = "INSERT INTO transacao (descricao, quantia, data_transacao, categoria, FK_id_usuario) VALUES (?,?,?,?,?)";
        String sqlSaldo = "UPDATE usuario SET saldo_total = ? WHERE id_usuario = ?";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {

            try ( PreparedStatement smt2 = con.prepareStatement(sqlSaldo)) {
                double quantia = 0;
                if ("ENTRADA".equals(t.getCategoria())) {
                    quantia = u.getSaldoTotal() + t.getQuantia();
                } else {
                    quantia = u.getSaldoTotal() - t.getQuantia();
                }

                smt2.setDouble(1, quantia);
                smt2.setDouble(2, t.getFK_id_usuario());

                smt2.executeUpdate();
                smt2.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar." + e.getMessage());
            }

            smt.setString(1, t.getDescricao());
            smt.setDouble(2, t.getQuantia());
            smt.setString(3, t.getData_transacao());
            smt.setString(4, t.getCategoria());
            smt.setInt(5, t.getFK_id_usuario());
            smt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso.");
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar." + e.getMessage());
        }

    }

    
    public void atualizar(Transacao t, Usuario u) {
        Connection con = Connect.getConnection();
        Transacao transacao = new Transacao();
        if (t.getFK_id_usuario() == u.getId_usuario()) {
            String sql = "UPDATE transacao SET descricao = ?,quantia = ?, data_transacao = ?, categoria = ? WHERE id_transacao = ?";
            String sql2 = "SELECT id_transacao, descricao, quantia, data_transacao, categoria, FK_id_usuario FROM transacao WHERE id_transacao = ?";
            try ( PreparedStatement smt = con.prepareStatement(sql2)) {
                smt.setString(1, String.valueOf(t.getId_transacao()));
                ResultSet resultado = smt.executeQuery();

                while (resultado.next()) {

                    transacao.setId_transacao(resultado.getInt("id_transacao"));
                    transacao.setDescricao(resultado.getString("descricao"));
                    transacao.setQuantia(resultado.getDouble("quantia"));
                    transacao.setData_transacao(resultado.getString("data_transacao"));
                    transacao.setCategoria(resultado.getString("categoria"));
                    transacao.setFK_id_usuario(resultado.getInt("FK_id_usuario"));
                }

                try ( PreparedStatement smt2 = con.prepareStatement(sql)) {
                    smt2.setString(1, t.getDescricao());
                    smt2.setDouble(2, t.getQuantia());
                    smt2.setString(3, t.getData_transacao());
                    smt2.setString(4, t.getCategoria());
                    smt2.setInt(5, t.getId_transacao());

                    if (!t.getCategoria().equals(transacao.getCategoria())) {
                        if (t.getCategoria().equals("ENTRADA")) {
                            if (t.getQuantia() == transacao.getQuantia()) {
                                u.setSaldoTotal(u.getSaldoTotal() + t.getQuantia());
                            } else {

                                    u.setSaldoTotal(u.getSaldoTotal()+ t.getQuantia());

                            }
                        } else if (t.getCategoria().equals("SAIDA")) {
                            if (t.getQuantia() == transacao.getQuantia()) {
                                u.setSaldoTotal(u.getSaldoTotal() - t.getQuantia());
                            } else {
                                u.setSaldoTotal(u.getSaldoTotal() - t.getQuantia());
                            }
                        }
                    }

                    smt2.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Atualizado com sucesso.");
                    smt2.close();
                    con.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar." + e.getMessage());
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar." + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível alterar transação de outro usuário.");
        }
    }

    public void excluir(Transacao t, Usuario u) {
        Connection con = Connect.getConnection();
        if (t.getFK_id_usuario() == u.getId_usuario()) {
            String sql = "DELETE FROM transacao WHERE id_transacao = ?";
            int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir transação? " + t.getDescricao(), "Excluir transação", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                try ( PreparedStatement smt = con.prepareStatement(sql)) {
                    smt.setInt(1, t.getId_transacao());
                    smt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Excluído com sucesso.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir." + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível excluir transação de outro usuário.");
        }

    }

    public List<Transacao> listarTodos() {
        Connection con = Connect.getConnection();
        List<Transacao> listaTransacao = new ArrayList<>();
        String sql = "SELECT id_transacao, descricao, quantia, data_transacao, categoria, FK_id_usuario FROM transacao ORDER BY categoria";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            ResultSet resultado = smt.executeQuery();

            while (resultado.next()) {
                Transacao t = new Transacao();
                t.setId_transacao(resultado.getInt("id_transacao"));
                t.setDescricao(resultado.getString("descricao"));
                t.setQuantia(resultado.getDouble("quantia"));
                t.setData_transacao(resultado.getString("data_transacao"));
                t.setCategoria(resultado.getString("categoria"));
                t.setFK_id_usuario(resultado.getInt("FK_id_usuario"));

                listaTransacao.add(t);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a lista de transacoes." + e.getMessage());
        }
        return listaTransacao;
    }

    public List<Transacao> buscarData(String data) {
        Connection con = Connect.getConnection();
        List<Transacao> listaTransacao = new ArrayList<>();
        String sql = "SELECT id_transacao, descricao, quantia, data_transacao, categoria, FK_id_usuario FROM transacao WHERE data_transacao = ? ORDER BY categoria";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, data);
            ResultSet resultado = smt.executeQuery();

            while (resultado.next()) {
                Transacao t = new Transacao();
                t.setId_transacao(resultado.getInt("id_transacao"));
                t.setDescricao(resultado.getString("descricao"));
                t.setQuantia(resultado.getDouble("quantia"));
                t.setData_transacao(resultado.getString("data_transacao"));
                t.setCategoria(resultado.getString("categoria"));
                t.setFK_id_usuario(resultado.getInt("FK_id_usuario"));
                listaTransacao.add(t);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a lista de transacoes." + e.getMessage());
        }
        return listaTransacao;
    }

    public List<Transacao> buscarCategoria(String categoria) {
        Connection con = Connect.getConnection();
        List<Transacao> listaTransacao = new ArrayList<>();
        String sql = "SELECT id_transacao, descricao, quantia, data_transacao, categoria, FK_id_usuario FROM transacao WHERE categoria = ? ORDER BY id_transacao";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, categoria);
            ResultSet resultado = smt.executeQuery();

            while (resultado.next()) {
                Transacao t = new Transacao();
                t.setId_transacao(resultado.getInt("id_transacao"));
                t.setDescricao(resultado.getString("descricao"));
                t.setQuantia(resultado.getDouble("quantia"));
                t.setData_transacao(resultado.getString("data_transacao"));
                t.setCategoria(resultado.getString("categoria"));
                t.setFK_id_usuario(resultado.getInt("FK_id_usuario"));
                listaTransacao.add(t);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar a lista de transacoes." + e.getMessage());
        }
        return listaTransacao;
    }

}
