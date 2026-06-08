package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConn {

  // JDBC URL, username, and password of PostgreSQL server

  // Eu mudei as credenciais aqui para que apontem pro meu PostgreSQL local (cognitodb)
  // e usem o usuário (cognito_user) com a senha Cognito123. 
  // Em outros ambientes, a gente só precisa alterar esses campos conforme as credenciais locais.
  private static final String url = "jdbc:postgresql://localhost:5432/cognitodb";
  private static final String user = "cognito_user";
  private static final String password = "Cognito123";

  public static Connection connect() {
    Connection conn = null;
    try {
      // Connect to PostgreSQL database
      conn = DriverManager.getConnection(url, user, password);

      // check if connection was successful
      if (conn != null) {
        System.out.println("Conectado ao banco de dados com sucesso.");
      } else {
        System.out.println("Conexão falhou.");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }
}
