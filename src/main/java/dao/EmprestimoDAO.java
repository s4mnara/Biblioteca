package dao;
import model.Emprestimo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoDAO {
    public int adicionarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo == null || emprestimo.getIdLivro() <= 0 || emprestimo.getIdUsuario() <= 0 || emprestimo.getDataEmprestimo() == null) {
            throw new IllegalArgumentException("Erro: Empréstimo inválido. Verifique os dados.");
        }

        int idGerado = -1;
        String sql = "INSERT INTO emprestimos (livro_id, usuario_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, emprestimo.getIdLivro());
            stmt.setInt(2, emprestimo.getIdUsuario());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));

            if (emprestimo.getDataDevolucao() != null) {
                stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idGerado = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar empréstimo: " + e.getMessage());
            e.printStackTrace(); // Exibe detalhes do erro
        }

        return idGerado;
    }


    public Emprestimo buscarPorId(int id) {
        String sql = "SELECT * FROM emprestimos WHERE id = ?";
        Emprestimo emprestimo = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.out.println("Erro: Conexão retornou null!");
                return null;
            }

            System.out.println("Buscando empréstimo com ID: " + id);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setIdUsuario(rs.getInt("usuario_id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emprestimo.setDevolvido(rs.getBoolean("devolvido"));

                // Adicionando logs
                Date dataDevolucaoDoBanco = rs.getDate("data_devolucao");
                System.out.println("Data de devolução lida do banco (Date): " + dataDevolucaoDoBanco);

                if (dataDevolucaoDoBanco != null) {
                    LocalDate dataDevolucaoLocalDate = dataDevolucaoDoBanco.toLocalDate();
                    System.out.println("Data de devolução lida do banco (LocalDate): " + dataDevolucaoLocalDate);
                    emprestimo.setDataDevolucao(dataDevolucaoLocalDate);
                } else {
                    emprestimo.setDataDevolucao(null);
                }

                System.out.println("Empréstimo encontrado: " + emprestimo.getId());
            } else {
                System.out.println("Nenhum empréstimo encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimo: " + e.getMessage());
            e.printStackTrace();
        }

        return emprestimo;
    }

    public List<Emprestimo> listarTodos() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("livro_id"),
                        rs.getDate("data_emprestimo").toLocalDate(),
                        rs.getBoolean("devolvido")
                );
                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public boolean atualizarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo == null || emprestimo.getId() <= 0) {
            System.err.println("Erro: O ID do empréstimo deve ser válido e maior que zero!");
            return false;
        }

        String sql = "UPDATE emprestimos SET devolvido = ?, data_devolucao = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Inicia a transação

            stmt.setBoolean(1, emprestimo.isDevolvido());
            if (emprestimo.getDataDevolucao() != null) {
                stmt.setDate(2, Date.valueOf(emprestimo.getDataDevolucao()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setInt(3, emprestimo.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                conn.commit();
                System.out.println("Empréstimo atualizado com sucesso! ID: " + emprestimo.getId());
                return true;
            } else {
                System.err.println("Erro: Nenhum empréstimo encontrado com o ID: " + emprestimo.getId());
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar empréstimo (ID: " + emprestimo.getId() + "): " + e.getMessage());
            return false;
        }
    }
        public boolean removerEmprestimo ( int id){
            String sql = "DELETE FROM emprestimos WHERE id = ?";
            boolean removido = false;

            try (Connection conn = Conexao.conectar();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                int linhasAfetadas = stmt.executeUpdate();
                removido = linhasAfetadas > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return removido;
        }
    }
