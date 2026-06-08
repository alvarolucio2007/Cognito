package database.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

  public static void migrate(Connection conn) {
    String query = """
            CREATE TABLE IF NOT EXISTS usuario (
                PK_id_usuario SERIAL PRIMARY KEY,
                usuario_nome VARCHAR(150) NOT NULL,
                usuario_email VARCHAR(100) NOT NULL UNIQUE, -- Adicionado UNIQUE para evitar emails duplicados
                usuario_senha VARCHAR(255) NOT NULL,        -- Aumentado para suportar Hash de senha (BCrypt)
                usuario_data_nascimento TIMESTAMP NOT NULL
            );

            CREATE TABLE IF NOT EXISTS aula (
                PK_id_aula SERIAL PRIMARY KEY,
                aula_titulo VARCHAR(100) NOT NULL,
                aula_descricao VARCHAR(300) NOT NULL,
                aula_nivel VARCHAR(20) NOT NULL
            );

            CREATE TABLE IF NOT EXISTS teste_nivelamento (
                PK_id_teste_nivelamento SERIAL PRIMARY KEY,
                teste_nivelamento_nivel_detectado VARCHAR(20),
                teste_nivelamento_data_realizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FK_id_usuario INT REFERENCES usuario(PK_id_usuario) ON DELETE CASCADE
            );

            CREATE TABLE IF NOT EXISTS configuracao (
                PK_id_configuracao SERIAL PRIMARY KEY,
                configuracao_alto_contraste BOOLEAN DEFAULT FALSE, -- Removidas as aspas de 'FALSE' (boolean não usa aspas)
                configuracao_texto_ampliado BOOLEAN DEFAULT FALSE,
                configuracao_sensibilidade_toque FLOAT DEFAULT 1.0,
                configuracao_modo_voz BOOLEAN DEFAULT FALSE,
                FK_id_usuario INT REFERENCES usuario(PK_id_usuario) ON DELETE CASCADE
            );

            CREATE TABLE IF NOT EXISTS atividade ( -- Corrigido: Faltava a abertura da tabela
                PK_id_atividade SERIAL PRIMARY KEY,
                atividade_pergunta VARCHAR(300) NOT NULL,
                atividade_resposta VARCHAR(200) NOT NULL,
                atividade_tipo VARCHAR(50) NOT NULL,
                FK_id_aula INT REFERENCES aula(PK_id_aula) ON DELETE CASCADE
            );

            CREATE TABLE IF NOT EXISTS usuario_progresso (
                id_progresso SERIAL PRIMARY KEY,
                id_usuario INT REFERENCES usuario(PK_id_usuario) ON DELETE CASCADE,     -- Corrigido para INT e adicionada a constraint de FK
                id_aula INT REFERENCES aula(PK_id_aula) ON DELETE SET NULL,             -- Adicionada a constraint de FK
                tipo_conclusao VARCHAR(50),
                data_conclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                id_atividade INT REFERENCES atividade(PK_id_atividade) ON DELETE SET NULL -- Corrigido: Removida a vírgula final e adicionada FK
            );
        """;

    // Try-with-resources: Garante o fechamento automático do Statement
    try (Statement stmt = conn.createStatement()) {
      stmt.execute(query);
      System.out.println("Tabelas criadas ou já existentes com sucesso!");
    } catch (SQLException e) {
      System.err.println("Erro ao criar as tabelas no banco de dados:");
      e.printStackTrace(); // Mostra o erro real na tela em vez do "AAAA"
    }
  }

  public static void rodarSeedInicial(Connection conn) {
    String checarAulas = "SELECT COUNT(*) FROM aula";

    try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(checarAulas)) {

      // Se retornar 0, significa que o banco acabou de ser resetado
      if (rs.next() && rs.getInt(1) == 0) {
        System.out.println("🌱 Banco de dados zerado detectado. Rodando Seed de dados...");

        // 1. Inserindo as 5 Aulas necessárias
        String seedAulas = """
            INSERT INTO aula (PK_id_aula, aula_titulo, aula_descricao, aula_nivel) VALUES
            (1, 'IA: O que é e para que serve?', 'Introdução simples sobre como a inteligência artificial funciona no celular e na TV.', 'Iniciante'),
            (2, 'ChatGPT para o dia a dia', 'Como conversar com a IA para tirar dúvidas, pedir receitas ou sugestões de viagens.', 'Iniciante'),
            (3, 'Atualizando seu Currículo com IA', 'Usando assistentes virtuais para organizar suas experiências profissionais e destacar suas qualidades.', 'Intermediário'),
            (4, 'Segurança e Golpes Digitais', 'Aprenda a usar a IA para identificar mensagens falsas e se proteger de fraudes no WhatsApp.', 'Iniciante'),
            (5, 'Como brilhar em Entrevistas', 'Prática guiada com IA para simular conversas de emprego e ganhar confiança para retornar ao mercado.', 'Intermediário');

            -- Sincroniza o contador do SERIAL das aulas para o número 5
            SELECT setval('aula_pk_id_aula_seq', 5);
                                    """;
        stmt.execute(seedAulas);

        // 2. Inserindo as Atividades atreladas a essas aulas
        String seedAtividades = """
            INSERT INTO atividade (PK_id_atividade, atividade_pergunta, atividade_resposta, atividade_tipo, FK_id_aula) VALUES
            (1, 'Qual destas opções melhor descreve a Inteligência Artificial no seu cotidiano?', 'Um assistente que ajuda a realizar tarefas e encontrar informações mais rápido.', 'Múltipla Escolha', 1),
            (2, 'Para que serve o campo de digitação (prompt) no ChatGPT?', 'Para escrever perguntas ou comandos que você quer que a IA responda.', 'Dissertativa', 2),
            (3, 'Ao usar IA para criar um currículo, o que é mais importante conferir?', 'Se todas as informações e experiências profissionais estão corretas e verdadeiras.', 'Múltipla Escolha', 3),
            (4, 'Se você receber um link suspeito no WhatsApp dizendo ser do banco, qual a primeira atitude?', 'Não clicar e verificar a informação em um canal oficial ou usar uma IA de segurança.', 'Múltipla Escolha', 4),
            (5, 'Verdadeiro ou Falso: A IA pode ser usada para praticar respostas de uma entrevista de emprego.', 'Verdadeiro', 'Verdadeiro/Falso', 5);

            -- Sincroniza o contador do SERIAL das atividades para o número 5
            SELECT setval('atividade_pk_id_atividade_seq', 5);
                                    """;
        stmt.execute(seedAtividades);

        System.out.println("✅ Seed finalizado! 5 Aulas e 5 Atividades criadas.");
      }
    } catch (SQLException e) {
      System.err.println("❌ Erro ao rodar o Seed do banco: " + e.getMessage());
    }
  }
}
