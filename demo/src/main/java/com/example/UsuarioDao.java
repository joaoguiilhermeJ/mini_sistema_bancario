package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private String url = "jdbc:postgresql://localhost:5432/sistema_bancario";
    private String usuario = "seu_usuario";
    private String senha = "sua_senha";

    public UsuarioDao() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }

    public void cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, cpf, saldo) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setFloat(3, usuario.getSaldo());

            stmt.executeUpdate();
            System.out.println("Usuário Cadastrado!!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuarioEncontrado = new Usuario(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getFloat("saldo"));
                usuarioEncontrado.setId(rs.getInt("id"));
                usuarios.add(usuarioEncontrado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuario: " + e.getMessage());
        }

        return usuarios;
    }

    public void atualizarSaldo(Usuario usuario) {
        String sql = "UPDATE usuarios SET saldo = ? WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, usuario.getSaldo());
            stmt.setInt(2, usuario.getId());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("Saldo atualizado com sucesso.");
            } else {
                System.out.println("Usuário não encontrado para atualizar saldo.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar saldo: " + e.getMessage());
        }
    }

    public void deletarUsuario(int id) {
        
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                
                System.out.println("Usuário deletado ");
            } else {
                
                System.out.println("Usuário não encontrado");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public int contarUsuarios() {
        String sql = "SELECT COUNT(*) AS total_usuarios FROM usuarios";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                
                return rs.getInt("total_usuarios");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao contar usuários: " + e.getMessage());
        }

        return 0;
    }
}