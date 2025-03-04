package main;
import dao.LivroDAO;
import model.Livro;
import java.util.List;
public class TesteLivroDAO {

        public static void main(String[] args) {
            LivroDAO livroDAO = new LivroDAO();

            // 1. Adicionar um livro
            Livro novoLivro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien");
            livroDAO.adicionarLivro(novoLivro);

            // 2. Listar livros
            System.out.println("Lista de Livros:");
            List<Livro> livros = livroDAO.listarLivros();
            for (Livro livro : livros) {
                System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor());
            }

            // 3. Atualizar um livro
            if (!livros.isEmpty()) {
                livroDAO.atualizarLivro(livros.get(0).getId(), "O Hobbit", "J.R.R. Tolkien");
            }

            // 4. Remover um livro
            if (!livros.isEmpty()) {
                livroDAO.removerLivro(livros.get(0).getId());
            }
        }
    }


