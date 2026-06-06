package database.databaseModels;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.conn.databaseConn;

public class Configuracao {
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
        this.configuracaoSensibilidade = configuracaoSensibilidade; 
    }

    public boolean isConfiguracaoModoVoz() { return configuracaoModoVoz; }
    public void setConfiguracaoModoVoz(boolean configuracaoModoVoz) { 
        this.configuracaoModoVoz = configuracaoModoVoz; 
    }

    public Long getFKidUsuario() { return FKidUsuario; }
    public void setFKidUsuario(Long fKidUsuario) {
        this.FKidUsuario = fKidUsuario;
    }

    public Configuracao(Long idConfiguracao, boolean configuracaoContraste, boolean configuracaoTextoAmpliado,
                        float configuracaoSensibilidade, boolean configuracaoModoVoz, Long fKidUsuario) {
        this.idConfiguracao = idConfiguracao;
        this.configuracaoContraste = configuracaoContraste;
        this.configuracaoTextoAmpliado = configuracaoTextoAmpliado;
        this.configuracaoSensibilidade = configuracaoSensibilidade;
        this.configuracaoModoVoz = configuracaoModoVoz;
        this.FKidUsuario = fKidUsuario;
    }

    // método do carlos que realiza a gravação real no banco de dados local
    public boolean criar(Configuracao configuracoes) {
        String query = "INSERT INTO configuracao (configuracao_alto_contraste, configuracao_texto_ampliado, configuracao_sensibilidade_toque, configuracao_modo_voz, FK_id_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, configuracoes.isConfiguracaoContraste());
            stmt.setBoolean(2, configuracoes.isConfiguracaoTextoAmpliado());
            stmt.setFloat(3, configuracoes.getConfiguracaoSensibilidade());
            stmt.setBoolean(4, configuracoes.isConfiguracaoModoVoz());
            stmt.setLong(5, configuracoes.getFKidUsuario());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir configuração no banco: " + e.getMessage());
            return false;
        }
    }

    // adicionei esse método para que o app consiga ler as configurações salvas do banco de dados
    public static Configuracao buscarPorUsuario(long idUsuario) {
        String query = "SELECT * FROM configuracao WHERE FK_id_usuario = ?";
        try (Connection conn = databaseConn.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Configuracao(
                        rs.getLong("id_configuracao"),
                        rs.getBoolean("configuracao_alto_contraste"),
                        rs.getBoolean("configuracao_texto_ampliado"),
                        rs.getFloat("configuracao_sensibilidade_toque"),
                        rs.getBoolean("configuracao_modo_voz"),
                        rs.getLong("FK_id_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar configurações salvas: " + e.getMessage());
        }
        return null;
    }
}