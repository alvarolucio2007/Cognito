package database.databaseModels;

public class Configuracoes {
  private Long idConfiguracao;
  private boolean configuracaoAltoContraste;
  private boolean configuracaoTextoAmpliado;
  private float configuracaoSensibilidadeToque;
  private boolean configuracaoModoVoz;
  private Long FKidUsuario;

  public Long getIdConfiguracao() {
    return idConfiguracao;
  }

  public void setIdConfiguracao(Long idConfiguracao) {
    this.idConfiguracao = idConfiguracao;
  }

  public boolean isConfiguracaoAltoContraste() {
    return configuracaoAltoContraste;
  }

  public void setConfiguracaoAltoContraste(boolean configuracaoAltoContraste) {
    this.configuracaoAltoContraste = configuracaoAltoContraste;
  }

  public boolean isConfiguracaoTextoAmpliado() {
    return configuracaoTextoAmpliado;
  }

  public void setConfiguracaoTextoAmpliado(boolean configuracaoTextoAmpliado) {
    this.configuracaoTextoAmpliado = configuracaoTextoAmpliado;
  }

  public float getConfiguracaoSensibilidadeToque() {
    return configuracaoSensibilidadeToque;
  }

  public void setConfiguracaoSensibilidadeToque(float configuracaoSensibilidadeToque) {
    this.configuracaoSensibilidadeToque = configuracaoSensibilidadeToque;
  }

  public boolean isConfiguracaoModoVoz() {
    return configuracaoModoVoz;
  }

  public void setConfiguracaoModoVoz(boolean configuracaoModoVoz) {
    this.configuracaoModoVoz = configuracaoModoVoz;
  }

  public Long getFKidUsuario() {
    return FKidUsuario;
  }

  public void setFKidUsuario(Long fKidUsuario) {
    FKidUsuario = fKidUsuario;
  }

  public Configuracoes(Long idConfiguracao, boolean configuracaoAltoContraste, boolean configuracaoTextoAmpliado,
      float configuracaoSensibilidadeToque, boolean configuracaoModoVoz, Long fKidUsuario) {
    this.idConfiguracao = idConfiguracao;
    this.configuracaoAltoContraste = configuracaoAltoContraste;
    this.configuracaoTextoAmpliado = configuracaoTextoAmpliado;
    this.configuracaoSensibilidadeToque = configuracaoSensibilidadeToque;
    this.configuracaoModoVoz = configuracaoModoVoz;
    FKidUsuario = fKidUsuario;
  }

}
