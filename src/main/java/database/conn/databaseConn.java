package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseConn {

  // JDBC URL, username, and password of PostgreSQL server

  // Eu mudei as credenciais aqui para que apontem pro meu PostgreSQL local
  // (cognitodb)
  // e usem o usuário (cognito_user) com a senha Cognito123.
  // Em outros ambientes, a gente só precisa alterar esses campos conforme as
  // credenciais locais.
  private static final String url = "jdbc:postgresql://localhost:5432/cognitodb";
  private static final String user = "cognito_user";
  private static final String password = "Cognito123";

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

  public static void migrate() {
    String query = """
        CREATE TABLE IF NOT EXISTS usuario (
        PK_id_usuario SERIAL PRIMARY KEY,
        usuario_nome VARCHAR(150) NOT NULL,
        usuario_email VARCHAR(100) NOT NULL,
        usuario_senha VARCHAR(16) NOT NULL,
        usuario_data_nascimento TIMESTAMP NOT NULL
        );
        CREATE TABLE IF NOT EXISTS aula (
        PK_id_aula SERIAL PRIMARY KEY,
        aula_titulo VARCHAR(100) NOT NULL,
        aula_descricao VARCHAR(300) NOT NULL,
        aula_nivel VARCHAR(20) NOT NULL
        );
        CREATE TABLE IF NOT EXISTS teste_nivelamento(
        PK_id_teste_nivelamento SERIAL PRIMARY KEY,
        teste_nivelamento_nivel_detectado VARCHAR(20),
        teste_nivelamento_data_realizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FK_id_usuario INT REFERENCES usuario(PK_id_usuario)
        );
        CREATE TABLE IF NOT EXISTS configuracao(
        PK_id_configuracao SERIAL PRIMARY KEY,
        configuracao_alto_contraste BOOLEAN DEFAULT FALSE,
        configuracao_texto_ampliado BOOLEAN DEFAULT FALSE,
        configuracao_sensibilidade_toque FLOAT DEFAULT 1.0,
        configuracao_modo_voz BOOLEAN DEFAULT FALSE,
        FK_id_usuario INT REFERENCES usuario(PK_id_usuario)
        );
        CREATE TABLE IF NOT EXISTS atividade(
        PK_id_atividade SERIAL PRIMARY KEY,
        atividade_pergunta VARCHAR(300) NOT NULL,
        atividade_resposta VARCHAR(200) NOT NULL,
        atividade_tipo VARCHAR(50) NOT NULL,
        FK_id_aula INT REFERENCES aula(PK_id_aula)
        );
                            """;
    try (Connection conn = DriverManager.getConnection(url, user, password);) {
      Statement stmt = conn.createStatement();
      stmt.execute(query);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
