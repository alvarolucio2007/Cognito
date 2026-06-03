package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConn {

  // JDBC URL, username, and password of PostgreSQL server
  private static final String url = "jdbc:postgresql://localhost/mydb";
  private static final String user = "Cognito";
  private static final String password = "Veritas";

  public static Connection connect() {
    Connection conn = null;
    try {
      // Connect to PostgreSQL database
      conn = DriverManager.getConnection(url, user, password);

      // Check if connection was successful
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
