package database.databaseModels;

public class Atividade {
  private Long idAtividade;
  private String atividadePergunta;
  private String atividadeResposta;
  private String atividadeTipo;
  private Long FKIdAula;

  public Long getIdAtividade() {
    return idAtividade;
  }

  public void setIdAtividade(Long idAtividade) {
    this.idAtividade = idAtividade;
  }

  public String getAtividadePergunta() {
    return atividadePergunta;
  }

  public void setAtividadePergunta(String atividadePergunta) {
    this.atividadePergunta = atividadePergunta;
  }

  public String getAtividadeResposta() {
    return atividadeResposta;
  }

  public void setAtividadeResposta(String atividadeResposta) {
    this.atividadeResposta = atividadeResposta;
  }

  public String getAtividadeTipo() {
    return atividadeTipo;
  }

  public void setAtividadeTipo(String atividadeTipo) {
    this.atividadeTipo = atividadeTipo;
  }

  public Long getFKIdAula() {
    return FKIdAula;
  }

  public void setFKIdAula(Long fKIdAula) {
    FKIdAula = fKIdAula;
  }

  public Atividade(Long idAtividade, String atividadePergunta, String atividadeResposta, String atividadeTipo,
      Long fKIdAula) {
    this.idAtividade = idAtividade;
    this.atividadePergunta = atividadePergunta;
    this.atividadeResposta = atividadeResposta;
    this.atividadeTipo = atividadeTipo;
    FKIdAula = fKIdAula;
  }

}
