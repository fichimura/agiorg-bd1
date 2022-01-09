/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configs.Connect;
import mapper.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

public class UsuarioDAO {

    public void cadastrar(Usuario u) {
        Connection con = Connect.getConnection();
        String sql = "INSERT INTO usuario (nome, cpf, email, celular, senha, saldo_total) VALUES (?,?,?,?, MD5(?), ?)";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, u.getNome());
            smt.setString(2, u.getCpf());
            smt.setString(3, u.getEmail());
            smt.setString(4, u.getCelular());
            smt.setString(5, u.getSenha());
            smt.setDouble(6, u.getSaldoTotal());
            smt.executeUpdate();
            smt.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar" + e.getMessage());

        }
    }

    public void atualizar(Usuario u) {
        Connection con = Connect.getConnection();
        String sql = "UPDATE usuario SET nome = ?, cpf = ?, email = ?, celular = ?, saldo_total = ? WHERE id_usuario = ?";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {

            smt.setString(1, u.getNome());
            smt.setString(2, u.getCpf());
            smt.setString(3, u.getEmail());
            smt.setString(4, u.getCelular());
            smt.setDouble(5, u.getSaldoTotal());
            smt.setInt(6, u.getId_usuario());
            smt.executeUpdate();

            smt.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar registro.");
        }
    }

    public void excluir(Usuario u) {
        Connection con = Connect.getConnection();
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir usuario "
                + u.getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            try ( PreparedStatement smt = con.prepareStatement(sql)) {
                smt.setInt(1, u.getId_usuario());
                smt.executeUpdate();
                smt.close();
                con.close();
                JOptionPane.showMessageDialog(null, "Excluido com sucesso.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir registro.");
            }
        }
    }

    public List<Usuario> listarTodos() {
        Connection con = Connect.getConnection();
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nome, cpf, email, celular, senha, saldo_total FROM usuario ORDER BY nome";
        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            ResultSet resultado = smt.executeQuery();
            while (resultado.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(resultado.getInt("id_usuario"));
                u.setNome(resultado.getString("nome"));
                u.setCpf(resultado.getString("cpf"));
                u.setEmail(resultado.getString("email"));
                u.setCelular(resultado.getString("celular"));
                u.setSaldoTotal(resultado.getDouble("saldo_total"));
                u.setSenha(resultado.getString("senha"));
                lista.add(u);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os registros");
        }

        return lista;
    }

    public List<Usuario> pesquisar(String nome) {
        Connection con = Connect.getConnection();
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nome, cpf, email, celular, senha, saldo_total FROM usuario WHERE nome = ? ORDER BY nome";
        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, nome);
            ResultSet resultado = smt.executeQuery();
            while (resultado.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(resultado.getInt("id_usuario"));
                u.setNome(resultado.getString("nome"));
                u.setCpf(resultado.getString("cpf"));
                u.setEmail(resultado.getString("email"));
                u.setCelular(resultado.getString("celular"));
                u.setSaldoTotal(resultado.getDouble("saldo_total"));
                u.setSenha(resultado.getString("senha"));
                lista.add(u);
            }
            smt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar os registros");
        }

        return lista;
    }

    public Usuario login(String email, String senha) {
        Connection con = Connect.getConnection();
        Usuario u = new Usuario();
        String sql = "SELECT id_usuario, nome, cpf, email, celular, senha, saldo_total FROM usuario WHERE email = ? AND senha = md5(?)";

        try ( PreparedStatement smt = con.prepareStatement(sql)) {
            smt.setString(1, email);
            smt.setString(2, senha);
            ResultSet resultado = smt.executeQuery();
            resultado.next();
            if (resultado.getInt("id_usuario") > 0) {
                u.setId_usuario(resultado.getInt("id_usuario"));
                u.setNome(resultado.getString("nome"));
                u.setCpf(resultado.getString("cpf"));
                u.setEmail(resultado.getString("email"));
                u.setCelular(resultado.getString("celular"));
                u.setSaldoTotal(resultado.getDouble("saldo_total"));
                u.setSenha(resultado.getString("senha"));
            } else {
                JOptionPane.showMessageDialog(null, " Usuário ou senha incorreto.");
            }

        } catch (Exception e) {
        }
        return u;

    }
}
