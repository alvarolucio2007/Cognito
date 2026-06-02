package database.databaseModels;

public class Aula {
  private Long idAula;
  private String aulaTitulo;
  private String aulaDescricao;
  private String aulaNivel;

  public Aula(Long idAula, String aulaTitulo, String aulaDescricao, String aulaNivel) {
    this.idAula = idAula;
    this.aulaTitulo = aulaTitulo;
    this.aulaDescricao = aulaDescricao;
    this.aulaNivel = aulaNivel;
  }

  public Long getIdAula() {
    return idAula;
  }

  public void setIdAula(Long idAula) {
    this.idAula = idAula;
  }

  public String getAulaTitulo() {
    return aulaTitulo;
  }

  public void setAulaTitulo(String aulaTitulo) {
    this.aulaTitulo = aulaTitulo;
  }

  public String getAulaDescricao() {
    return aulaDescricao;
  }

  public void setAulaDescricao(String aulaDescricao) {
    this.aulaDescricao = aulaDescricao;
  }

  public String getAulaNivel() {
    return aulaNivel;
  }

  public void setAulaNivel(String aulaNivel) {
    this.aulaNivel = aulaNivel;
  }
}
