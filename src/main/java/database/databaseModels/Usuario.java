package database.databaseModels;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import database.databaseConn;

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

  public void criar(Usuario usuario) {
    String query = ""; // TODO: Receber e inserir aqui o query
    try (Connection conn = databaseConn.connect()) {

    } catch (SQLException e) {
      System.out.println("Erro no banco ou fechamento da conexão: " + e.getMessage());
    }

  }

}
