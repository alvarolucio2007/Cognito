package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConn {

  // JDBC URL, username, and password of PostgreSQL server
  private final String url = "jdbc:postgresql://localhost/mydb";
  private final String user = "postgres";
  private final String password = "root";

  /**
   * Connect to the PostgreSQL database
   *
   * @return a Connection object
   */
  public Connection connect() {
    Connection conn = null;
    try {
      // Connect to PostgreSQL database
      conn = DriverManager.getConnection(url, user, password);

      // Check if connection was successful
      if (conn != null) {
        System.out.println("Connected to the PostgreSQL server successfully.");
      } else {
        System.out.println("Failed to make connection!");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return conn;
  }

  public static void main(String[] args) {
    databaseConn app = new databaseConn();
    // Connect to the PostgreSQL database
    app.connect();
  }
}
