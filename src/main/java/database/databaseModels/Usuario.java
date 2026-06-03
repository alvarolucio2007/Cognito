package database.databaseModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.conn.databaseConn;

public class Usuario {
  private Long pkIdUsuario;
  private String usuarioNome;
  private String usuarioEmail;
  private String usuarioSenha;
  private Timestamp usuarioDataNascimento;

  public Long getPkIdUsuario() {
    return pkIdUsuario;
  }

  public void setPkIdUsuario(Long pkIdUsuario) {
    this.pkIdUsuario = pkIdUsuario;
  }

  public String getUsuarioNome() {
    return usuarioNome;
  }

  public void setUsuarioNome(String usuarioNome) {
    this.usuarioNome = usuarioNome;
  }

  public String getUsuarioEmail() {
    return usuarioEmail;
  }

  public void setUsuarioEmail(String usuarioEmail) {
    this.usuarioEmail = usuarioEmail;
  }

  public String getUsuarioSenha() {
    return usuarioSenha;
  }

  public void setUsuarioSenha(String usuarioSenha) {
    this.usuarioSenha = usuarioSenha;
  }

  public Timestamp getUsuarioDataNascimento() {
    return usuarioDataNascimento;
  }

  public void setUsuarioDataNascimento(Timestamp usuarioDataNascimento) {
    this.usuarioDataNascimento = usuarioDataNascimento;
  }

  public Usuario(Long pkIdUsuario, String usuarioNome, String usuarioEmail, String usuarioSenha,
      Timestamp usuarioDataNascimento) {
    this.pkIdUsuario = pkIdUsuario;
    this.usuarioNome = usuarioNome;
    this.usuarioEmail = usuarioEmail;
    this.usuarioSenha = usuarioSenha;
    this.usuarioDataNascimento = usuarioDataNascimento;
  }

  public boolean criar(Usuario usuario) {
    String query = "INSERT INTO usuario (usuario_nome ,usuario_email,usuario_senha,usuario_data_nascimento) VALUES (?, ?, ?, ?)";
    try (Connection conn = databaseConn.connect()) {
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setString(1, usuario.getUsuarioNome());
      stmt.setString(2, usuario.getUsuarioEmail());
      stmt.setString(3, usuario.getUsuarioSenha());
      stmt.setTimestamp(4, usuario.getUsuarioDataNascimento());
      int linhasAfetadas = stmt.executeUpdate();
      if (linhasAfetadas > 0) {
        return true;
      }
    } catch (SQLException e) {
      System.out.println("Erro no banco ou fechamento da conexão: " + e.getMessage());
      return false;
    }
    return false;
  }

  public String getSenhaEmail(String email) throws SQLException {
    String query = "SELECT usuario_senha FROM usuario WHERE usuario_email = ?";
    try (Connection conn = databaseConn.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getString("usuario_senha") : null;
    }

  }
}
