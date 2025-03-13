package dao;
import model.Usuario;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Controla a ordem dos testes
public class TesteUsuarioDAO {

    private static UsuarioDAO usuarioDAO;

    @BeforeAll
    static void setup() {
        usuarioDAO = new UsuarioDAO();
    }

    @Test
    @Order(1)
    void testAdicionarUsuario() throws SQLException {
        Usuario usuario = new Usuario("Maria Silva", "maria@email.com");
        int idGerado = usuarioDAO.adicionarUsuario(usuario);

        assertTrue(idGerado > 0, "O ID gerado deve ser maior que 0");
    }

    @Test
    @Order(2)
    void testBuscarUsuarioPorId() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Criar e inserir um usuário antes de buscar
        Usuario usuario = new Usuario("Maria", "maria@email.com");
        int idInserido = usuarioDAO.adicionarUsuario(usuario); // Supondo que `inserir` retorne o ID gerado

        // Buscar o usuário recém-inserido
        Usuario usuarioEncontrado = usuarioDAO.buscarPorId(idInserido);

        // Testar se ele foi encontrado
        assertNotNull(usuarioEncontrado, "Usuário deve existir no banco de dados");
    }

    @Test
    @Order(3)
    void testListarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        assertFalse(usuarios.isEmpty(), "A lista de usuários não deve estar vazia");
    }

    @Test
    @Order(4)
    void testAtualizarUsuario() throws SQLException {
        Usuario usuario = new Usuario(); // Certifique-se de inicializar
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int idInserido = usuarioDAO.adicionarUsuario(usuario); // Insere no banco e obtém ID

        // Buscar usuário salvo antes de atualizar
        usuario = usuarioDAO.buscarPorId(idInserido);
        if (usuario == null) {
            fail("Usuário não encontrado no banco");
        }

        usuario.setNome("Novo Nome"); // Agora usuário não será null
        boolean atualizado = usuarioDAO.atualizarUsuario(usuario);
        assertTrue(atualizado, "O usuário deve ser atualizado com sucesso");
    }


    @Test
    @Order(5)
    void testRemoverUsuario() throws SQLException {
        boolean removido = usuarioDAO.removerUsuario(1);
        assertTrue(removido, "Usuário deve ser removido");

        Usuario usuarioRemovido = usuarioDAO.buscarPorId(1);
        assertNull(usuarioRemovido, "Usuário não deve existir após remoção");
    }
}
