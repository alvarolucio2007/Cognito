package database.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import database.conn.databaseConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class TesteNivelamentoController {

    @FXML private DatePicker dataPosseCelular;
    @FXML private Button botaoSim;
    @FXML private Button botaoNao;
    @FXML private Label labelAviso;
    @FXML private Button botaoContinuar;

    private String usuarioEmail;
    private Boolean usaAssistenteVoz = null;

    public void setUsuarioEmail(String email) {
        this.usuarioEmail = email;
    }

    @FXML
    private void selecionarSim() {
        usaAssistenteVoz = true;
        botaoSim.setStyle("-fx-background-color: #3B52E2; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
        botaoNao.setStyle("-fx-background-color: #0A145C; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
    }

    @FXML
    private void selecionarNao() {
        usaAssistenteVoz = false;
        botaoSim.setStyle("-fx-background-color: #D6E0FF; -fx-text-fill: #3B52E2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-border-color: #3B52E2; -fx-border-radius: 10;");
        botaoNao.setStyle("-fx-background-color: #3B52E2; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10;");
    }

    @FXML
    private void salvarNivelamento() {
        LocalDate posse = dataPosseCelular.getValue();

        if (posse == null || usaAssistenteVoz == null) {
            labelAviso.setText("Por favor, preencha todos os campos!");
            return;
        }

        String nivel = calcularNivel(posse, usaAssistenteVoz);

        try {
            salvarNoBanco(nivel);
            irParaPrincipal();
        } catch (SQLException e) {
            labelAviso.setText("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    private String calcularNivel(LocalDate posse, boolean usaVoz) {
        int pontos = 0;

        int anosDePosse = LocalDate.now().getYear() - posse.getYear();
        if (anosDePosse > 5) pontos += 2;
        else if (anosDePosse >= 2) pontos += 1;

        if (usaVoz) pontos += 1;

        if (pontos <= 1) return "Iniciante";
        if (pontos <= 2) return "Intermediário";
        return "Avançado";
    }

    private void salvarNoBanco(String nivel) throws SQLException {
        // Consultamos pk_id_usaurio(dps eu mudo esse "usaurio") de forma compatível
        String queryBuscaId = "SELECT pk_id_usaurio FROM usuario WHERE usuario_email = ?";
        long idUsuario = -1;

        try (Connection conn = databaseConn.connect();
             PreparedStatement stmtBusca = conn.prepareStatement(queryBuscaId)) {
            stmtBusca.setString(1, usuarioEmail);
            try (ResultSet rs = stmtBusca.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getLong("pk_id_usaurio");
                }
            }
        }

        if (idUsuario == -1) {
            throw new SQLException("Usuário de email " + usuarioEmail + " não foi localizado.");
        }

        String queryInsert = "INSERT INTO teste_nivelamento (teste_nivelamento_nivel_detectado, teste_nivelamento_data_realizacao, FK_id_usuario) " +
                             "VALUES (?, CURRENT_TIMESTAMP, ?)";
        
        try (Connection conn = databaseConn.connect(); 
             PreparedStatement stmtInsert = conn.prepareStatement(queryInsert)) {
            stmtInsert.setString(1, nivel);
            stmtInsert.setLong(2, idUsuario);
            stmtInsert.executeUpdate();
        }
    }

    private void irParaPrincipal() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
            botaoContinuar.getScene().setRoot(root);
        } catch (Exception e) {
            labelAviso.setText("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }
}