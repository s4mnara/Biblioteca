import dao.Conexao;

import java.sql.Connection;

public class TesteConexao {

        public static void main(String[] args) {
            Connection conexao = Conexao.conectar();
            if (conexao != null) {
                System.out.println("Conexão bem-sucedida! ✅");
            } else {
                System.out.println("Falha na conexão. ❌");
            }
        }
    }


