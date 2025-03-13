package dao;

import model.Emprestimo;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Define a ordem dos testes
public class TesteEmprestimoDAO {
    private static EmprestimoDAO emprestimoDAO;
    private static int idGerado;

    @BeforeAll
    static void setup() {
        emprestimoDAO = new EmprestimoDAO();
    }

    @Test
    @Order(1)
    void testAdicionarEmprestimo() {
        Emprestimo emprestimo = new Emprestimo(1, 1, LocalDate.now(), false); // Agora funcionará corretamente
        idGerado = emprestimoDAO.adicionarEmprestimo(emprestimo);
        assertTrue(idGerado > 0, "O ID do empréstimo deve ser maior que 0");}
        @Test
    @Order(2)
    void testBuscarEmprestimoPorId() {
        // Criando um novo empréstimo
        Emprestimo novoEmprestimo = new Emprestimo(1, 1, LocalDate.now(), false);

        // Adicionando o empréstimo ao banco de dados e capturando o ID gerado
        int idGerado = emprestimoDAO.adicionarEmprestimo(novoEmprestimo);

        // Verificando se um ID válido foi gerado (normalmente, IDs são maiores que zero)
        assertTrue(idGerado > 0, "Erro ao adicionar o empréstimo no banco de dados");

        // Buscando o empréstimo pelo ID gerado
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(idGerado);

        // Depuração para verificar se o empréstimo foi encontrado
        System.out.println("Empréstimo encontrado? " + (emprestimo != null));

        // Verificação final
        assertNotNull(emprestimo, "Empréstimo deve existir no banco de dados");
    }


    @Test
    @Order(3)
    void testListarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoDAO.listarTodos();
        assertFalse(emprestimos.isEmpty(), "A lista de empréstimos não deve estar vazia");
    }

    @Test
    @Order(4)
    void testAtualizarEmprestimo() {
        // Criando um novo empréstimo com dados completos
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setIdLivro(1);
        emprestimo.setIdUsuario(1);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDevolvido(false); // Inicialmente, não devolvido

        // Adicionando o empréstimo e validando se foi salvo corretamente
        int idGerado = emprestimoDAO.adicionarEmprestimo(emprestimo);
        assertNotEquals(0, idGerado, "O ID gerado deve ser válido.");
        emprestimo.setId(idGerado);

        // Buscando o empréstimo criado para confirmar que foi salvo
        Emprestimo emprestimoBuscado = emprestimoDAO.buscarPorId(idGerado);
        assertNotNull(emprestimoBuscado, "O empréstimo deve existir no banco antes da atualização.");
        assertFalse(emprestimoBuscado.isDevolvido(), "O empréstimo inicialmente deve estar como não devolvido.");

        // Criando um novo objeto para atualização
        Emprestimo emprestimoAtualizado = new Emprestimo();
        emprestimoAtualizado.setId(idGerado);
        emprestimoAtualizado.setIdLivro(emprestimoBuscado.getIdLivro());
        emprestimoAtualizado.setIdUsuario(emprestimoBuscado.getIdUsuario());
        emprestimoAtualizado.setDataEmprestimo(emprestimoBuscado.getDataEmprestimo());
        emprestimoAtualizado.setDataDevolucao(LocalDate.now()); // Definir data de devolução
        emprestimoAtualizado.setDevolvido(true); // Alterar status para devolvido

        // Atualizando o empréstimo e verificando se foi bem-sucedido
        boolean atualizado = emprestimoDAO.atualizarEmprestimo(emprestimoAtualizado);
        assertTrue(atualizado, "O empréstimo deve ser atualizado com sucesso.");

        // Buscando novamente para validar a atualização
        Emprestimo emprestimoValidado = emprestimoDAO.buscarPorId(idGerado);
        assertNotNull(emprestimoValidado, "O empréstimo atualizado deve existir.");
        assertTrue(emprestimoValidado.isDevolvido(), "O status de devolução deve ser verdadeiro.");
        assertEquals(emprestimoAtualizado.getDataDevolucao(), emprestimoValidado.getDataDevolucao(),
                "A data de devolução deve ser atualizada corretamente.");
    }
    @Test
    @Order(5)
    void testRemoverEmprestimo() {
        boolean removido = emprestimoDAO.removerEmprestimo(idGerado);
        assertTrue(removido, "O empréstimo deve ser removido");

        Emprestimo emprestimoRemovido = emprestimoDAO.buscarPorId(idGerado);
        assertNull(emprestimoRemovido, "O empréstimo não deve existir após remoção");
    }
}