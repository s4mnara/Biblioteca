package model;
import java.time.LocalDate;
public class Emprestimo {
    private int id;
    private int idUsuario;
    private int idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao; // Adicionado para representar a data de devolução
    private boolean devolvido;

    public Emprestimo() {
    }

    // Construtor completo
    public Emprestimo(int id, int idUsuario, int idLivro, LocalDate dataEmprestimo, boolean devolvido) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = devolvido ? LocalDate.now() : null;
        this.devolvido = devolvido;
    }

    // Construtor sem ID (antes de ser salvo no banco)
    public Emprestimo(int idUsuario, int idLivro, LocalDate dataEmprestimo, boolean devolvido) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = devolvido ? LocalDate.now() : null;
        this.devolvido = devolvido;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() { // Getter para dataDevolucao
        return dataDevolucao;
    }


    public boolean isDevolvido() {
        return devolvido;
    }


    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        this.devolvido = (dataDevolucao != null);
    }

}

