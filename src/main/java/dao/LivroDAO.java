package dao;
import model.Livro;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LivroDAO {

    public boolean adicionarLivro(Livro livro)  {
        String sql = "INSERT INTO livros (titulo, autor, disponivel) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setBoolean(3, livro.isDisponivel());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se o livro foi adicionado com sucesso
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false em caso de erro
    }


    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getBoolean("disponivel")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public boolean atualizarLivro(int id, String novoTitulo, String novoAutor) {
        if (!livroExiste(id)) {
            System.out.println("Livro com ID " + id + " não encontrado.");
            return false;
        }
        String sql = "UPDATE livros SET titulo = ?, autor = ? WHERE id = ?";


        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoTitulo);
            stmt.setString(2, novoAutor);
            stmt.setInt(3, id);


            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se alguma linha foi afetada

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removerLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se o livro foi removido
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false em caso de erro
    }
    public boolean livroExiste(int id) {
        String sql = "SELECT COUNT(*) FROM livros WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o livro existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


