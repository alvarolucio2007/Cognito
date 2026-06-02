package database.databaseModels;

import java.sql.Timestamp;

public class TesteNivelamento {
  private Long idTesteNivelamento;
  private String testeNivelamentoNivelDetectado;
  private Timestamp testeNivelamentoDataRealizacao;
  private Long FKIdUsuario;

  public Long getIdTesteNivelamento() {
    return idTesteNivelamento;
  }

  public void setIdTesteNivelamento(Long idTesteNivelamento) {
    this.idTesteNivelamento = idTesteNivelamento;
  }

  public String getTesteNivelamentoNivelDetectado() {
    return testeNivelamentoNivelDetectado;
  }

  public void setTesteNivelamentoNivelDetectado(String testeNivelamentoNivelDetectado) {
    this.testeNivelamentoNivelDetectado = testeNivelamentoNivelDetectado;
  }

  public Timestamp getTesteNivelamentoDataRealizacao() {
    return testeNivelamentoDataRealizacao;
  }

  public void setTesteNivelamentoDataRealizacao(Timestamp testeNivelamentoDataRealizacao) {
    this.testeNivelamentoDataRealizacao = testeNivelamentoDataRealizacao;
  }

  public Long getFKIdUsuario() {
    return FKIdUsuario;
  }

  public void setFKIdUsuario(Long fKIdUsuario) {
    FKIdUsuario = fKIdUsuario;
  }

  public TesteNivelamento(Long idTesteNivelamento, String testeNivelamentoNivelDetectado,
      Timestamp testeNivelamentoDataRealizacao, Long fKIdUsuario) {
    this.idTesteNivelamento = idTesteNivelamento;
    this.testeNivelamentoNivelDetectado = testeNivelamentoNivelDetectado;
    this.testeNivelamentoDataRealizacao = testeNivelamentoDataRealizacao;
    FKIdUsuario = fKIdUsuario;
  }

}
