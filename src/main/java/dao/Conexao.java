package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexao {



        private static final String URL = "jdbc:mysql://127.0.0.1:3306/library";
        private static final String USUARIO = "root";
        private static final String SENHA = "Maravilhosa12@";

        public static Connection conectar() {
            try {return DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (SQLException e) {
                System.out.println("Erro na conexão: " + e.getMessage());
                return null;
            }
        }
}

