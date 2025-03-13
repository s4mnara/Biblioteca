package dao;
import model.Livro;
import java.util.List;
import org.junit.jupiter.api.*;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Controla a ordem dos testes

public class TesteLivroDAO {
    private static LivroDAO livroDAO;

    @BeforeAll
    static void setup() {
        livroDAO = new LivroDAO();
    }
    @Test
    @Order(1)
    void testAdicionarLivro() {
        Livro livro = new Livro("Código Limpo", "Robert C. Martin");
        boolean livroAdicionado = livroDAO.adicionarLivro(livro);

        assertTrue(livroAdicionado, "O livro deveria ser adicionado com sucesso.");
    }

    @Test
    @Order(2)
    void testListarLivros() {
        List<Livro> livros = livroDAO.listarLivros();
        assertFalse(livros.isEmpty(), "A lista de livros não deveria estar vazia.");
    }

    @Test
    @Order(3)
    void testAtualizarLivro() {
        // Supondo que o ID do livro no banco seja 1, vamos atualizar o título e autor
        boolean livroAtualizado = livroDAO.atualizarLivro(1, "Novo Título", "Novo Autor");

        assertTrue(livroAtualizado, "O livro deveria ser atualizado com sucesso.");
    }

    @Test
    @Order(4)
    void testRemoverLivro() {
        // Supondo que o ID do livro no banco seja 1, vamos removê-lo
        boolean livroRemovido = livroDAO.removerLivro(1);

        assertTrue(livroRemovido, "O livro deveria ser removido com sucesso.");
    }
}

