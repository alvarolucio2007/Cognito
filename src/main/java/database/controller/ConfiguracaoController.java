package database.controller;

import database.conn.databaseConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConfiguracaoController {
    private Long idConfiguracao;
    private boolean configuracaoContraste;
    private boolean configuracaoTextoAmpliado;
    private float configuracaoSensibilidade;
    private boolean configuracaoModoVoz;
    private Long FKidUsuario;

    public Long getIdConfiguracao() { return idConfiguracao; }
    public void setIdConfiguracao(Long idConfiguracao) {
        this.idConfiguracao = idConfiguracao;
    }

    public boolean isConfiguracaoContraste() { return configuracaoContraste; }
    public void setConfiguracaoContraste(boolean configuracaoContraste) {
        this.configuracaoContraste = configuracaoContraste;
    }

    public boolean isConfiguracaoTextoAmpliado() { return configuracaoTextoAmpliado; }
    public void setConfiguracaoTextoAmpliado(boolean configuracaoTextoAmpliado) {
        this.configuracaoTextoAmpliado = configuracaoTextoAmpliado;
    }

    public float getConfiguracaoSensibilidade() { return configuracaoSensibilidade; }
    public void setConfiguracaoSensibilidade(float configuracaoSensibilidade) {
        this.configuracaoSensibilidade = configuracaoSensibilidade; }

    public boolean isConfiguracaoModoVoz() { return configuracaoModoVoz; }
    public void setConfiguracaoModoVoz(boolean configuracaoModoVoz) { this.configuracaoModoVoz = configuracaoModoVoz; }

    public Long getFKidUsuario() { return FKidUsuario; }
    public void setFKidUsuario(Long fKidUsuario) {
        FKidUsuario = fKidUsuario;
    }

    public ConfiguracaoController(Long idConfiguracao, boolean configuracaoContraste, boolean configuracaoTextoAmpliado,
                         float configuracaoSensibilidade, boolean configuracaoModoVoz, Long fKidUsuario) {
        this.idConfiguracao = idConfiguracao;
        this.configuracaoContraste = configuracaoContraste;
        this.configuracaoTextoAmpliado = configuracaoTextoAmpliado;
        this.configuracaoSensibilidade = configuracaoSensibilidade;
        this.configuracaoModoVoz = configuracaoModoVoz;
        FKidUsuario = fKidUsuario;
    }

    public boolean criar(ConfiguracaoController configuracoes) {
        String query = "INSERT INTO configuracao (configuracao_alto_contraste, configuracao_texto_ampliado, configuracao_sensibilidade_toque, configuracao_modo_voz, FK_id_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConn.connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setBoolean(1, configuracoes.isConfiguracaoContraste());
            stmt.setBoolean(2, configuracoes.isConfiguracaoTextoAmpliado());
            stmt.setFloat(3, configuracoes.getConfiguracaoSensibilidade());
            stmt.setBoolean(4, configuracoes.isConfiguracaoModoVoz());
            stmt.setLong(5, configuracoes.getFKidUsuario());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir configuração: " + e.getMessage());
            return false;
        }
    }
}
}
